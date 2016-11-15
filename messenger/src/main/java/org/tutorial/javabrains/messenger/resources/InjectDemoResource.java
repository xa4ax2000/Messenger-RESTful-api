package org.tutorial.javabrains.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

//This Class is not used in the messengerApp
//This is to demonstrate the @Param

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {
//So far we are familiar with @QueryParam and @PathParam
/*--------------------------------@PathParam--------------------------------------*/
//               Resource Path in URI can be taken as parameter
/*--------------------------------@QueryParam-------------------------------------*/
//			  A specific query in URI (?) can be taken as parameter
	
	
/*--------------------------------Class Methods-----------------------------------*/
	
	//Testing out different Parameters in method----------------------------------
	@GET
	@Path("/annotations")
	public String getParamsUsingAnnotations(@MatrixParam("matrix") String matrixParam,
											@HeaderParam("customHeaderValue") int headerParam,
											@CookieParam("name") String cookie){
		return "@MatrixParam's value: " + matrixParam + 
				" @HeaderParam's value: " + String.valueOf(headerParam) +
				" @CookieParam's value: " + cookie;
	}
	
	
	//Testing out Context in method-----------------------------------------------
	/* Generally you want to use @Context when you are unfamiliar with the name of 
	 * a specific parameter. Knowing the special classes: UriInfo and HttpHeaders, 
	 * you can get a list of queryParams, cookieParams, headerParams, MatrixParams
	 * etc.*/
	@GET
	@Path("/context")
	public String getParamsUsingContext(@Context UriInfo uriInfo, 
										@Context HttpHeaders httpHeaders){
		String path = uriInfo.getAbsolutePath().toString();
		String cookies = httpHeaders.getCookies().toString();
		return "Path: " + path + "   Cookies: " + cookies;
	}
	
	//Testing out BeanParam in method---------------------------------------------
	/* Generally you want to use @BeanParam when you don't want to cluster call a 
	 * series of param annotations. You create a separate bean of parameters which
	 * contain your annotations. Then in your resource class, you can just point
	 * at the bean. The example is shown on the left in our messenger app in a 
	 * separate class -- MessageFilterBean*/
}
