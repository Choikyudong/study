package com.example.layered.presentation;

import com.example.layered.application.CouponService;
import com.example.layered.dto.SaveCouponReq;
import com.example.layered.dto.SaveCouponRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponController {

	private final CouponService couponService;

	@PostMapping
	public ResponseEntity<SaveCouponRes> saveCoupon(@RequestBody SaveCouponReq request) {
		return ResponseEntity.ok(couponService.saveCoupon(request));
	}

}
