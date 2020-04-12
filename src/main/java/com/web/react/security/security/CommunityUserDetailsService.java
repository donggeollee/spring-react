package com.web.react.security.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.web.react.user.dao.UserDao;

public class CommunityUserDetailsService implements UserDetailsService{
	Logger log = LoggerFactory.getLogger(getClass());
 
	@Autowired
	UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("=================loadUserByUsername==============");
		log.info(":: username : " + username);
		
		try {
			return userDao.selectUserByUsername(username);
		} catch(UsernameNotFoundException e) {
			throw new UsernameNotFoundException("username not found");
		}
	}

}
