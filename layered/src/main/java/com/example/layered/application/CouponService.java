package com.example.layered.application;

import com.example.layered.dataaccess.CouponRepository;
import com.example.layered.dto.SaveCouponReq;
import com.example.layered.dto.SaveCouponRes;
import com.example.layered.entity.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	public SaveCouponRes saveCoupon(SaveCouponReq request) {
		if (couponRepository.existsCouponByUserNameAndType(request.userName(), request.type())) {
			throw new IllegalArgumentException("중복된 쿠폰이 존재함.");
		}

		Coupon newCoupon = new Coupon(
				request.userName(), request.name(), request.type(), request.count()
		);

		return SaveCouponRes.from(couponRepository.save(newCoupon));
	}

}
