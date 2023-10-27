package com.myweb.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import com.myweb.www.security.CustomAuthMemberService;
import com.myweb.www.security.LoginFailureHandler;
import com.myweb.www.security.LoginSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	//security package를 생성하여 사용자 핸들러 생성
	
	//비밀번호 암호화 객체 빈 생성
	//객체 생성해서 빈에 등록
	@Bean
	public PasswordEncoder bcPasswordEncoder() {
		//객체 생성해서 리턴
		return new BCryptPasswordEncoder();
	}
	
	//SuccessHandler 빈 객체 생성 => 사용자가 생성
	@Bean
	public AuthenticationSuccessHandler authSuccessHandler() {
		return new LoginSuccessHandler();
	}
	
	//failureHandler 빈 객체 생성 => 사용자가 생성
	@Bean
	public AuthenticationFailureHandler authFailureHandler() {
		return new LoginFailureHandler();
	}
	
	//userDetail 빈 객체 생성 => 사용자가 생성
	@Bean
	public UserDetailsService CustomUserService() {
		return new CustomAuthMemberService();
	}
	
	//유저에 대한 설정
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(CustomUserService())
		.passwordEncoder(bcPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		//(htpp)승인 요청
		//ADMIN이라는 hasRole 객체만 /member/list 허용 => admin이 할 수 있는 경로...
		http.authorizeRequests()
		.antMatchers("/member/list").hasRole("ADMIN")
		.antMatchers("/", "/board/list", "/board/detail", "/resources/**", "/upload/**", "/comment/**", "/member/register", "/member/login").permitAll() //permitAll() : 모든 사용자에게 볼 수 있는 경로(비회원 포함), / : 인덱스
		.anyRequest().authenticated(); // (anyRequest() : 나머지, 나머지 경로는, (인증된 사용자만 접근 가능한 매핑으로 간다면, 로그인 페이지로 이동)) => 인증된 사용자만 처리
		
		//커스텀 로그인 페이지를 구성
		//Controller에 주소 요청 매핑도 같이 꼭 적어줘야 함
		http.formLogin()
		.usernameParameter("email") //username이 아이디인 경우 아이디
		.passwordParameter("pwd")
		.loginPage("/member/login")
		.successHandler(authSuccessHandler())
		.failureHandler(authFailureHandler());
		
		//로그아웃 페이지
		http.logout()
		.logoutUrl("/member/logout")
		.invalidateHttpSession(true) //기존 세션 끊기
		.deleteCookies("JSESSIONID") //로그인을 하게 되면 쿠키에 세션에 아이디가 자동 등록됨 => 지우기
		.logoutSuccessUrl("/"); //로그아웃 후 갈 url
	}

	
}
