package com.tenco.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenco.bank.handler.AuthInterceptor;
import com.tenco.bank.utils.Define;

// @Configuration --> 스프링 부트 설정 클래스라는 의미
@Configuration // IoC 대상 : 2개 이상에 IoC(Bean) 사용
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Autowired // DI
	private AuthInterceptor authInterceptor;

	// 요청 올 때 마다 Domain URI 검사 예정
	// /account/xxx <-으로 들어오는 도메인은 다 검사
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
				.addPathPatterns("/account/**")
				.addPathPatterns("/auth/**");
	}
	
	@Bean // IoC 대상 - 싱글톤 처리
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// 리소스 등록 처리
	// 서버 컴퓨터에 위치한 리소스를 활용하는 방법(프로젝트 외부 폴더 접근)
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Fake Path <-- 보안을 위해 가짜 셋팅
		registry.addResourceHandler("/images/upload/**")
		.addResourceLocations("file:///C:\\work_spring\\upload/");
	}
}
