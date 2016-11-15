package org.tutorial.javabrains.messenger.database;

import java.util.HashMap;
import java.util.Map;

import org.tutorial.javabrains.messenger.model.Message;
import org.tutorial.javabrains.messenger.model.Profile;

public class DatabaseClass {

	/*---------------------Static "Database" Containers-------------------------*/
	
	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<Long, Profile> profiles = new HashMap<>();
	
	/*------------------------------Getters-------------------------------------*/
	
	public static Map<Long, Message> getMessages(){
		return messages;	
	}
	
	public static Map<Long, Profile> getProfiles(){
		return profiles;
	}

}
