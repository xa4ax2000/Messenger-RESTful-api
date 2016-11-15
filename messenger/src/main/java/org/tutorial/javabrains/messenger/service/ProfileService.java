package org.tutorial.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.tutorial.javabrains.messenger.database.DatabaseClass;
import org.tutorial.javabrains.messenger.model.Profile;

public class ProfileService {

	private static Map<Long, Profile> profiles = DatabaseClass.getProfiles();
	
	/*-------------------------------Constructor---------------------------------*/
	
	public ProfileService(){
		
	}
	
	/*------------------------Static Block to Fill "Database"--------------------*/
	
	static{
		profiles.put(1L, new Profile(1L, "ProfileName1", "Andrew", "Hyun"));
		profiles.put(2L, new Profile(2L, "ProfileName2", "Andy", "Hun"));
		profiles.put(3L, new Profile(3L, "ProfileName3", "J", "Hyun"));
		profiles.put(4L, new Profile(4L, "ProfileName4", "A", "Hyun"));
		profiles.put(5L, new Profile(5L, "ProfileName5", "I", "Hyun"));
	}
	
	/*-------------------------Path("/profiles")Methods--------------------------*/
	
	//GET All Profiles Method(s)--------------------------------------------------
	public List<Profile> getAllProfiles(){
		return new ArrayList<Profile>(profiles.values());
	}
	
	public List<Profile> getAllProfilesForYear(int year){
		ArrayList<Profile> profilesForYear = new ArrayList<Profile>();
		Calendar cal = Calendar.getInstance();
		for(Profile profile : profiles.values()){
			cal.setTime(profile.getCreated());
			if(cal.get(Calendar.YEAR) == year)
				profilesForYear.add(profile);
		}
		return profilesForYear;
	}
	
	public List<Profile> getAllProfilesPaginated(int start, int size){
		ArrayList<Profile> list = new ArrayList<Profile>(profiles.values());
		if(start > list.size())
			return null;
		if(start+size > list.size())
			return list.subList(start, list.size());
		return list.subList(start, start+size);
	}
	
	public List<Profile> getAllProfilesForYearPaginated(int year, int start, int size){
		ArrayList<Profile> list = new ArrayList<Profile>(getAllProfilesForYear(year));
		if(start > list.size())
			return null;
		if(start+size > list.size())
			return list.subList(start, list.size());
		return list.subList(start, start+size);
	}
	
	//DELETE All Profiles Method--------------------------------------------------
	public void deleteAllProfiles() {
		profiles.clear();
	}
	
	//POST Profile Method---------------------------------------------------------
	public Profile addProfile(Profile profile){
		profile.setId(profiles.size()+1);//Fix algorithm here
		profile.setCreated(new Date());
		profiles.put(profile.getId(), profile);
		return profile;
	}
	
	/*-----------------------Path("/profiles/{profileName}")---------------------*/
	
	//GET Profile Method----------------------------------------------------------
	public Profile getProfile(String profileName){
		for(Profile x : profiles.values()){
			if(x.getProfileName().equals(profileName))
				return x;
		}
		return null;
	}
	
	//PUT Profile Method----------------------------------------------------------
	public Profile updateProfile(Profile profile, String profileName){
		if(this.getProfile(profileName) == null)
			return null;
		profile.setProfileName(this.getProfile(profileName).getProfileName());
		profile.setCreated(this.getProfile(profileName).getCreated());
		profile.setId(this.getProfile(profileName).getId());
		profiles.put(profile.getId(), profile);
		return profile;
	}
	
	//DELETE Profile Method-------------------------------------------------------
	public Profile removeProfile(String profileName){
		if(this.getProfile(profileName) == null)
			return null;
		return profiles.remove(this.getProfile(profileName).getId());
	}

}
