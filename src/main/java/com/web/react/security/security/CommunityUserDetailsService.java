package com.web.react.security.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.web.react.user.model.CommunityUser;
import com.web.react.utils.JsonHelper;

public class CommunityUserDetailsService implements UserDetailsService{
	Logger log = LoggerFactory.getLogger(getClass());
 
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("=================loadUserByUsername==============");
		log.info(":: username : " + username);
		
		try {
			// db에서 사용자 정보 조회하는 로직 
//			String query = " SELECT * FROM TB_USER";
//			List<Map<String, Object>> userList = jdbcTemplate.queryForList(query);
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPw = encoder.encode("1"); // 비밀번호 1로 박아놓기
			
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_MANAGER");
			ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>(); 
			authorities.add(authority); 
			
			UserDetails user = new CommunityUser(username, encodedPw , authorities);
			 
			log.info(":: user : " + user);
			
			return user;
		} catch(UsernameNotFoundException e) {
			throw new UsernameNotFoundException("username not found");
		}
	}

}
