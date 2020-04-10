package com.web.react.post.dao;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDAOImpl implements CommentDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int createComment(int postId, int userId, String comment) {
		String sql = "INSERT INTO COMMENTS VALUES ( ?, ?, ?, ?, now(), now() )";
		
		int nextId = getNextId("COMMENTS");
		Object[] args = new Object[] { nextId, postId, userId, comment };
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR };
		
		return jdbcTemplate.update(sql, args, types);
	}

	@Override
	public int readComment(int postId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readCommentById(int commentId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readCommentByUserId(int userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateComment(int commentId, String comment) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteComment(int commentId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getNextId(String table) {
		String query = "SELECT IFNULL(MAX(ID),0)+1 FROM " + table;
		return jdbcTemplate.queryForObject(query, int.class);
	}
	
	

}
