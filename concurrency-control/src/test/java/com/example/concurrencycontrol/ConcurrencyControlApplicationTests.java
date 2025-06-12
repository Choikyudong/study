package com.example.concurrencycontrol;

import com.example.concurrencycontrol.entity.Product;
import com.example.concurrencycontrol.repository.ProductRepository;
import com.example.concurrencycontrol.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConcurrencyControlApplicationTests {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private PlatformTransactionManager transactionManager; // 트랜잭션 매니저 주입

	private Long laptopId;

	@BeforeEach
	@Transactional
	void setUp() {
		productRepository.deleteAllInBatch(); // 모든 데이터 삭제
		Product laptop = new Product(null, "Laptop", 100);
		laptop = productRepository.saveAndFlush(laptop);
		laptopId = laptop.getId();
		System.out.println("\n--- 테스트 데이터 초기화 완료: Laptop 재고=" + laptop.getStock() + " ---\n");
	}

	@Test
	void testNonRepeatableReadWithoutSharedLock() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		CountDownLatch latch = new CountDownLatch(2);

		// 트랜잭션 A (보고서 생성/읽기): 두 번의 읽기 사이에 딜레이를 줍니다.
		Future<int[]> readerFuture = executorService.submit(() -> {
			int firstReadStock = -1;
			int secondReadStock = -1;
			try {
				// 첫 번째 읽기 시작
				firstReadStock = productService.getStockWithoutLock(laptopId); // 락 없이 읽기
				System.out.println("트랜잭션 A: 첫 번째 읽기 - 재고: " + firstReadStock);

				TimeUnit.MILLISECONDS.sleep(500); // 0.5초 대기

				// 두 번째 읽기 시작
				secondReadStock = productService.getStockWithoutLock(laptopId); // 락 없이 다시 읽기
				System.out.println("트랜잭션 A: 두 번째 읽기 - 재고: " + secondReadStock);

			} catch (Exception e) {
				System.err.println("트랜잭션 A 오류: " + e.getMessage());
			} finally {
				latch.countDown();
			}
			return new int[]{firstReadStock, secondReadStock};
		});

		// 트랜잭션 B (재고 감소): A의 첫 번째 읽기 직후 재고를 변경합니다.
		executorService.submit(() -> {
			try {
				TimeUnit.MILLISECONDS.sleep(100); // A가 먼저 첫 번째 읽기를 시작할 시간을 줌

				productService.decreaseStock(laptopId, 50);
			} catch (Exception e) {
				System.err.println("트랜잭션 B 오류: " + e.getMessage());
			} finally {
				latch.countDown();
			}
		});

		latch.await();
		executorService.shutdown();

		try {
			int[] readStocks = readerFuture.get();
			int firstRead = readStocks[0];
			int secondRead = readStocks[1];

			System.out.println("최종 결과: 첫 번째 읽기 = " + firstRead + ", 두 번째 읽기 = " + secondRead);

			assertNotEquals(firstRead, secondRead,
					"공유 잠금 없음으로 인해 두 번의 읽기 결과가 달라야 합니다 (반복 불가능 읽기 발생)");

			assertThat(productService.getStockWithoutLock(laptopId)).isEqualTo(50);
		} catch (Exception e) {
			System.err.println("결과 검증 중 오류: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Test
	void testNonRepeatableReadWithSharedLock() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		CountDownLatch latch = new CountDownLatch(2);

		// 트랜잭션 A (읽기): 외부에서 트랜잭션을 제어하여 공유 잠금을 유지합니다.
		Future<int[]> readerFuture = executorService.submit(() -> {
			// 이 변수들은 람다 외부에서 선언된 것이 아니라, 람다 내부에서 선언됩니다.
			final int[] readStocks = new int[2]; // 결과를 저장할 배열 선언

			TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
			try {
				txTemplate.execute(status -> {
					try {
						readStocks[0] = productService.getStockWithLock(laptopId);
						System.out.println("트랜잭션 A: 첫 번째 읽기 (공유 잠금 획득) - 재고: " + readStocks[0]);

						TimeUnit.MILLISECONDS.sleep(1000); // 1초 대기

						readStocks[1] = productService.getStockWithLock(laptopId);
						System.out.println("트랜잭션 A: 두 번째 읽기 (공유 잠금 유지) - 재고: " + readStocks[1]);

					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						status.setRollbackOnly();
						System.err.println("트랜잭션 A 내부에서 InterruptedException 발생: " + e.getMessage());
					} catch (Exception e) {
						status.setRollbackOnly();
						System.err.println("트랜잭션 A 내부에서 오류: " + e.getMessage());
					}
					return null;
				});
			} finally {
				latch.countDown();
			}

			return readStocks;
		});

		// 트랜잭션 B (쓰기): A가 공유 잠금 걸고 읽는 동안 재고 변경 시도
		executorService.submit(() -> {
			try {
				TimeUnit.MILLISECONDS.sleep(100); // A가 먼저 공유 잠금을 획득할 시간을 줌
				System.out.println("트랜잭션 B: 재고 감소 시도 (A의 공유 잠금 때문에 대기 예상)...");
				productService.decreaseStock(laptopId, 50);
			} catch (Exception e) {
				System.err.println("트랜잭션 B 오류: " + e.getMessage());
			} finally {
				latch.countDown();
			}
		});

		latch.await();
		executorService.shutdown();

		try {
			int[] readStocksResult = readerFuture.get();
			int firstRead = readStocksResult[0];
			int secondRead = readStocksResult[1];

			System.out.println("최종 결과: 첫 번째 읽기 = " + firstRead + ", 두 번째 읽기 = " + secondRead);

			assertEquals(firstRead, secondRead,
					"공유 잠금 적용으로 인해 두 번의 읽기 결과가 같아야 합니다 (반복 불가능 읽기 방지)");

			assertThat(productService.getStockWithoutLock(laptopId)).isEqualTo(50);

		} catch (Exception e) {
			System.err.println("결과 검증 중 오류: " + e.getMessage());
			fail("테스트 실행 중 예외 발생");
		}
	}

	@Test
	void testExclusiveLockPreventsLostUpdate() throws InterruptedException {
		System.out.println("### 독점 잠금으로 인한 갱신 손실 방지 테스트 시작 ###");

		int numberOfThreads = 2; // 동시에 재고를 감소시킬 스레드 수
		int increaseQuantity = 10; // 각 스레드가 감소시킬 수량
		int initialStock = productService.getStockWithXlock(laptopId); // 초기 재고 100

		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		AtomicInteger successCount = new AtomicInteger(0); // 성공적으로 재고 감소한 트랜잭션 수
		AtomicInteger failureCount = new AtomicInteger(0); // 잠금 실패 등 예외 발생한 트랜잭션 수

		for (int i = 0; i < numberOfThreads; i++) {
			executorService.submit(() -> {
				try {
					// 독점 잠금이 적용된 메서드 호출
					productService.increaseStockWithXlock(laptopId, increaseQuantity);
					successCount.incrementAndGet();
				} catch (PessimisticLockingFailureException e) {
					System.err.println("독점 잠금 실패 (대기 후 타임아웃 등): " + e.getMessage());
					failureCount.incrementAndGet();
				} catch (IllegalStateException e) {
					System.err.println("재고 부족 예외 발생: " + e.getMessage());
					failureCount.incrementAndGet();
				} catch (Exception e) {
					System.err.println("예상치 못한 오류: " + e.getMessage());
					failureCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await(); // 모든 스레드가 작업을 완료할 때까지 대기
		executorService.shutdown();

		int finalStock = productService.getStockWithXlock(laptopId);
		System.out.println("--- 테스트 종료 ---");
		System.out.println("초기 재고: " + initialStock);
		System.out.println("최종 재고: " + finalStock);
		System.out.println("성공적인 감소 트랜잭션 수: " + successCount.get());
		System.out.println("실패한 트랜잭션 수: " + failureCount.get());

		assertThat(finalStock).isEqualTo(initialStock + (numberOfThreads * increaseQuantity));
		assertThat(successCount.get()).isEqualTo(numberOfThreads); // 모든 트랜잭션이 성공했는지 확인
		assertThat(failureCount.get()).isEqualTo(0); // 실패한 트랜잭션이 없어야 함
	}

	@Test
	void testOptimisticLockPreventsLostUpdate() throws InterruptedException {
		int numberOfAttempts = 2; // 동시에 재고를 감소시킬 시도 횟수
		int decreaseQuantity = 10; // 각 시도가 감소시킬 수량
		int initialStock = productService.getStockWithoutLock(laptopId); // 초기 재고 100

		ExecutorService executorService = Executors.newFixedThreadPool(numberOfAttempts);
		CountDownLatch latch = new CountDownLatch(numberOfAttempts);
		AtomicInteger successCount = new AtomicInteger(0); // 성공적으로 재고 감소한 트랜잭션 수
		AtomicInteger failureCount = new AtomicInteger(0); // 낙관적 잠금 실패 예외 발생한 트랜잭션 수

		for (int i = 0; i < numberOfAttempts; i++) {
			executorService.submit(() -> {
				try {
					// 낙관적 잠금이 적용된 메서드 호출 (별도 메서드 아님, @Version 존재만으로 동작)
					productService.decreaseStock(laptopId, decreaseQuantity);
					successCount.incrementAndGet();
					System.out.println(Thread.currentThread().getName() + ": 재고 감소 성공!");
				} catch (ObjectOptimisticLockingFailureException e) {
					System.err.println(Thread.currentThread().getName() + ": 낙관적 잠금 실패 (다른 트랜잭션이 먼저 업데이트): " + e.getMessage());
					failureCount.incrementAndGet();
				} catch (IllegalStateException e) {
					System.err.println(Thread.currentThread().getName() + ": 재고 부족 예외 발생: " + e.getMessage());
					failureCount.incrementAndGet();
				} catch (Exception e) {
					System.err.println(Thread.currentThread().getName() + ": 예상치 못한 오류: " + e.getMessage());
					e.printStackTrace();
					failureCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await(); // 모든 스레드가 작업을 완료할 때까지 대기
		executorService.shutdown();

		int finalStock = productService.getStockWithoutLock(laptopId);
		System.out.println("\n--- 테스트 종료 ---");
		System.out.println("초기 재고: " + initialStock);
		System.out.println("최종 재고: " + finalStock);
		System.out.println("성공적인 감소 트랜잭션 수: " + successCount.get());
		System.out.println("낙관적 잠금 실패 트랜잭션 수: " + failureCount.get());

		// 기대 결과:
		// 1. 최종 재고는 '한 번만' 성공적으로 감소된 값이어야 합니다 (100 - 10 = 90).
		// 2. 하나의 트랜잭션만 성공하고, 다른 하나의 트랜잭션은 ObjectOptimisticLockingFailureException 발생하여 실패해야 합니다.
		assertThat(finalStock).isEqualTo(initialStock - decreaseQuantity); // 100 - 10 = 90
		assertThat(successCount.get()).isEqualTo(1); // 오직 하나의 트랜잭션만 성공해야 함
		assertThat(failureCount.get()).isEqualTo(1); // 오직 하나의 트랜잭션만 실패해야 함 (충돌)

		// 최종 Product 버전은 2여야 합니다 (초기 1에서 한 번 증가)
		assertThat(productRepository.findById(laptopId).get().getVersion()).isEqualTo(2L);
	}

}
