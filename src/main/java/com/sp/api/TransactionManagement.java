package com.sp.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sp.dto.Transaction;
import com.sp.exception.InValidOperationException;
import com.sp.model.Transfer;
import com.sp.service.TransactionService;

/**
 * Transactions resource (exposed at "transactions" path)
 */
@Path("/transactions")
public class TransactionManagement {

    private final TransactionService transactionService = TransactionService.getInstance();

    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return Transaction Object that will be returned as a JSON response.
     */
    @Path("/fundstransfer")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fundsTransfer(Transfer transfer) 
    {
    	System.out.println("initiated");
    	if(transfer == null)
    	{
    		throw new InValidOperationException("Paylod can not be null");
    	}
        Transaction result = transactionService.transfer(transfer);
        return Response.ok().entity(result).build();
    }
    
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return Transaction Object that will be returned as a JSON response.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String id) 
    {
    	System.out.println("initiated");
    	if(id == null)
    	{
    		throw new InValidOperationException("inVaild trnasaction ID::Id  not be null");
    	}
        Transaction result = transactionService.getById(id);
        
        if (result == null)
            return Response.noContent().build();

        return Response.ok(result).build();
    }

}
