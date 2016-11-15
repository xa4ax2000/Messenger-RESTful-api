package org.tutorial.javabrains.messenger.service;

import org.tutorial.javabrains.messenger.database.DatabaseClass;
import org.tutorial.javabrains.messenger.model.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MessageService {

	/*--------------------------Private Class Variables--------------------------*/
	
	private static Map<Long, Message> messages = DatabaseClass.getMessages();
	
	/*-------------------------------Constructor---------------------------------*/
	
	public MessageService(){
		
	}
	
	/*------------------------Static Block to Fill "Database"--------------------*/
	
	static{
		  messages.put(1L, new Message(1, "Hello World!", "Andrew"));
		  messages.put(2L, new Message(2, "Hello Jersey!", "Andrew"));
		  messages.put(3L, new Message(3, "Hello 1!", "Andrew"));
		  messages.put(4L, new Message(4, "Hello 2orld!", "Andrew"));
		  messages.put(5L, new Message(5, "Hello 3!", "Andrew"));
		  messages.put(6L, new Message(6, "Hello W4!", "Andrew"));
		  messages.put(7L, new Message(7, "Hello 5rld!", "Andrew"));
		  messages.put(8L, new Message(8, "Hello 6!", "Andrew"));
		} 
	
	/*-------------------------Path("/messages")Methods--------------------------*/
	
	//GET All Messages Method(s)--------------------------------------------------
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year){
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for(Message message : messages.values()){
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year)
				messagesForYear.add(message);
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size){
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if(start > list.size())
			return null;
		if(start+size > list.size())
			return list.subList(start, list.size());
		return list.subList(start, start+size);
	}
	
	public List<Message> getAllMessagesForYearPaginated(int year, int start, int size){
		ArrayList<Message> list = new ArrayList<Message>(getAllMessagesForYear(year));
		if(start > list.size())
			return null;
		if(start+size > list.size())
			return list.subList(start, list.size());
		return list.subList(start, start+size);
	}
	
	//DELETE All Messages Method--------------------------------------------------
	public void deleteAllMessages(){
		messages.clear();
	}
	
	//POST Message Method---------------------------------------------------------
	public Message addMessage(Message message){
		System.out.println("accessed");
		message.setId(messages.size()+1); //there is a problem with this algorithm
		message.setCreated(new Date());
		messages.put(message.getId(), message);
		return message;
	}
	
	/*------------------------Path("/messages/{messageId}")----------------------*/
	
	//GET Message Method----------------------------------------------------------
		public Message getMessage(Long id){
			return messages.get(id);
		}
	
	//PUT Message Method----------------------------------------------------------
	public Message updateMessage(Message message, long id){
		if(messages.get(id) == null)
			return null;
		message.setId(id);
		message.setCreated(messages.get(id).getCreated());
		message.setAuthor(messages.get(id).getAuthor());
		messages.put(message.getId(), message);
		return message;
	}
	
	//DELETE Message Method-------------------------------------------------------
	public Message removeMessage(long id){
		return messages.remove(id);
	}
	
}
