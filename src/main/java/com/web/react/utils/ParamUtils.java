package com.web.react.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.ServletRequestUtils;

public class ParamUtils extends ServletRequestUtils{
	
	
	/**
     * Json 형식의 문자열을 List or Map 타입의 형태로 변환해준다.
     * @param String jsonString
     * @param List.class or Map.class
     * @return List.class or Map.class
     */
	public static <T> T jsonFormatStringToJavaObject(String jsonString, Class<T> classType) {
		ObjectMapper jsonToObjectParser = new ObjectMapper(); 
		try {
			return (T)jsonToObjectParser.readValue(jsonString, classType);
		} catch (IOException e) {
			e.printStackTrace();
			if( classType == List.class ) {
				return (T)Collections.EMPTY_LIST;
			} else if ( classType == List.class ) {
				return (T)Collections.EMPTY_MAP;
			}
			return null;
		} 
	}
}
