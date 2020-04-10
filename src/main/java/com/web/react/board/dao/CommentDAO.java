package com.web.react.board.dao;

public interface CommentDAO {
	
	int createComment(int postId, int userId, String comment);
	
	int readComment(int postId);
	
	int readCommentById(int commentId);
	
	int readCommentByUserId(int userId);
	
	int updateComment(int commentId, String comment);
	
	int deleteComment(int commentId);

}
