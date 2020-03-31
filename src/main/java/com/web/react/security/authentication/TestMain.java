package com.web.react.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class TestMain {

	private static JWTprovider JwtProvider = new JWTprovider();
	
    public static void main(String[] args) {
    	
    	String username = "donggeol";
    	String password = "yi109304";
    	Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
    	String JsonWebToken = JwtProvider.createToken(authentication);
    	System.out.printf("(%s) (%s) JWT : %s \n",username, password, JsonWebToken );
    	
    	boolean validate = JwtProvider.validateToken(JsonWebToken);
    	System.out.printf("Token Validate Check : %b ", validate ); 
    	 
    	
    }
}
