package com.web.react.post.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.react.post.dao.ReplyDAO;
import com.web.react.utils.ParamUtils;
import com.web.react.utils.Result;

@RestController
@RequestMapping(value = "/reply")
public class ReplyController {
	
	@Autowired
	ReplyDAO replyDao;
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 댓글입력
	 * @param jsonString
	 * @return saveCount
	 */
	@PostMapping("/create")
	@ResponseBody
	public Object createComment(@RequestBody String jsonString) {
		
		Result result = Result.newResult();
		try {
			Map<String, Object> createCommentInfo= ParamUtils.jsonFormatStringToJavaObject(jsonString, Map.class);
			log.info("createCommentInfo : {}",createCommentInfo);
			int postId = createCommentInfo.get("POST_ID") != null ? Integer.parseInt(createCommentInfo.get("POST_ID").toString()) : 0 ;
			int userId = createCommentInfo.get("USER_ID") != null ? Integer.parseInt(createCommentInfo.get("USER_ID").toString()) : 0 ;
			String comment = createCommentInfo.get("REPLY") != null ? createCommentInfo.get("REPLY").toString() : "";
			
			// 댓글 삽입 => 조회
			int newReplyId = replyDao.createReply(postId, userId, comment);
			
			if ( newReplyId == 0 ) {
				result.setSuccess(false);
			} else {
				result.setSuccess(true);
				result.getData().put("newReply", replyDao.readReplyById(newReplyId));
			}
		} catch(Exception e) {
			result.setError(e);
		}

		return result;  
	} 
	
	@CrossOrigin
	@GetMapping("/read/all")
	@ResponseBody
	public Object readCommentsAll() {
		
		Result result = Result.newResult();
		try {
			List<Map<String, Object>> replys = replyDao.readReply();
			result.getData().put("replys", replys);
		} catch(Exception e) {
			result.setError(e);
			log.error(e.getLocalizedMessage());
		}
		
		return result;
	}
	

	
	
}
