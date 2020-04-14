package com.web.react.user.dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web.react.user.model.CommunityUser;
import com.web.react.utils.DAOUtils;
import com.web.react.utils.JsonHelper;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Repository
public class UserDaoImpl implements UserDao{

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public CommunityUser selectUserByUsername(String username) {  
		String query  = "SELECT U.USERNAME, U.PASSWORD, U.NICKNAME, R.ROLE_NAME FROM USERS U, ROLE_MEMBERS M, ROLE R WHERE 1 = 1 AND U.ID = M.USER_ID AND M.ROLE_ID = R.ROLE_ID AND USERNAME = ?";
		
		CommunityUser user = null; 
		
		Object[] params = new Object[] { username };
		int[] types = new int[] { Types.VARCHAR };
		List<Map<String,Object>> userList = jdbcTemplate.queryForList(query, params, types);
		
		if ( !userList.isEmpty() ) {
			Map<String, Object> tempUser = userList.get(0);
			
			ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(tempUser.get("ROLE_NAME").toString()));
			
			user = new CommunityUser(tempUser.get("USERNAME").toString(), tempUser.get("PASSWORD").toString(), authorities);
			
			user.setNickname(tempUser.get("NICKNAME").toString());
		} else {
			throw new UsernameNotFoundException("username not found"); 
		}
		
		log.info("seleceUserByUsername : {}", user);
		
		return user;
	}

	@Override
	public List<Map<String,Object>> insertUser(int id, String username, String password, String nickname) {

		
		List<Map<String, Object >> user = Collections.EMPTY_LIST;
		try {
			
			// 사용자 순번 가져오기
			int nextId = getNextId("USERS");
			
			// 사용자 테이블 데이터 추가
			String userInsertQuery = "INSERT INTO USERS (ID, USERNAME, PASSWORD, NICKNAME, CREATEDAT, UPDATEDAT ) VALUES( ?, ?, ?, ?, now(),now() )";
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String hashingPassword = encoder.encode(password);
			
			int userInsertCount = jdbcTemplate.update(userInsertQuery
					, new Object[] { nextId, username, hashingPassword, nickname }
					, new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR }); 
			
			log.info("================= signup Info =================");
			log.info("nextId : " + nextId);
			log.info("username : " + username);
			log.info("password : " + hashingPassword);
			log.info("nickname : " + nickname);
			
			// 새로운 사용자 권한 추가
			String roleInsertQuery = "INSERT INTO ROLE_MEMBERS ( ROLE_ID, USER_ID ) VALUES ( 3, ? )";
			int roleInsertCount = jdbcTemplate.update(roleInsertQuery , new Object[] { nextId } , new int[] { Types.VARCHAR });
			
			// 회원가입 사용자 정보 불러오기
			if( userInsertCount == 1 && roleInsertCount == 1) {
				String query2 = "SELECT USERNAME, PASSWORD, NICKNAME, ROLE_NAME FROM USERS U, ROLE_MEMBERS M, ROLE R WHERE 1 = 1 AND U.ID = M.USER_ID AND M.ROLE_ID = M.ROLE_ID AND U.ID = ?";
				user = jdbcTemplate.queryForList( query2, new Object[] {nextId}, new int[] {Types.VARCHAR} );
				log.info("Signup success user : " + JsonHelper.Obj2Json(user));
			}
		} catch( Exception e) {
			e.printStackTrace();
		} 
		return user;
	} 
	
	public int getNextId(String table) {
		String query = "SELECT IFNULL(MAX(ID),0)+1 FROM " + table;
		return jdbcTemplate.queryForObject(query, int.class);
	}
	
}
