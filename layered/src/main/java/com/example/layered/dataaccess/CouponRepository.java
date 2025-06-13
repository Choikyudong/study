package com.example.layered.dataaccess;

import com.example.layered.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

	boolean existsCouponByUserNameAndType(String userName, String type);

}
