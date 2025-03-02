import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConnectionPoolTests {

	private static DataSource pooledDataSource;
	private static final String JDBC_URL = "jdbc:h2:file:../database/connectiontest";
	private static final String USER_NAME = "sa";
	private static final String PASS_WORD = "";

	@BeforeAll
	static void setUp() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(JDBC_URL);
		config.setUsername(USER_NAME);
		config.setPassword(PASS_WORD);
		config.setMaximumPoolSize(50);
		config.setMinimumIdle(5);
		config.setConnectionTimeout(30000);
		pooledDataSource = new HikariDataSource(config);

		// 스키마 초기화
		try (Connection conn = pooledDataSource.getConnection();
			 Statement stmt = conn.createStatement()) {
			stmt.execute("DROP TABLE IF EXISTS CONNECTION_TEST");
			stmt.execute(""" 
			CREATE TABLE CONNECTION_TEST (
				id BIGINT AUTO_INCREMENT PRIMARY KEY, 
				NAME VARCHAR(255), 
				CREATEDAT TIMESTAMP
			)
			""");
			stmt.execute("CREATE INDEX idx_createdat ON CONNECTION_TEST (CREATEDAT)");
		} catch (SQLException e) {
			throw new RuntimeException("setUp Fail : " + e);
		}
	}

	@Test
	void compareConnectionPerformance() throws InterruptedException {
		long poolInsertTime = insertPerformance(true);
		printMemoryUsage("poolInsertTime");
		long noPoolInsertTime = insertPerformance(false);
		printMemoryUsage("noPoolInsertTime");

		long poolSelectTime = selectPerformance(true);
		printMemoryUsage("poolSelectTime");
		long noPoolSelectTime = selectPerformance(false);
		printMemoryUsage("noPoolSelectTime");

		System.out.println("\n=== [결과] 실행 시간 비교 ===");
		System.out.printf("INSERT (커넥션풀 사용): %dns\n", poolInsertTime);
		System.out.printf("INSERT (매번 새 연결): %dns\n", noPoolInsertTime);
		System.out.printf("INSERT 속도 향상: %.2f%%\n", calculateImprovement(poolInsertTime, noPoolInsertTime));

		System.out.printf("\nSELECT (커넥션풀 사용): %dns\n", poolSelectTime);
		System.out.printf("SELECT (매번 새 연결): %dns\n", noPoolSelectTime);
		System.out.printf("SELECT 속도 향상: %.2f%%\n", calculateImprovement(poolSelectTime, noPoolSelectTime));
	}

	private long insertPerformance(boolean isConnectionPool) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(50);
		long startTime = System.nanoTime();
		for (int i = 0; i < 100_000; i++) {
			executorService.submit(() -> {
				try (Connection conn = isConnectionPool ? pooledDataSource.getConnection()
						: DriverManager.getConnection(JDBC_URL, USER_NAME, PASS_WORD);
					 PreparedStatement stmt = conn.prepareStatement("INSERT INTO CONNECTION_TEST (NAME, CREATEDAT) VALUES (?, CURRENT_TIMESTAMP)")) {
					stmt.setString(1, "name");
					stmt.executeUpdate();
				} catch (SQLException e) {
					throw new RuntimeException("insertPerformance Fail : " + e);
				}
			});
		}

		executorService.shutdown();
		if (!executorService.awaitTermination(5, TimeUnit.MINUTES)) {
			throw new InterruptedException("insertPerformance Wrong TT");
		}
		long endTime = System.nanoTime();
		return endTime - startTime;
	}

	private long selectPerformance(boolean isConnectionPool) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(50);
		long startTime = System.nanoTime();
		for (int thread = 0; thread < 50; thread++) {
			executorService.submit(() -> {
				for (int offset = 10; offset <= 1_000; offset += 10) {
					try (Connection conn = isConnectionPool ? pooledDataSource.getConnection()
							: DriverManager.getConnection(JDBC_URL, USER_NAME, PASS_WORD);
						 PreparedStatement stmt = conn.prepareStatement("SELECT NAME FROM CONNECTION_TEST ORDER BY CREATEDAT DESC LIMIT 100 OFFSET " + offset);
						 ResultSet resultSet = stmt.executeQuery()) {
						while (resultSet.next()) {
							resultSet.getString(1);
						}
					} catch (SQLException e) {
						throw new RuntimeException("selectPerformance Fail : " + e);
					}
				}
			});
		}

		executorService.shutdown();
		if (!executorService.awaitTermination(5, TimeUnit.MINUTES)) {
			throw new InterruptedException("selectPerformance Wrong TT");
		}
		long endTime = System.nanoTime();
		return endTime - startTime;
	}

	private double calculateImprovement(long poolTime, long noPoolTime) {
		if (noPoolTime <= poolTime) {
			return 0.0; // 성능 향상이 없으면 0%
		}
		return ((double) (noPoolTime - poolTime) / noPoolTime) * 100;
	}

	private static void printMemoryUsage(String phase) {
		Runtime runtime = Runtime.getRuntime();
		long usedMemorySum = 0;
		int iterations = 5;
		for (int i = 0; i < iterations; i++) {
			System.gc();
			try {
				Thread.sleep(500); // GC 안정화 대기
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			long totalMemory = runtime.totalMemory();
			long freeMemory = runtime.freeMemory();
			usedMemorySum += (totalMemory - freeMemory);
		}
		double avgUsedMemory = usedMemorySum / (iterations * 1024.0 * 1024.0);
		System.out.println("=== " + phase + " ===");
		System.out.printf("평균 사용 중 메모리: %.2f MB%n", avgUsedMemory);
		System.out.println();
	}

}
