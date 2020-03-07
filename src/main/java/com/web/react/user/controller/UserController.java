package com.web.react.user.controller;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.react.user.dao.UserDao;
import com.web.react.user.model.CommunityUser;
import com.web.react.utils.JsonHelper;
import com.web.react.utils.ParamUtils;
import com.web.react.utils.Result;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

@Controller
@RequestMapping(value = "/user") 
public class UserController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UserDao userDaoImpl;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET , value = "/")
	public String test() {
		return "success";
	}
	
	@CrossOrigin
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/usernameDuplicateCheck") 
	public String getUserByUsername(@RequestBody String signUpInfo) {
		log.info("getUserByUsername() 메서드 실행");
		log.info("signUpInfo : " + signUpInfo);
		
		Map<String, Object> requestUser = null;
		try {
			requestUser = ParamUtils.jsonFormatStringToJavaObject(signUpInfo, Map.class);
		} catch(Exception e) {
			e.printStackTrace();
		}

		log.info("requestUser : " + JsonHelper.Obj2Json(requestUser));

		CommunityUser user = null;  
		String result = null;
		
		user = userDaoImpl.selectUserByUsername(requestUser.get("username").toString());	  
		 
		if (user == null) {
			result = "success";
		} else {
		    result = "fail";
		}
		return result;
	}
	
	@CrossOrigin
	@ResponseBody
	@PostMapping("/signup")
	public Object signUp(@RequestBody String signUpInfo) {
		
		Map<String, Object> signUpUser = ParamUtils.jsonFormatStringToJavaObject(signUpInfo, Map.class);
		log.info("signupUser => ");
		log.info(""+JsonHelper.Obj2Json(signUpUser));
		CommunityUser signupSuccessUser = null;
		if ( signUpUser.keySet().size() > 0 ) {
			signupSuccessUser = userDaoImpl.insertUser(0, signUpUser.get("username").toString(), 
									  signUpUser.get("password").toString(), 
									  signUpUser.get("nickname").toString());
		}
		signupSuccessUser.setPassword(""); 
		return signupSuccessUser;
	}
	
	@CrossOrigin
	@ResponseBody
	@PostMapping("/login")
	public Object login(@RequestBody String userLoginInfo) {
		log.debug("userLoginInfo : "+JsonHelper.Obj2Json(userLoginInfo));
		// 암호일치여부 확인 용도
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		Map<String,Object> loginUser = ParamUtils.jsonFormatStringToJavaObject(userLoginInfo, Map.class);
		
		Result result = Result.newResult();
		
		try {
			CommunityUser user = userDaoImpl.selectUserByUsername(loginUser.get("username").toString());
			
			// 계정 존재 여부
			if( user == null ) {
				result.setSuccess(false);
			}
			
			// 비밀번호 일치여부
			if ( !encoder.matches(loginUser.get("password").toString(), user.getPassword()) ) {
				result.setSuccess(false);
			}
			
			result.getData().put("loginUser", user);
			
		}catch(Exception e) {
			result.setError(e);
			e.printStackTrace();
		}
		
		return result;
	}
}
