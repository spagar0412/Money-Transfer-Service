package com.sp.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ProcessingFailedException extends RuntimeException implements ExceptionMapper<ProcessingFailedException> 
{
	public ProcessingFailedException() 
	{
        super("Something went wrong. Please try again after sometime !");
    }

    public ProcessingFailedException(String message) {
        super(message);
    }


	@Override
	public Response toResponse(ProcessingFailedException exception) 
	{
        return Response
        		.status(Response.Status.INTERNAL_SERVER_ERROR)
        		.entity("Something went wrong. Please try again after sometime !")
        		.type("text/plain")
        		.build();
    }
}

