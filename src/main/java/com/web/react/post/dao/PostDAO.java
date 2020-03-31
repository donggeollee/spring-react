package com.web.react.post.dao;

import org.springframework.stereotype.Repository;

import com.web.react.post.model.Post;

public interface PostDAO {
	
	public Object createPost(Post post);
	
	public Object readPostById(int id);
	
	public Object readPostAll();
	
	public int updatePost(Post post);
	
	public int deletePost(int id);

}
