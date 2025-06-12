package com.example.concurrencycontrol.service;

import com.example.concurrencycontrol.entity.Product;
import com.example.concurrencycontrol.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	@Transactional
	public int getStockWithoutLock(Long productId) {
		Product product = productRepository.findProductByIdWithoutLock(productId)
				.orElseThrow(() -> new IllegalArgumentException("Product not found"));

		return product.getStock();
	}

	@Transactional
	public void decreaseStock(Long productId, int quantity) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("Product not found"));

		if (product.getStock() < quantity) {
			throw new IllegalStateException("Insufficient stock");
		}

		product.decreaseStock(quantity);
	}

	@Transactional
	public int getStockWithLock(Long productId) {
		Product product = productRepository.findProductByIdWithLock(productId)
				.orElseThrow(() -> new IllegalArgumentException("Product not found"));

		return product.getStock();
	}

	@Transactional
	public int getStockWithXlock(Long productId) {
		Product product = productRepository.findProductByIdWithoutXlock(productId)
				.orElseThrow(() -> new IllegalArgumentException("Product not found"));

		return product.getStock();
	}

	@Transactional
	public void increaseStockWithXlock(Long productId, int quantity) {
		Product product = productRepository.findProductByIdWithoutXlock(productId)
				.orElseThrow(() -> new IllegalArgumentException("Product not found"));

		product.increaseStock(quantity);
	}

}
