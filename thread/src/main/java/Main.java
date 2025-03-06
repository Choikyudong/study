public class Main {

	static int count = 0;

	public static void main(String[] args) {
		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				System.out.println("Thread 1 : " + i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.err.println("Thread1 sleep 에러 : " + e.getMessage());
				}
			}
		});
		thread1.start();


		Thread race1 = new Thread(() -> {
			for (int i = 0; i < 100_000; i++) {
				synchronized (Main.class) {
					count++;
				}
			}
		});

		Thread race2 = new Thread(() -> {
			for (int i = 0; i < 100_000; i++) {
				synchronized (Main.class) {
					count++;
				}
			}
		});

		race1.start();
		race2.start();

		try {
			// 테스트를 위해 두 스레드를 기다림
			race1.join();
			race2.join();
		} catch (InterruptedException i) {
			System.err.println("스레드 join 에러 : " + i);
		}

		System.out.println("count : " + count);
	}

}
