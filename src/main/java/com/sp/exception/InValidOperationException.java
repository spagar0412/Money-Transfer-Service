package com.sp.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InValidOperationException extends RuntimeException implements ExceptionMapper<InValidOperationException> 
{
	public InValidOperationException() {
        super("Operation can't be performed-Invalid action or input");
    }

    public InValidOperationException(String message) {
        super(message);
    }

    @Override
    public Response toResponse(InValidOperationException exception) 
    {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .type("text/plain")
                .build();
    }
}

