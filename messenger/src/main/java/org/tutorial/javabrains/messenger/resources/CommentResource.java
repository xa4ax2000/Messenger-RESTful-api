package org.tutorial.javabrains.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.tutorial.javabrains.messenger.exception.DataNotFoundException;
import org.tutorial.javabrains.messenger.model.Comment;
import org.tutorial.javabrains.messenger.model.ErrorMessage;
import org.tutorial.javabrains.messenger.service.CommentService;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

	/*--------------------------Private Class Variables--------------------------*/
	
	private CommentService commentService = new CommentService();
	
	/*---------------------------Path(".../comments)-----------------------------*/
	
	@GET
	public List<Comment> getComments(@PathParam("messageId") long messageId){
		List<Comment> allComments = commentService.getComments(messageId);
		if(allComments == null)
			throw new DataNotFoundException("No comments were found.");
		return allComments;
	}
	
	@POST
	public Response addComment(@Context UriInfo uriInfo, @PathParam("messageId") long messageId, 
								Comment comment){
		Comment newComment = commentService.addComment(messageId, comment);
		String commentId = String.valueOf(newComment.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(commentId).build();
		return Response.created(uri)
				.entity(newComment)
				.build();
	}
	
	@DELETE
	public void deleteComments(@PathParam("messageId") long messageId){
		commentService.deleteAllComments(messageId);
	}
	
	/*-----------------------Path(".../comments/{commentId}")--------------------*/
	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("messageId") long messageId, 
							  @PathParam("commentId") long commentId){
		ErrorMessage errorMessage = new ErrorMessage("Page Not Found", 404, "[Doc Web]");
		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
		Comment comment = commentService.getComment(messageId, commentId);
		if(comment == null){
			System.out.println("null");
			throw new WebApplicationException(response);
		}
		return comment;
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("messageId") long messageId,
			                     @PathParam("commentId") long commentId,
			                     Comment comment){
		ErrorMessage errorMessage = new ErrorMessage("Page Not Found", 404, "[Doc Web]");
		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
		Comment updatedComment = commentService.updateComment(messageId, commentId, comment);
		if(updatedComment == null)
			throw new WebApplicationException(response);
		return updatedComment;
	}
	
	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("messageId") long messageId, 
							  @PathParam("commentId") long commentId){
		commentService.removeComment(messageId, commentId);
	}
	
	
}
