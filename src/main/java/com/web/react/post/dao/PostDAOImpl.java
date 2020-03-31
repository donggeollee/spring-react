package com.web.react.post.dao;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.web.react.post.dao.PostDAO;
import com.web.react.post.model.Post;
import com.web.react.utils.DAOUtils;
import com.web.react.utils.JsonHelper;

@Repository
public class PostDAOImpl implements PostDAO{

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public Object createPost(Post post) {
		
		int nextId= getNextId("POSTS");
		
		String sql = "INSERT INTO POSTS VALUES (?,?,?,now(),now())";
		
		Object[] params = new Object[] { nextId, post.getUserId(),post.getContent() };
		int[] types = new int[] {Types.INTEGER, Types.INTEGER, Types.VARCHAR };

		// 포스트 저장
		int saveCount = jdbcTemplate.update(sql, params, types);
		
		Map<String, Object> createdPost = null;
		
		if ( saveCount == 1 ) {
			createdPost = jdbcTemplate.queryForMap("SELECT * FROM POSTS WHERE id = ?", nextId);
			log.info(JsonHelper.Obj2Json(createdPost) + "");
		}
		
		return createdPost;
	}

	@Override
	public Object readPostById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object readPostAll() {
		String sql = "SELECT * FROM POSTS ORDER BY id DESC";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public int updatePost(Post post) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deletePost(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getNextId(String table) {
		String query = "SELECT IFNULL(MAX(ID),0)+1 FROM " + table;
		return jdbcTemplate.queryForObject(query, int.class);
	}

	
	
}
