package com.web.react.user.dao;

import org.springframework.stereotype.Repository;

import com.web.react.user.model.CommunityUser;

public interface UserDao { 
	
	CommunityUser selectUserByUsername(String username);
	
	CommunityUser insertUser(int id, String username, String password, String nickname);

}
  