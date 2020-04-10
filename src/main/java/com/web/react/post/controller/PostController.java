package com.web.react.post.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.react.post.dao.PostDAO;
import com.web.react.post.model.Post;
import com.web.react.utils.JsonHelper;
import com.web.react.utils.ParamUtils;
import com.web.react.utils.Result;

@RestController
@RequestMapping(value="/post")
public class PostController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	PostDAO postDAO;
	
	@Autowired
	JdbcTemplate template;
	
	
	@CrossOrigin 
	@PostMapping("/upload")  // url =>  /upload 까지 붙이니까 잘됨. 
	public Object post(@RequestBody String addPostInfo) {
		
		log.info(addPostInfo);
		
		Map<String, Object> postInfo = ParamUtils.jsonFormatStringToJavaObject(addPostInfo, Map.class);
		
		log.info("postInfo : " + JsonHelper.Obj2Json(postInfo));
		Post post = new Post();
		post.setUserId(Integer.parseInt(postInfo.get("USERNAME").toString()));
		post.setContent(postInfo.get("CONTENT").toString());
		
		return postDAO.createPost(post);
	} 
	
	@CrossOrigin
	@GetMapping("/all")
	public Object getPostAll() {
		log.info("getPostAll()");
		
		Result result = Result.newResult();
		try {
			List<Map<String,Object>> postList = ((List<Map<String,Object>>)postDAO.readPostAll());
			
			if ( postList.isEmpty() ) {
				result.getData().put("posts", Collections.EMPTY_LIST);
			} else {
				result.getData().put("posts", postList);
			}
		} catch(Exception e) {
			result.setError(e);
		}
		return result;
	}
}
