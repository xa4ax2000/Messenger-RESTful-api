package org.tutorial.javabrains.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import org.tutorial.javabrains.messenger.model.ErrorMessage;

//@Provider  This annotation lets jax-rs know that this is a mapper.
//This annotation has been disabled so we can utilize and observe web application exception
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 500, "[website documentation]");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errorMessage)
				.build();
	}
	
}
