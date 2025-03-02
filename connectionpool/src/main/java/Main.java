public class Main {

	public static void main(String[] args) {
		ObjectPool pool = new ObjectPool(5);

		System.out.println("=== 기본 동작 테스트 ===");

		MyObject obj1 = pool.getMyObject();
		MyObject obj2 = pool.getMyObject();
		MyObject obj3 = pool.getMyObject();
		System.out.println("꺼낸 객체: " + obj1 + ", " + obj2 + ", " + obj3);
		System.out.println("3개 꺼냄: 풀 크기 = " + pool.getPoolSize());

		MyObject obj4 = pool.getMyObject();
		System.out.println("4번째 꺼냄: " + obj4 + ", 풀 크기 = " + pool.getPoolSize());

		pool.returnMyObject(obj1);
		pool.returnMyObject(obj2);
		System.out.println("2개 반납 후 풀 크기 = " + pool.getPoolSize());

		MyObject reusedObj = pool.getMyObject();
		System.out.println("재사용 객체: " + reusedObj + ", 풀 크기 = " + pool.getPoolSize());
	}

}
