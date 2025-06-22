package spring.bean;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import spring.bean.object.MyAppService;
import spring.bean.object.StringClass;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


@Configuration
@EnableAspectJAutoProxy
public class BeanConfig {

	@Bean
	public StringClass stringClass() {
		return new StringClass();
	}

	@Bean
	public MyAppService myAppService() {
		return new MyAppService(stringClass());
	}

	@Bean
	public Logger mylogger() {
		Logger logger = Logger.getLogger("spring.mylogger");
		logger.setUseParentHandlers(false);

		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINER);

		logger.addHandler(handler);
		logger.setLevel(Level.FINER);

		return logger;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	public StopWatch myWatch() {
		return new StopWatch();
	}

}
