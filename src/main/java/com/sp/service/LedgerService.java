package com.sp.service;

import com.sp.dto.Payment;
import com.sp.dto.Transaction;
import com.sp.enums.TransactionState;

/**
 * LedgerService class for handling payments(Scheduled and non-scheduled) which can be of following types.
 * 
 * -- Accounts of Similar Bank,  Same Branch,       Similar Country 
 * -- Accounts of Similar Bank,  Different Branch,  Similar Country 
 * -- Accounts of Similar Bank,  Different Branch,  Different Country (Currency changes here)
 * 
 * -- Accounts of Different Bank, Similar Country
 * -- Accounts of Different Bank, Different Country (Currency changes here)
 *
 */
public class LedgerService 
{
	private static final LedgerService INSTANCE = new LedgerService();
    private final AccountService accService = AccountService.getInstance();
    private final TransactionService txnService = TransactionService.getInstance();
    private LedgerService() 
    {}

    public static LedgerService getInstance() 
    {
        return INSTANCE;
    }
    
    public Payment createPayment(final Transaction trx) 
    {
    	return new Payment();
    }
    
    public void schedulePayments(final Transaction trx) 
    {
    	
    	// here Job-schedulerService will be called.
    	//not implemented for this task
    }
    
    public void cancelPayments(final Transaction trx) 
    {
    	throw new UnsupportedOperationException();
    	//not implemented for this task
    }
    
    public void ProcessPayments(final Transaction trx) 
    {
    	throw new UnsupportedOperationException();
    	//not implemented for this task
    }
	
}
