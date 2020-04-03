package com.web.react.security.authentication;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import com.web.react.utils.StringUtils;
import io.jsonwebtoken.ExpiredJwtException;

public class JWTFilter extends GenericFilterBean { 
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private final JwtTokenProvider jwtTokenProvider;
	
	public JWTFilter(JwtTokenProvider jwtTokenProvider) { 
		Assert.notNull(jwtTokenProvider, "JwtTokenProvider cannot be null");
        this.jwtTokenProvider = jwtTokenProvider;
    }
	
	@Override
	public void afterPropertiesSet() {
		Assert.notNull(jwtTokenProvider, "JwtTokenProvider cannot be null");
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		logger.debug("==================JWTFilter==================");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String header = request.getHeader(JwtTokenProvider.HEADER_STRING);
		if (StringUtils.isNullOrEmpty(header)) {
			chain.doFilter(request, response);
			return;
		}
		
        try { 
            String jwt = this.resolveToken(request); 
             if (!StringUtils.isNullOrEmpty(jwt)) {
            	logger.debug("jwt token : {}", jwt);
                if (this.jwtTokenProvider.validateToken(jwt)) {
                    Authentication authentication = this.jwtTokenProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }  
        } catch (ExpiredJwtException eje) {
        	logger.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
        	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            logger.debug("Exception " + eje.getMessage(), eje);
            return;
        }
        
        chain.doFilter(request, response);
        this.resetAuthenticationAfterRequest(); 
    }

    private void resetAuthenticationAfterRequest() {
    	logger.debug("reset authentication as null.");
        SecurityContextHolder.getContext().setAuthentication(null);
    }
 
    private String resolveToken(HttpServletRequest request) { 
        String bearerToken = request.getHeader(JwtTokenProvider.HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
            String jwt = bearerToken.substring(7, bearerToken.length());
            return jwt;
        }
        return null;
    }	
}