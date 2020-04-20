package com.web.react.utils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DAOUtils {
	
	@Autowired
	public static JdbcTemplate jdbcTemplate;
	
	public static int readNextIdByTableName(String tableName) {
		return jdbcTemplate.queryForObject("SELECT MAX(ID) + 1 AS NEXT_ID FROM " + tableName, Integer.class);
	}
	
	public static int readNextIdByObjectType(int objectType) {
		return jdbcTemplate.queryForObject("SELECT OBJECT_SEQUENCE + 1 AS NEXT_ID FROM OBJECTS WHERE OBJECT_TYPE = ?", Integer.class, objectType);
	}

	public static int getFileIdByObjectType(int objectType) {
		return jdbcTemplate.queryForObject("SELECT OBJECT_ID + 1 FROM FILES WHERE OBJECT_TYPE = ?", Integer.class, objectType);
	}
	

	
	
}
