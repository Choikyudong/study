package spring.bean.object;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class MyClass2 implements MyClass2Inter {

	@Override
	public void doSomething2(String str) {
		if (str.equals("Hello World")) {
			str = str.toLowerCase();
		} else {
			str = str.toUpperCase();
		}
		System.out.println(str);
	}

	/* AOP 적용전
	private final PerformanceClass performanceClass;

	public MyClass2(PerformanceClass performanceClass) {
		this.performanceClass = performanceClass;
	}

	public void doSomething2(String str) {
		AtomicReference<String> ref = new AtomicReference<>(str);
		performanceClass.method("doSomething2", () -> {
			if (ref.get().equals("Hello World")) {
				ref.set(ref.get().toLowerCase());
			} else {
				ref.set(ref.get().toUpperCase());
			}
		});
		System.out.println(ref.get());
	}
	*/
}
