package org.tutorial.javabrains.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.tutorial.javabrains.messenger.exception.DataNotFoundException;
import org.tutorial.javabrains.messenger.model.Link;
import org.tutorial.javabrains.messenger.model.Message;
import org.tutorial.javabrains.messenger.resources.beans.MessageFilterBean;
import org.tutorial.javabrains.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	/*--------------------------Private Class Variables--------------------------*/
	
	MessageService messageService = new MessageService();

	/*---------------------------Path("/messages)--------------------------------*/
	
	@GET
	public List<Message> getMessages(@Context UriInfo uriInfo, @BeanParam MessageFilterBean filterBean){
		if(filterBean.getYear() > 0 && filterBean.getStart() == 0 && filterBean.getSize() == 0){
			List<Message> messagesForYear = messageService.getAllMessagesForYear(filterBean.getYear());
			if (messagesForYear == null)
				throw new DataNotFoundException("Messages within year " + filterBean.getYear() + " was not found.");
			for(Message eachMessage : messagesForYear){
				if(eachMessage.getLinks().isEmpty()){
					String uri = getUriForSelf(uriInfo, eachMessage);
					eachMessage.addLink(uri, "self");
				}
			}
			return messagesForYear;
		}
		else if(filterBean.getStart() >= 0 && filterBean.getSize() > 0 && filterBean.getYear() == 0){
			List<Message> messagesPaginated = messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
			if (messagesPaginated == null)
				throw new DataNotFoundException("The page range (beginning at " + filterBean.getStart() + " displaying "
						+ filterBean.getSize() + " messages) was not found.");
			for(Message eachMessage : messagesPaginated){
				if(eachMessage.getLinks().isEmpty()){
					String uri = getUriForSelf(uriInfo, eachMessage);
					eachMessage.addLink(uri, "self");
				}
			}
			return messagesPaginated;
		}
		else if(filterBean.getYear() > 0 && filterBean.getStart() >= 0 && filterBean.getSize() > 0){
			List<Message> messagesForYearPaginated = messageService.getAllMessagesForYearPaginated(
					filterBean.getYear(), filterBean.getStart(), filterBean.getSize());
			if (messagesForYearPaginated == null)
				throw new DataNotFoundException("The page range for the year: " + filterBean.getYear() + " (first entry beginning at " + filterBean.getStart() + " displaying "
						+ filterBean.getSize() + " messages) was not found.");
			for(Message eachMessage : messagesForYearPaginated){
				if(eachMessage.getLinks().isEmpty()){
					String uri = getUriForSelf(uriInfo, eachMessage);
					eachMessage.addLink(uri, "self");
				}
			}
			return messagesForYearPaginated;
		}
		List<Message> messages = messageService.getAllMessages();
		if(messages == null)
			throw new DataNotFoundException("No messages were found");
		for(Message eachMessage : messages){
			if(eachMessage.getLinks().isEmpty()){
				String uri = getUriForSelf(uriInfo, eachMessage);
				eachMessage.addLink(uri, "self");
			}
		}
		return messages;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(Long.toString(message.getId()))
				.build()
				.toString();
		return uri;
	}
	
	@POST
	public Response addMessage(@Context UriInfo uriInfo, Message message){
		Message newMessage = messageService.addMessage(message);
		String messageId = newMessage.toString();
		URI uri = uriInfo.getAbsolutePathBuilder().path(messageId).build();
		return Response.created(uri)
				.entity(newMessage)
				.build();
	}
	
	@DELETE
	public void deleteMessages(){
		messageService.deleteAllMessages();
	}
	
	/*-----------------------Path("/messages/{messageId}")-----------------------*/
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@Context UriInfo uriInfo, @PathParam("messageId") long id){
		Message message = messageService.getMessage(id);
		if(message == null)
			throw new DataNotFoundException("Message with id: " + id + 
					" was not found");
		if(message.getLinks().isEmpty()){
			String uri = getUriForSelf(uriInfo, message);
			message.addLink(uri, "self");
		}
		return message;
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message){
		Message updatedMessage = messageService.updateMessage(message, id);
		if(updatedMessage == null)
			throw new DataNotFoundException("Message with id: " + id + 
					" was not found");
		return message;
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id){
		messageService.removeMessage(id);
	}
	
	/*-----------------------Create Sub-Resource Directory-----------------------*/
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource(){
		return new CommentResource();
	}
}
