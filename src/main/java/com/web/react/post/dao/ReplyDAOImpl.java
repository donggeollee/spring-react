package com.web.react.post.dao;

import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyDAOImpl implements ReplyDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 댓글 생성
	 * @param postId, userId, reply
	 * @return replyId
	 */
	@Override
	public int createReply(int postId, int userId, String reply) {
		String createSql = "INSERT INTO REPLYS VALUES ( ?, ?, ?, ?, now(), now() )";
		int nextId = getNextId("REPLYS");
		Object[] args = new Object[] { nextId, postId, userId, reply };
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR };
		
		// 댓글 생성 성공시 replyId 리턴
		if ( jdbcTemplate.update(createSql, args, types) == 1) {
			return nextId;
		} else {
			return 0;
		}
	}

	/**
	 * 댓글 전체 조회
	 * @param 
	 * @return list
	 */
	@Override
	public List<Map<String, Object>> readReply() {
		String sql = "SELECT R.ID, R.POST_ID, R.USER_ID, U.NICKNAME, R.CREATEDAT, R.REPLY FROM REPLYS R, USERS U WHERE R.USER_ID = U.ID ORDER BY R.ID ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;  
	}

	/**
	 * 댓글 조회 by replyId
	 * @param replyId
	 * @return list
	 */
	@Override
	public List<Map<String, Object>> readReplyById(int replyId) {
		String readSql = "SELECT R.ID, R.POST_ID, U.NICKNAME, U.USERNAME, R.REPLY, R.CREATEDAT,R.UPDATEDAT FROM REPLYS R, USERS U WHERE 1 = 1 AND R.USER_ID = U.ID AND R.ID = ?";
		Object[] args = new Object[] { replyId };	
		int[] types = new int[] { Types.VARCHAR }; 
		List<Map<String, Object>> newReply = Collections.EMPTY_LIST;
		newReply = jdbcTemplate.queryForList( readSql, args, types );
		return newReply;
	}
	
	@Override
	public List<Map<String, Object>> readReplyByPostId(int postId) {
		return null;
	}
	

	@Override
	public List<Map<String, Object>> readReplyByUserId(int userId) {
		return null;
	}

	@Override
	public int updateReply(int replyId, String reply) {
		return 0;
	}

	@Override
	public int deleteReply(int replyId) {
		return 0;
	}
	
	public int getNextId(String tableName) {
		String query = "SELECT IFNULL(MAX(ID),0)+1 FROM " + tableName;
		return jdbcTemplate.queryForObject(query, int.class);
	}

	
	

}
