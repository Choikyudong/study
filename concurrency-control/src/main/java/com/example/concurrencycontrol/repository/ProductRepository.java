package com.example.concurrencycontrol.repository;

import com.example.concurrencycontrol.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("select p from Product p where p.id = :id")
	Optional<Product> findProductByIdWithoutLock(@Param("id") Long id);

	// 공유 잠금 적용
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("select p from Product p where p.id = :id")
	Optional<Product> findProductByIdWithLock(@Param("id") Long id);

	// 독점 잠금 적용
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from Product p where p.id = :id")
	Optional<Product> findProductByIdWithoutXlock(@Param("id") Long id);
	
}
