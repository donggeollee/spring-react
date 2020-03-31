package com.web.react.user;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.web.react.user.Role;
import com.web.react.user.RoleManager;
import com.web.react.user.User;
import com.web.react.user.UserManager;
import com.web.react.utils.CommunityConstants;
import com.web.react.user.ConfigService;
import com.web.react.utils.StringUtils;
public class CommunityUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	@Qualifier("userManager")
	private UserManager userManager;	
	
	@Inject
	@Qualifier("roleManager")
	private RoleManager roleManager;	
	
	
	@Inject
	@Qualifier("configService")
	private ConfigService configService;	
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {			
			logger.debug("loading user by {}", username );
			User user = userManager.getUser(username); 
			logger.debug("loaded user {}", user );
			CommuintyUserDetails details = new CommuintyUserDetails(user, getFinalUserAuthority(user)); 
			return details ;			
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found.", e);	
		}
	}

	protected List<GrantedAuthority> getFinalUserAuthority(User user) {		
		if( user.getUserId() <= 0)
			return Collections.EMPTY_LIST;
		
		String authority = configService.getLocalProperty(CommunityConstants.SECURITY_AUTHENTICATION_AUTHORITY_PROP_NAME);
	    if(logger.isDebugEnabled())
	    	logger.debug("grant default authentication {}", authority );
		List<String> roles = new ArrayList<String>();		
		if(! StringUtils.isNullOrEmpty( authority ))
		{
			authority = authority.trim();
		    if (!roles.contains(authority)) {
		    	roles.add(authority);
		    }
		}
		for(Role role : roleManager.getFinalUserRoles(user.getUserId())){
			roles.add(role.getName());
		}
		return AuthorityUtils.createAuthorityList(StringUtils.toStringArray(roles));
	}
	
}
