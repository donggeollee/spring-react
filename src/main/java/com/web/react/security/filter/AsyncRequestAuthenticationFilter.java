package com.web.react.security.filter;

import java.io.IOException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.Enumerator;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.react.security.authentication.JwtTokenProvider;
import com.web.react.utils.ServletUtils;
import com.web.react.utils.StringUtils;

public class AsyncRequestAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	Logger log = LoggerFactory.getLogger(getClass());
	
	private static String JWT_REQUEST = "JWT_REQUEST";
	private HashMap<String, Object> jsonRequest;
	private boolean postOnly = true;
	private ObjectMapper mapper;
	private String usernameParameter;
	private String passwordParameter;
	
	public AsyncRequestAuthenticationFilter() {
		log.info("AsyncRequestFilter 객체 생성");
		this.mapper = new ObjectMapper();
		usernameParameter = super.getUsernameParameter();
		passwordParameter = super.getPasswordParameter();
	}
	 
	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.mapper, "::::: mapper must be not null ::::"); 
		super.afterPropertiesSet();
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		// POST 방식만 인증 가능
		if ( postOnly && !request.getMethod().equals("POST")) {
			log.info("Authentication method not supported : " + request.getMethod());
			throw new AuthenticationServiceException("Authentication method not supported : " + request.getMethod());
		}
		
		// 요청이  application/json 타입이면 requestBody 꺼내옴
		if ( request.getHeader("Content-Type").contains(ContentType.APPLICATION_JSON.getMimeType()) ) {
			log.info("request's content-type is application/json ");
			
			try {
				this.jsonRequest = this.mapper.readValue( 
											request.getReader().lines().collect(Collectors.joining()) ,
											new TypeReference<Map<String, Object>>(){});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		log.info("[LOGIN REQUEST] username : {} , password : {}]",username,password);
		
		if( StringUtils.isNullOrEmpty(username)) username = "";
		if( StringUtils.isNullOrEmpty(password)) password = "";
		username = username.trim(); 
		
		// Authentication 객체 생성
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		
		// JWT 로그인 요청이면 토큰 발급
		String headerAuthorization = request.getHeader("Authorization");
		if ( !StringUtils.isNullOrEmpty(headerAuthorization) && headerAuthorization.contains("loginRequest") ) {
			log.info("this is Jwt request. Authentication's detail will take \"JWT_REQUEST\" to success or failure handler");
		}
		
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		if ( request.getHeader("Content-Type").contains(ContentType.APPLICATION_JSON.getMimeType()) ) {
			return (String)jsonRequest.get(this.usernameParameter);
		}
		return request.getParameter(usernameParameter);
	}
	
	@Override
	protected String obtainPassword(HttpServletRequest request) {
		if ( request.getHeader("Content-Type").contains(ContentType.APPLICATION_JSON.getMimeType()) ) {
			return (String)jsonRequest.get(this.passwordParameter);
		}
		return request.getParameter(passwordParameter);
	}
	
	@Override
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
}
