package com.sp.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InsufficientFundException extends RuntimeException implements ExceptionMapper<InsufficientFundException> 
{
	public InsufficientFundException() {
        super("Operation can't be performed :: Insufficient funds.");
    }

    public InsufficientFundException(String message) {
        super(message);
    }

    @Override
    public Response toResponse(InsufficientFundException exception) 
    {
        return Response
                .status(Response.Status.CONFLICT)
                .entity(exception.getMessage())
                .type("text/plain")
                .build();
    }
}
