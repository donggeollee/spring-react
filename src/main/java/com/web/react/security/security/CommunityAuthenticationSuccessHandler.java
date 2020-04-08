package com.web.react.security.security;

import java.io.IOException;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.web.react.security.authentication.JwtTokenProvider;
import com.web.react.user.model.CommunityUser;
import com.web.react.utils.Result;
import com.web.react.utils.ServletUtils;
import com.web.react.utils.StringUtils;


public class CommunityAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	RequestCache requestCache;

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Override
	public void onAuthenticationSuccess( HttpServletRequest request, 
										 HttpServletResponse response,
										 Authentication authentication) throws IOException, ServletException {
		log.info("==============onAuthenticationSuccess===============");
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if( savedRequest == null ) {
			log.info("savedRequest is null");
		} else { 
			log.info("savedRequest is not null");
		}
		
		if( request.getHeader(HttpHeaders.CONTENT_TYPE).contains(ContentType.APPLICATION_JSON.getMimeType()) ) {
			log.info("handle json Request");
			handleJsonRequest(request, response, authentication);
		} else {
			log.info("handle normal Request");
			super.onAuthenticationSuccess(request, response, authentication);
		}
		
	}
	
	
	
	
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request,
//			HttpServletResponse response, Authentication authentication)
//			throws ServletException, IOException {
//		
//		SavedRequest savedRequest = requestCache.getRequest(request, response);
//
//		if (savedRequest == null) {
//			logger.debug("no savend request");			
//			if (ServletUtils.isAcceptJson(request)) {
//				logger.debug("handle json request");
//				handleJsonRequest(request, response, authentication);
//			}else{
//				logger.debug("handle normal request");
//				super.onAuthenticationSuccess(request, response, authentication);
//			}
//			return;
//			
//		}
//		
//		String targetUrlParameter = getTargetUrlParameter();		
//		logger.debug("target parameters {0}", (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) );
//		
//		if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
//			requestCache.removeRequest(request, response);
//			super.onAuthenticationSuccess(request, response, authentication);
//
//			return;
//		}
//
//		logger.debug("clear authentication attrs");
//		clearAuthenticationAttributes(request);
//
//		// Use the DefaultSavedRequest URL
//		String targetUrl = savedRequest.getRedirectUrl();
//		logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
//		getRedirectStrategy().sendRedirect(request, response, targetUrl);
//	}
//
//	
//	public void onAuthenticationSuccess2(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//		SavedRequest savedRequest = requestCache.getRequest(request, response);
//		// Checking Cache Exist.
//		if (savedRequest == null) {		
//			doAuthenticationSuccess(request, response, authentication);		
//		}		
//		String targetUrlParameter = getTargetUrlParameter();
//		if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
//			requestCache.removeRequest(request, response);			
//			doAuthenticationSuccess(request, response, authentication);			
//		}		
//		clearAuthenticationAttributes(request);
//		// Use the DefaultSavedRequest URL 
//		if (ServletUtils.isAcceptJson(request)) {
//			handleJsonRequest(request, response, authentication);
//		}else{			
//			String targetUrl = savedRequest.getRedirectUrl();
//			logger.debug("Redirecting to Default SavedRequest Url: " + targetUrl);		
//			getRedirectStrategy().sendRedirect(request, response, targetUrl);
//		}
//	}
//	
//	public void setRequestCache(RequestCache requestCache) {
//		this.requestCache = requestCache;
//	}
//	
//	protected void doAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {		
//		if (ServletUtils.isAcceptJson(request)) {
//			handleJsonRequest(request, response, authentication);
//			return;
//		}else{		
//			super.onAuthenticationSuccess(request, response, authentication);	
//		}
//	}
//	
	protected void handleJsonRequest(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
		Result result = Result.newResult();
		
		// JWT 로그인 요청 -> authToken 반환
		String authToken = null;
		log.info("authentication.getDetails() : {}",authentication.getDetails());

		String jwtToken = jwtTokenProvider.createToken(authentication);
		result.getData().put("authToken", jwtToken);
		result.getData().put("success", true);
		result.getData().put("returnUrl", ServletUtils.getReturnUrl(request, response));
		if( authentication.getPrincipal() instanceof CommunityUser ) {
			CommunityUser temp = (CommunityUser)authentication.getPrincipal();
			temp.setPassword("");
			result.getData().put("loginInfo",temp);
		}
		
		String referer = request.getHeader("Referer");		
		if (StringUtils.isNullOrEmpty(referer))
			result.getData().put("referer", referer);
		
		Map<String, Object> model = new ModelMap(); 
		model.put("item", result);
		try {
			createJsonViewAndRender(model, request, response);
		} catch (Exception e) {}	
	}
	
	protected void createJsonViewAndRender(Map<String, ?> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin","*");
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.setExtractValueFromSingleKeyModel(true);
		view.setModelKey("item");
		view.render(model, request, response);
	}

}

