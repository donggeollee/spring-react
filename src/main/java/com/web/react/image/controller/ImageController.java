package com.web.react.image.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.react.utils.Result;

@RequestMapping(value = "/image")
@RestController
public class ImageController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	private static String UPLOAD_DIRECTORY = "C:/DEV/attachments";
	
//	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
//	@RequestMapping(value = "/create", method= RequestMethod.POST, headers = ("content-type=multipart/*"))
//	public Object createImage(MultipartHttpServletRequest request) {
	@PostMapping(value = "/create")
	public Object createImage(@RequestParam ArrayList<MultipartFile> multipartFileList, HttpServletRequest request ) {
		MultipartFile file = multipartFileList.get(0);
	
		Result result = Result.newResult();
		
		
//		try {
//			saveFile(file.getInputStream(), UPLOAD_DIRECTORY);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		log.debug("request.getContextPath(); : {}", request.getContextPath()); 
		log.debug("file.getContentType() : {}", file.getContentType()); // 파일 타입 ex) text, video/mp4, application/pdf, ...
		log.debug("file.getName() : {} ", file.getName()); // 파라미터 이름
		log.debug("file.getOriginalFilename() : {}", file.getOriginalFilename()); // 파일명
		log.debug("file.getSize() : {}", file.getSize()); // 파일크기
		log.debug("file.getSize() : {}", file.getSize()); // 파일크기
		 
		return null;
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
	
//	private void saveFile(InputStream inputStream, String savePath) {
//
//		new File("savePath");
//		BufferedInputStream bis = new BufferedInputStream(inputStream, 1028);
//		byte[] fileSize = new byte[1024]; 
//		bis.read(fileSize, off, );
//	}
}
