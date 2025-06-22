package spring;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.ValidationUtils;
import spring.bean.BeanConfig;
import spring.bean.object.MyAppService;
import spring.bean.object.MyClass1;
import spring.bean.object.MyClass2;
import spring.bean.object.MyClass2Inter;
import spring.service.MyService;

@ComponentScan(basePackages = "spring")
public class Main {

	public static void main(String[] args) {
//		ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
//
//		MyAppService myAppService = context.getBean(MyAppService.class);
//
//		myAppService.printStrByNum(1);

		ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

		// MyAppService 빈 가져오기
		MyService myService = context.getBean(MyService.class);

		// save 메서드 호출
		myService.save("Hello World");

		MyClass1 myClass1 = context.getBean(MyClass1.class);
		myClass1.doSomething1(1);

		MyClass2Inter myClass2 = context.getBean(MyClass2Inter.class);
		myClass2.doSomething2("Hello");
	}
}

