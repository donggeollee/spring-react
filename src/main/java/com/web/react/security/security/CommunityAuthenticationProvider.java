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

import com.web.react.user.model.CommunityUser;

// import com.web.react.user.UserManager;

// import com.google.common.eventbus.EventBus;

// import architecture.community.i18n.CommunityLogLocalizer;
// import com.web.react.user.CommuintyUserDetails;

//import architecture.community.user.event.UserActivityEvent;

public class CommunityAuthenticationProvider extends DaoAuthenticationProvider {
	
//	@Autowired
//	@Qualifier("userManager")
//	private UserManager userManager;	
//	
//	@Autowired(required = false)
//	@Qualifier("eventBus")
//	private EventBus eventBus;
//	
//	private ApplicationEventPublisher applicationEventPublisher;	
//	
//	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
//		this.applicationEventPublisher = applicationEventPublisher;
//	}
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		log.info("===================additionalAuthenticationChecks=====================");
		
		if (authentication.getCredentials() == null) {
			throw new BadCredentialsException("BadCredentialException Publish"); 
		}
		
		CommunityUser cuser = null;
		if (userDetails instanceof CommunityUser) {
			log.info("userDetails's type is CommunityUser");
			cuser = (CommunityUser)userDetails;
			log.info("cuser.getNickname() : {}",cuser.getNickname());
		}
		
		authentication.setDetails(cuser);
		super.additionalAuthenticationChecks(userDetails, authentication);
	} 
	
	@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("===================authenticate=====================");
			return super.authenticate(authentication);
		}
	
//	@Override
//	protected void additionalAuthenticationChecks(UserDetails userDetails,
//			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//		logger.info("===================additionalAuthenticationChecks=====================");
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
//			UserActivityEvent event = new UserActivityEvent(this, user.getUser(), UserActivityEvent.ACTIVITY.SIGNIN );
//			if(applicationEventPublisher!= null) {
//				applicationEventPublisher.publishEvent( event );
//			}
//			
//			if(eventBus!=null){
//				eventBus.post(event);
//			}
//		} catch (Exception e) {
//		    logger.error(CommunityLogLocalizer.getMessage("010102"), e);
//			logger.info("Bad credentials");
//		    throw new BadCredentialsException( messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
//		} 
//		logger.debug("additional authentication checks");
//		super.additionalAuthenticationChecks(userDetails, authentication);
//	} 
	
	private void checkLicense ( String username ) {
		
		
	}

}