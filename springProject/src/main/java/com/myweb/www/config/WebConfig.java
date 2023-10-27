package com.myweb.www.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	//클래스 객체를 배열로 리턴
	//	<context-param>
	//	<param-name>contextConfigLocation</param-name>
	//	<param-value>/WEB-INF/spring/root-context.xml</param-value>
	//	</context-param>
	@Override
	protected Class<?>[] getRootConfigClasses() {
		//10/27 SecurityConfig 추가, 최상위 루트에서 처리해야하는 클래스 추가
		return new Class[] {RootConfig.class, SecurityConfig.class};
	}

	//	<servlet>
	//	<servlet-name>appServlet</servlet-name>
	//	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	//	<init-param>
	//		<param-name>contextConfigLocation</param-name>
	//		<param-value>
	//		/WEB-INF/spring/appServlet/servlet-context.xml
	//		/WEB-INF/spring/appServlet/security-context.xml
	//		</param-value>
	//	</init-param>
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {ServletConfiguration.class};
	}

	//	<servlet-mapping>
	//	<servlet-name>appServlet</servlet-name>
	//	<url-pattern>/</url-pattern>
	//	</servlet-mapping>
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}
	
	//	<filter>
	//	<filter-name>encoding</filter-name>
	//	<filter-class>
	//		org.springframework.web.filter.CharacterEncodingFilter	
	//	</filter-class>
	//	<init-param>
	//		<param-name>encoding</param-name>
	//		<param-value>UTF-8</param-value>
	//	</init-param>
	//</filter>
	@Override
	protected Filter[] getServletFilters() {
		//encoding filer 설정
		//(내가 원하는 설정의 인코딩 필터가 아니면 내가 원하는 설정으로 바꿔줌)
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		//인코딩 설정
		encodingFilter.setEncoding("utf-8");
		//setEncoding : 들어오는 값(request)을 처리
		//setForceEncoding : 나가는 값(response)을 처리(true)
		encodingFilter.setForceEncoding(true); //외부로 나가는 데이터도 인코딩 설정
		return new Filter[] {encodingFilter};
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		//그 외 기타 사용자 설정
		//사용자 지정 익셉션 설정을 할 것인지 처리
		//(init-parm 설정)
		registration.setInitParameter("throwExceptionIfNotHandlerFound", "true");
		
		//파일 업로드 설정
		//경로, maxFileSize, maxReqSize, fileSizeThreshold
		String uploadLocation = "D:\\_myweb\\_java\\fileupload";
		int maxFileSize = 1024*1024*20; //20MB
		int maxReqSize = maxFileSize*2; //40MB
		int fileSizeThreshold = maxFileSize; //20MB
		
		MultipartConfigElement multipartConfig = 
				new MultipartConfigElement(uploadLocation, maxFileSize, maxReqSize, fileSizeThreshold);
		
		registration.setMultipartConfig(multipartConfig);
		
	}
	
	

}
