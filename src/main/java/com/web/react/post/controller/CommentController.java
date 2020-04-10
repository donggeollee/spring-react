package com.web.react.post.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.react.post.dao.CommentDAO;
import com.web.react.utils.ParamUtils;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {
	
	@Autowired
	CommentDAO commentDao;
	
	
	/**
	 * 댓글입력
	 * @param jsonString
	 * @return saveCount
	 */
	public int createComment(@RequestBody String jsonString) {
		
		Map<String, Object> createCommentInfo= ParamUtils.jsonFormatStringToJavaObject(jsonString, Map.class);
		
		int postId = createCommentInfo.get("POST_ID") != null ? Integer.parseInt(createCommentInfo.get("POST_ID").toString()) : 0;
		int userId = createCommentInfo.get("USER_ID") != null ? Integer.parseInt(createCommentInfo.get("USER_ID").toString()) : 0;
		String comment = createCommentInfo.get("REPLY") != null ? createCommentInfo.get("REPLY").toString() : "";

		return commentDao.createComment(postId, userId, comment); 
	}
	
	

	
	
}
