package com.web.react.user.dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.web.react.user.model.CommunityUser;
import com.web.react.utils.DAOUtils;
import com.web.react.utils.JsonHelper;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Repository
public class UserDaoImpl implements UserDao{

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public CommunityUser selectUserByUsername(String username) {  
		String query  = "SELECT ID, USERNAME, NICKNAME, CREATEDAT, UPDATEDAT FROM USERS WHERE USERNAME = ?";
		
		CommunityUser user = null; 
		
		try {
			Object[] params = new Object[] { username };
			int[] types = new int[] { Types.VARCHAR };
			log.info(username);
			List<Map<String,Object>> userList = jdbcTemplate.queryForList(query, params, types);
			
			
			if ( !userList.isEmpty() ) {
				Map<String, Object> tempUser = userList.get(0);
				
				ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
				
				user = new CommunityUser(tempUser.get("USERNAME").toString(), tempUser.get("PASSWORD").toString(), authorities);
				
				user.setNickname(tempUser.get("NICKNAME").toString());
			}
		} catch(Exception e) {
			log.error(e.getMessage());
		}
	 
		if ( user != null ) 
			log.info(user.getUsername());
		
		return user;
	}

	@Override
	public CommunityUser insertUser(int id, String username, String password, String nickname) {

		int nextId = getNextId("USERS");
		String query = "INSERT INTO USERS (ID, USERNAME, PASSWORD, NICKNAME, CREATEDAT, UPDATEDAT ) VALUES( ?, ?, ?, ?, now(),now() )";
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashingPassword = encoder.encode(password);
		
		int saveCount = jdbcTemplate.update(query
				, new Object[] { nextId, username, hashingPassword, nickname }
				, new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,});
		
		log.info("================= signup Info =================");
		log.info("nextId : " + nextId);
		log.info("username : " + username);
		log.info("password : " + hashingPassword);
		log.info("nickname : " + nickname);
		
		CommunityUser user = null; 
		if( saveCount == 1 ) {
			String query2 = "SELECT ID, USERNAME, PASSWORD, NICKNAME, CREATEDAT, UPDATEDAT FROM USERS WHERE ID = ? ";
//			try {
//				user = jdbcTemplate.queryForObject(query2, new RowMapper<CommunityUser>() {
//					@Override
//					public CommunityUser mapRow(ResultSet rs, int rowNum) throws SQLException {
//						CommunityUser user = new CommunityUser(   rs.getInt(1) 
//																, rs.getString(2)
//																, rs.getString(3)
//																, rs.getString(4)
//																, rs.getDate(5)
//																, rs.getDate(6)
//															);
//						return user;
//					}
//				}, nextId);
//			} catch(Exception e) {
//				log.error(e.getLocalizedMessage());
//			}
		}
		log.info("Signup success user : " + JsonHelper.Obj2Json(user));
		return user;
	} 
	
	public int getNextId(String table) {
		String query = "SELECT IFNULL(MAX(ID),0)+1 FROM " + table;
		return jdbcTemplate.queryForObject(query, int.class);
	}
	
}
