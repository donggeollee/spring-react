package com.web.react.security.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

// import com.web.react.user.UserManager;

// import com.google.common.eventbus.EventBus;

// import architecture.community.i18n.CommunityLogLocalizer;
// import com.web.react.user.CommuintyUserDetails;

//import architecture.community.user.event.UserActivityEvent;

public class CommunityAuthenticationProvider extends DaoAuthenticationProvider {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		logger.info("===================additionalAuthenticationChecks=====================");
		super.additionalAuthenticationChecks(userDetails, authentication);
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("===================authenticate=====================");
		logger.info(":: authentication.getPrincipal() : " + authentication.getPrincipal());
		logger.info(":: authentication.getCredentials() : " + authentication.getCredentials());
		
		authentication 객체에 ajax 요청의 로그인 정보가 안 담기는 문제....spring security ajax 인증 어떻게 함?
		
		return super.authenticate(authentication);
	}
	
//	@Autowired
//	@Qualifier("userManager")
//	private UserManager userManager;	
//	
////	@Autowired(required = false)
////	@Qualifier("eventBus")
////	private EventBus eventBus;
//	
////	private ApplicationEventPublisher applicationEventPublisher;	
////	
////	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
////		this.applicationEventPublisher = applicationEventPublisher;
////	}
//	
//	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//
//		if (authentication.getCredentials() == null) {
//			throw new BadCredentialsException("BadCredentialException Publish"); 
//		}
//		
//		checkLicense(userDetails.getUsername());  
//		try {
//			CommuintyUserDetails user = (CommuintyUserDetails) userDetails;
//			if( user.getUser().isExternal() )
//			{
//				logger.debug("This is external : {}. Auth not supported yet for external users.", user.getUsername() ); 
//			}
//			
////			UserActivityEvent event = new UserActivityEvent(this, user.getUser(), UserActivityEvent.ACTIVITY.SIGNIN );
////			if(applicationEventPublisher!= null) {
////				applicationEventPublisher.publishEvent( event );
////			}
////			
////			if(eventBus!=null){
////				eventBus.post(event);
////			}
//		} catch (Exception e) {
////		    logger.error(CommunityLogLocalizer.getMessage("010102"), e);
//			logger.info("Bad credentials");
//		    throw new BadCredentialsException( messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
//		} 
//		logger.debug("additional authentication checks");
//		super.additionalAuthenticationChecks(userDetails, authentication); 
//	}
//	
//	private void checkLicense ( String username ) {
//		
//		
//	}
}