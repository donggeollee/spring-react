package com.web.react.file.dao;

import java.util.ArrayList;
import java.util.Map;

public interface FileDAO {

	int createFile(int objectType, String fileName, long fileSize);
	
	int updateFileId(int ObjeccdType, int ObjectId, int updateId); 
	
	ArrayList<Map<String,Object>> readFileListByPostId();
	
	int getFileIdByObjectType(int objectType);
	
	int getRandomId();
	
}
