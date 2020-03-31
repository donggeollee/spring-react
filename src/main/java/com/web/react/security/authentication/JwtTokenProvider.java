package com.web.react.security.authentication;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtTokenProvider{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String AUTHORITIES_KEY = "auth";

    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";
    
    public String createToken(Authentication authentication) {
    	
    	String authorities = authentication.getAuthorities()
    										.stream()
    										.map(authority ->authority.getAuthority())
    										.collect(Collectors.joining(","));
    	ZonedDateTime now = ZonedDateTime.now();
    	ZonedDateTime expirationDateTime = now.plus(EXPIRATIONTIME, ChronoUnit.MILLIS);
    	Date issueDate = Date.from(now.toInstant());
    	Date expirationDate = Date.from(expirationDateTime.toInstant());
    	
    	System.out.println("  authorities : " + authorities);
    	System.out.println("  now : " + now);
    	System.out.println("  expirationDateTime : " + expirationDateTime);
    	System.out.println("  issueDate : " + issueDate);
    	System.out.println("  expirationDate : " + expirationDate);
    	System.out.println("  SignatureAlgorithm.HS512 : " + SignatureAlgorithm.HS512);
    	System.out.println("  authentication : " + authentication);
    	
    	return Jwts.builder()
    			.setSubject(authentication.getName())
    			.claim(AUTHORITIES_KEY, SECRET)
    			.signWith(SignatureAlgorithm.HS512, SECRET)
    			.setIssuedAt(issueDate)
    			.setExpiration(expirationDate)
    			.compact();
    }
    
    public Authentication getAuthentication(String token) { 
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody(); 
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(",")).stream()
                    .map(authority -> new SimpleGrantedAuthority(authority))
                    .collect(Collectors.toList()); 
        User principal = new User(claims.getSubject(), "", authorities); 
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String authToken) { 
        try { 
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
			return true;
        } catch (SignatureException e) {
        	logger.info("Invalid JWT signature: " + e.getMessage());
        	logger.debug("Exception " + e.getMessage(), e); 
	    } catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
        return false;
    }

}
