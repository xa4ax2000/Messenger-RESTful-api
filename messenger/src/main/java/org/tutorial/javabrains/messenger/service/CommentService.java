package org.tutorial.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.tutorial.javabrains.messenger.database.DatabaseClass;
import org.tutorial.javabrains.messenger.model.Comment;
import org.tutorial.javabrains.messenger.model.Message;

public class CommentService {

	/*--------------------------Private Class Variables--------------------------*/

	private static Map<Long, Message> messages = DatabaseClass.getMessages();
	
	/*-------------------------------Constructor---------------------------------*/

	public CommentService(){
		
	}
	
	/*------------------------Static Block to Fill "Database"--------------------*/
	
	static{
		messages.get(1L).getComments().put(1L, new Comment(1L, "First Comment!", "Andrew Hyun"));
		messages.get(1L).getComments().put(2L, new Comment(2L, "Second Comment!", "Test User1"));
		messages.get(2L).getComments().put(3L, new Comment(3L, "Third Comment!", "Test User2"));
	} 
	
	/*-------------------------Path(".../comments")Methods-----------------------*/

	//GET All Comments Method(s)--------------------------------------------------
	public List<Comment> getComments(long messageId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	//DELETE All Comments Method--------------------------------------------------
	public void deleteAllComments(long messageId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comments.clear();
	}
	
	//POST Message Method---------------------------------------------------------
	public Comment addComment(long messageId, Comment comment){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1); //fix algorithm
		comment.setCreated(new Date());
		comments.put(comment.getId(), comment);
		return comment;
	}

	/*------------------------Path("/messages/{messageId}")----------------------*/
	
	//GET Message Method----------------------------------------------------------
	public Comment getComment(long messageId, long commentId){
		Message message = messages.get(messageId);
		if (message == null){
			return null;
		}
		Map<Long, Comment> comments = message.getComments();
		Comment comment = comments.get(commentId);
		if(comment == null){
			return null;
		}
		return comment;
	}
	
	//PUT Message Method----------------------------------------------------------
	public Comment updateComment(long messageId, long commentId, Comment comment){
		Message message = messages.get(messageId);
		if(message == null)
			return null;
		Map<Long, Comment> comments = message.getComments();
		Comment commentIdCheck = comments.get(commentId);
		if(commentIdCheck == null)
			return null;
		comment.setId(commentId);
		comment.setCreated(commentIdCheck.getCreated());
		comment.setAuthor(commentIdCheck.getAuthor());
		comments.put(commentId, comment);
		return comment;
	}
	
	//DELETE Message Method-------------------------------------------------------
	public Comment removeComment(long messageId, long commentId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}
