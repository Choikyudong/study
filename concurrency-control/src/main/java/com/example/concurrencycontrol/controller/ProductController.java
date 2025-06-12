package com.example.concurrencycontrol.controller;

import com.example.concurrencycontrol.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/products/{id}")
	public ResponseEntity<Integer> getStock(@PathVariable Long id) {
		return ResponseEntity.ok(productService.getStockWithoutLock(id));
	}

	@PostMapping("/products/{id}/increase")
	public ResponseEntity<String> increaseStock(@PathVariable Long id, @RequestParam int quantity) {
		productService.increaseStockWithXlock(id, quantity);
		return ResponseEntity.ok("Stock updated successfully");
	}

	@PostMapping("/products/{id}/decrease")
	public ResponseEntity<String> decreaseStock(@PathVariable Long id, @RequestParam int quantity) {
		productService.decreaseStock(id, quantity);
		return ResponseEntity.ok("Stock updated successfully");
	}

}
