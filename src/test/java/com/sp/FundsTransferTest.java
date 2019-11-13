package com.sp;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.sp.dto.Account;
import com.sp.exception.InValidOperationException;
import com.sp.model.Transfer;
import com.sp.service.AccountService;
import com.sp.service.TransactionService;

@TestInstance(Lifecycle.PER_CLASS)
public class FundsTransferTest 
 {

    private HttpServer server;
    private WebTarget target;
    private TransactionService txnService;
    private AccountService accService;
    private Account debitAcc;
    private Account creditAcc;
    
    @BeforeAll
    public void setUp() throws Exception 
    {
        // start the server
        server = Server.startServer();
        // create the client
        Client c = ClientBuilder.newClient();
        target = c.target(Server.BASE_URI); 
        txnService = TransactionService.getInstance();
        accService = AccountService.getInstance();
        debitAcc = accService.addAccount(setAccount("12345678910120", "123123", "GBP"));
        creditAcc = accService.addAccount(setAccount("12345678910120", "123123", "GBP"));
    }

    @AfterAll
    public void tearDown() throws Exception {
        server.shutdown();
    }

    /**
     * Test to see that the fundstransfer works as expected end to end.
     */
    @Test
    public void testGetIt() 
    {
    	Transfer tx = setTransfer(debitAcc.getId(), creditAcc.getId(), "10", "Transfer 10 pounds");
    	Response response = target.path("/transactions/fundstransfer")
    			.request(MediaType.APPLICATION_JSON_TYPE)
    			.post(Entity.entity(tx, MediaType.APPLICATION_JSON_TYPE));
    	
    	String result = response.readEntity(String.class);
        assertTrue(result.contains("\"state\":\"COMPLETED\"") || result.contains("\"state\":\"CREATED\"") );
    }
    
    @Test
    void validateWithNulls() 
    {
    	InValidOperationException e = assertThrows(InValidOperationException.class,
                () ->  txnService.transfer(null));
        assertEquals("Transfer cannot be null", e.getLocalizedMessage());

        //debit account can not be null
       e = assertThrows(InValidOperationException.class,
                () -> txnService.transfer(setTransfer(null, "123123124", "1234", "Test trasaction")));
        assertEquals("Account Id cannot be null", e.getLocalizedMessage());
        
        ////credit account can not be null
        e = assertThrows(InValidOperationException.class,
                () -> txnService.transfer(setTransfer("123123124", null, "1234", "Test trasaction")));
        assertEquals("Account Id cannot be null", e.getLocalizedMessage());

        //Amount cannot be null
        e = assertThrows(InValidOperationException.class,
                () -> txnService.transfer(setTransfer("123123124", "1234", "Test trasaction")));
        assertEquals("Amount cannot be null or negative", e.getLocalizedMessage());
    }
    
	@Test
    void validateWithInValidValues() 
    {
      //Amount cannot negative
        InValidOperationException e = assertThrows(InValidOperationException.class,
                () -> txnService.transfer(setTransfer("123123124", "123345456", "-123", "Test trasaction")));
        assertEquals("Amount cannot be null or negative", e.getLocalizedMessage());
        
        //Invalid debit account
        e = assertThrows(InValidOperationException.class,
                () -> txnService.transfer(setTransfer("123123124", "123345456", "123", "Test trasaction")));
        assertTrue(e.getLocalizedMessage().contains("Account doesn't exist"));
        
        //Invalid credit account
        e = assertThrows(InValidOperationException.class,
                () -> txnService.transfer(setTransfer("123123124", "123345456", "123", "Test trasaction")));
        assertTrue(e.getLocalizedMessage().contains("Account doesn't exist"));

        // debit and credit account  can not be same
        e = assertThrows(InValidOperationException.class,
                () -> txnService.transfer(setTransfer("12345678910120", "12345678910120", "123", "Test trasaction")));
        assertEquals("Accounts must be different", e.getLocalizedMessage());
    }

    private Transfer setTransfer(String source, String target, String amount, String  description) 
    {
    	Transfer tsr = new Transfer();	
    	tsr.setSource(source);
    	tsr.setTarget(target);
    	tsr.setAmount(new BigDecimal(amount));
    	tsr.setDescription(description);
		return tsr;
	}
    
    private Transfer setTransfer(String source, String target, String  description) //Added as new BigDecimal(null) iss not handled in native".math" package 
    {
    	Transfer tsr = new Transfer();	
    	tsr.setSource(source);
    	tsr.setTarget(target);
    	tsr.setAmount(null);
    	tsr.setDescription(description);
		return tsr;
	}
    
    private Account setAccount(String number, String balance, String currency) 
    {
    	return new Account(number, new BigDecimal(balance), currency, true);	
	}

	/*
	 * public static void main(String[] args) throws Exception { FundsTransferTest
	 * test = new FundsTransferTest(); test.setUp(); //before
	 * 
	 * System.out.println("Start"); test.testGetIt(); test.validateWithNulls();
	 * test.validateWithInValidValues(); System.out.println("End");
	 * 
	 * test.tearDown(); //after }
	 */
}
