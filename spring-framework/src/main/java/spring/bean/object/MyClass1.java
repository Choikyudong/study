package spring.bean.object;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class MyClass1 {

	public void doSomething1(int  i) {
		if (i == 1) {
			i += 5;
		} else {
			i -= 5;
		}
     	System.out.println(i);
	}

	/* AOP 적용전
	private final PerformanceClass performanceClass;

	public MyClass1(PerformanceClass performanceClass) {
		this.performanceClass = performanceClass;
	}

	public void doSomething1(int  i) {
		AtomicReference<Integer> ref = new AtomicReference<>(i);
		performanceClass.method("doSomething1" , () -> {
			int num = ref.get();
			if (num == 1) {
				num += 5;
			} else {
				num -= 5;
			}
			ref.set(num);
		});
		System.out.println(ref.get());
	}
	*/
}
