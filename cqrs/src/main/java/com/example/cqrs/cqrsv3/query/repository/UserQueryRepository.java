package com.example.cqrs.cqrsv3.query.repository;

import com.example.cqrs.cqrsv3.query.controller.dto.AddressDto;
import com.example.cqrs.cqrsv3.query.controller.dto.ContactDto;
import com.example.cqrs.cqrsv3.query.domain.UserReadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserQueryRepository extends JpaRepository<UserReadEntity, Long> {

	@Query("""
		SELECT new com.example.cqrs.cqrsv2.query.domain.dto.ContactDto(
			c.contactId, c.type, c.detail
		)
		FROM Contact c
		WHERE c.user.userId = :userId
	""")
	Set<ContactDto> findContactByUserId(@Param("userId") Long userId);

	@Query("""
		SELECT new com.example.cqrs.cqrsv2.query.domain.dto.AddressDto(
			a.addressId, a.city, a.state, a.postcode
		)
		FROM Address a
		WHERE a.user.userId = :userId
	""")
	Set<AddressDto> findAddressByUserId(@Param("userId") Long userId);

}
