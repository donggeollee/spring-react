package com.web.react.user.dao;

import java.util.List;
import java.util.Map;


import com.web.react.user.model.CommunityUser;

public interface UserDao { 
	
	CommunityUser selectUserByUsername(String username);
	
	List<Map<String,Object>> insertUser(int id, String username, String password, String nickname);

}
  