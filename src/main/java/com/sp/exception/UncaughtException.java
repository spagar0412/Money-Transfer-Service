package com.sp.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
 
@Provider
public class UncaughtException extends Throwable implements ExceptionMapper<Throwable>
{
    @Override
    public Response toResponse(Throwable exception)
    {
        return Response
        		.status(Response.Status.INTERNAL_SERVER_ERROR)
        		.entity("Something went wrong. Please try again after sometime !")
        		.type("text/plain")
        		.build();
    }
}