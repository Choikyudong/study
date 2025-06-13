package com.example.layered.dto;

import com.example.layered.entity.Coupon;

public record SaveCouponRes(String name, String Type, int count) {
	public static SaveCouponRes from(Coupon coupon) {
		return new SaveCouponRes(
				coupon.getName(), coupon.getType(), coupon.getCount()
		);
	}
}
