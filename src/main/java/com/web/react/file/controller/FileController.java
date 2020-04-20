package com.web.react.file.controller;

import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.web.react.file.dao.FileDAO;
import com.web.react.utils.Result;

@RequestMapping(value = "/file")
@RestController
public class FileController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	FileDAO fileDAO;
	
	private static String UPLOAD_DIRECTORY = "C:"+ File.separator + "DEV" + File.separator + "attachments";
	
//	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
//	@RequestMapping(value = "/create", method= RequestMethod.POST, headers = ("content-type=multipart/*"))
//	public Object createImage(MultipartHttpServletRequest request) {
	@PostMapping(value = "/create")
	public Object createImage(@RequestParam ArrayList<MultipartFile> fileList, HttpServletRequest request ) {
		
		Result result = Result.newResult();
		
		try {
			MultipartFile file = fileList.get(0);
			 
			String savePath = UPLOAD_DIRECTORY + File.separator + getNowLocalDate();
			String fileName = UUID.randomUUID() +  "_" + file.getOriginalFilename();
			File temp = new File(savePath);
			
			if( !temp.exists() ) {
				temp.mkdirs();
			}
			
			file.transferTo(new File(savePath + File.separator + fileName));
			
			// DB 저장
			int objectId = fileDAO.createFile(3, fileName, file.getSize());
			result.getData().put("objectId", objectId);
			
		} catch(Exception e) {
			log.error(e.getLocalizedMessage());
		}
		
		return result;
	}
	
	@GetMapping("/readAll")
	public Object readAllImage(HttpServletRequest request) {
		return null;
	}
	
	@GetMapping("/read/{id}")
	public Object readImageById(@RequestBody String imageId) {
		
		return null;
	}
	
	@PostMapping("/delete/{id}")
	public Object deleteImage(@RequestBody String imageId, @PathVariable int id) {
		return null;
	}
	 
	@PostMapping("/delete")
	public Object deleteImage(@RequestBody String imageFile) {
		return null;
	}
	
	private String getNowLocalDate() {
		LocalDate ld = LocalDate.now();
		return String.valueOf(ld.getYear()) 
				+ File.separator + String.valueOf(ld.getMonthValue()) 
				+ File.separator + String.valueOf(ld.getDayOfMonth()) ;
	}
}
