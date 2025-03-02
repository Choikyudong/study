package com.example.webfilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<MemberFilter> memberFilterRegistration(MemberFilter memberFilter) {
		FilterRegistrationBean<MemberFilter> registration = new FilterRegistrationBean<>(memberFilter);
		registration.setOrder(1);
		return registration;
	}

	@Bean
	public FilterRegistrationBean<TokenFilter> tokenFilterRegistration(TokenFilter tokenFilter) {
		FilterRegistrationBean<TokenFilter> registration = new FilterRegistrationBean<>(tokenFilter);
		registration.setOrder(2);
		return registration;
	}

}
