package com.web.react.post.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.react.utils.JsonHelper;
import com.web.react.utils.ParamUtils;

@Controller
@RequestMapping(value = "/post")
public class PostController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@CrossOrigin
	@ResponseBody
	@PostMapping("/")
	public Object post(@RequestBody String postInfo) {  
		
		Map<String, Object> addPostInfo = null;
		
		try {
			addPostInfo = ParamUtils.jsonFormatStringToJavaObject(postInfo, Map.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		log.info("addPostInfo : " + JsonHelper.Obj2Json(addPostInfo));
		
		return null;
	}

}
