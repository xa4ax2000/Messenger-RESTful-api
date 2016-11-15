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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.tutorial.javabrains.messenger.exception.DataNotFoundException;
import org.tutorial.javabrains.messenger.model.Profile;
import org.tutorial.javabrains.messenger.service.ProfileService;

@Path("/profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {

	/*--------------------------Private Class Variables--------------------------*/
	
	private ProfileService profileService = new ProfileService();
	
	/*---------------------------Path("/profiles)--------------------------------*/
	
	@GET
	public List<Profile> getProfiles(@QueryParam("year") int year,
										@QueryParam("start") int start,
										@QueryParam("size") int size){
		if(year > 0 && start == 0 && size == 0){
			List<Profile> allProfilesForYear = profileService
					.getAllProfilesForYear(year);
			if(allProfilesForYear == null)
				throw new DataNotFoundException("Profiles within year " + 
						year + " was not found.");
			return allProfilesForYear;
		}
		else if(start >= 0 && size > 0 && year == 0){
			List<Profile> allProfilesPaginated = profileService
					.getAllProfilesPaginated(start, size);
			if(allProfilesPaginated == null)
				throw new DataNotFoundException("The page range (beginning at " 
						+ start + " displaying " + size + " profiles) was not found.");
			return allProfilesPaginated;
		}
		else if(year > 0 && start >= 0 && size > 0){
			List<Profile> allProfilesForYearPaginated = profileService
					.getAllProfilesForYearPaginated(year, start, size);
			if(allProfilesForYearPaginated == null)
				throw new DataNotFoundException("The page range for the year: " 
						+ year + " (first entry beginning at " + start 
						+ " displaying " + size + " profiles) was not found.");
			return allProfilesForYearPaginated;
		}
		List<Profile> allProfiles = profileService.getAllProfiles();
		if(allProfiles == null)
			throw new DataNotFoundException("No profiles were found");
		return allProfiles;
	}
	
	@POST
	public Response addProfile(@Context UriInfo uriInfo, Profile profile){
		Profile newProfile = profileService.addProfile(profile);
		String profileName = newProfile.getProfileName();
		URI uri = uriInfo.getAbsolutePathBuilder().path(profileName).build();
		return Response.created(uri)
				.entity(newProfile)
				.build();
	}
	
	@DELETE
	public void deleteProfiles(){
		profileService.deleteAllProfiles();
	}
	
	/*-----------------------Path("/profiles/{profileName}")---------------------*/
	
	@GET
	@Path("/{profileName}")
	public Profile getProfile(@PathParam("profileName") String profileName){
		Profile profile = profileService.getProfile(profileName);
		if(profile == null)
			throw new DataNotFoundException("Profile with profile name: " 
					+ profileName + " was not found");
		return profile;
	}
	
	@PUT
	@Path("/{profileName}")
	public Profile updateProfile(@PathParam("profileName") String profileName, Profile profile){
		Profile updatedProfile = profileService.updateProfile(profile, profileName);
		if(updatedProfile == null)
			throw new DataNotFoundException("Profile with profile name: " 
					+ profileName + " was not found");
		return updatedProfile;
	}
	
	@DELETE
	@Path("/{profileName}")
	public void deleteProfile(@PathParam("profileName") String profileName){
		profileService.removeProfile(profileName);
	}
}
