package com.example.cqrs.cqrsv1.query.repository;

import com.example.cqrs.cqrsv1.query.controller.dto.AddressDto;
import com.example.cqrs.cqrsv1.query.controller.dto.ContactDto;
import com.example.cqrs.notcqrs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserQueryRepository extends JpaRepository<User, Long> {

	@Query("""
		SELECT new com.example.cqrs.cqrsv1.query.dto.ContactDto(
			c.contactId, c.type, c.detail
		)
		FROM Contact c
		WHERE c.user.userId = :userId
	""")
	Set<ContactDto> findContactByUserId(@Param("userId") Long userId);

	@Query("""
		SELECT new com.example.cqrs.cqrsv1.query.dto.AddressDto(
			a.addressId, a.city, a.state, a.postcode
		)
		FROM Address a
		WHERE a.user.userId = :userId
	""")
	Set<AddressDto> findAddressByUserId(@Param("userId") Long userId);

}
