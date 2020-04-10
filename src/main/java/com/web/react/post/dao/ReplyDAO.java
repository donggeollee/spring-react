package com.web.react.post.dao;

import java.util.List;
import java.util.Map;

public interface ReplyDAO {
	
	int createReply(int postId, int userId, String reply);
	
	List<Map<String, Object>> readReply();
	
	List<Map<String, Object>> readReplyById(int replyId);
	
	List<Map<String, Object>> readReplyByPostId(int postId);
	
	List<Map<String, Object>> readReplyByUserId(int userId);
	
	int updateReply(int replyId, String reply);
	
	int deleteReply(int replyId);

}
