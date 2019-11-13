package com.sp.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sp.dto.Account;
import com.sp.service.AccountService;

/**
 * Accounts resource (exposed at "accounts" path)
 */
@Path("/accounts")
public class AccountManagement 
{
    private final AccountService accService = AccountService.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() 
    {
    	Collection<Account>  allAcc = accService.getgetAllAccounts();
    	if(allAcc != null && allAcc.size()>0) 
    		return Response.ok(allAcc).build();
    	 
    	return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountById(@PathParam("id") String id) 
    {
        Account account = accService.getAccount(id);
        if (account == null)
            return Response.noContent().build();

        return Response.ok(account).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewAccount(Account account) 
    {
    	accService.addAccount(account);
        return Response.ok(account).build();
    }


}
