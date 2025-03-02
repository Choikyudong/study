import java.util.*;

public class ObjectPool {

	private final Queue<MyObject> pool = new ArrayDeque<>();
	private final int MAX_SIZE;

	public ObjectPool() {
		MAX_SIZE = 10; // default
		for (int i = 0; i < MAX_SIZE; i++) {
			pool.offer(new MyObject());
		}
	}

	public ObjectPool(int maxSize) {
		this.MAX_SIZE = maxSize;
		for (int i = 0; i < MAX_SIZE; i++) {
			pool.offer(new MyObject());
		}
	}

	public MyObject getMyObject() {
		if (!pool.isEmpty()) {
			return pool.poll();
		}
		return new MyObject(); // 풀 부족시 생성
	}

	public void returnMyObject(MyObject myObject) {
		if (myObject != null && pool.size() < MAX_SIZE) {
			pool.offer(myObject);
		}
	}

	public int getPoolSize() {
		return pool.size();
	}

}

class MyObject {
	private static int count = 0;
	private final int id; // 식별용

	public MyObject() {
		id = count++;
	}

	@Override
	public String toString() {
		return "MyObject-" + id;
	}

}