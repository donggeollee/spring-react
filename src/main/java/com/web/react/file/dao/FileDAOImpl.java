package com.web.react.file.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.web.react.utils.DAOUtils;

@Repository
public class FileDAOImpl implements FileDAO{

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int createFile(int objectType, String fileName, long fileSize) {
		
		
		int nextId = getRandomId();
		// int nextId = getFileIdByObjectType;
		Object[] params = new Object[] {nextId, fileName, fileSize};
		int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
		
		String sql = "INSERT INTO FILES VALUES (3, ?, ? ,? )";
		
		if ( jdbcTemplate.update(sql, params, types) == 1 ) {
			return nextId;
		} else { 
			return 0;
		}
	}
	
	@Override
	public ArrayList<Map<String, Object>> readFileListByPostId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int updateFileId(int ObjeccdType, int ObjectId, int updateId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getFileIdByObjectType(int objectType) {
		return jdbcTemplate.queryForObject("SELECT MAX(OBJECT_ID) + 1 FROM FILES WHERE OBJECT_TYPE = ?", Integer.class, objectType);
	}
	
	public int getRandomId() {
		Random random = new Random();
		int randomInt = random.nextInt();
		log.debug("randomInt : {}", randomInt);
		return randomInt;
	}
	
}
