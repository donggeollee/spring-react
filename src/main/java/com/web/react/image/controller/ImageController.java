package com.web.react.image.controller;

import java.io.File;
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

@RequestMapping(value = "/image")
@RestController
public class ImageController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
//	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
//	@RequestMapping(value = "/create", method= RequestMethod.POST, headers = ("content-type=multipart/*"))
	@PostMapping(value = "/create")
//	public Object createImage(MultipartHttpServletRequest request) {
	public Object createImage(@RequestParam ArrayList<MultipartFile> files ) {
		
		log.debug("files : {}", files);
//		log.debug("file : {}", file.getBytes());
//		log.debug("file.getName() : {}", file.getName());
//		log.debug("file.getOriginalFilename() : {}", file.getOriginalFilename());
//		log.debug("file : {}", file.getInputStream());
//		log.debug("file.getSize() : {}", file.getSize());
//		log.debug("file.getContentType() : {}", file.getContentType());
//		log.debug("file.getClass() : {}", file.getClass());
		 
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
	

}
