package com.sp.service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import com.sp.dto.Account;
import com.sp.dto.Transaction;
import com.sp.enums.TransactionState;
import com.sp.exception.ProcessingFailedException;
import com.sp.model.Transfer;
import com.sp.repository.TransactionRepository;
import com.sp.validator.Validator;

public class TransactionService 
{
	
    private static final TransactionService INSTANCE = new TransactionService(TransactionRepository.getInstance());
    private final AccountService accService = AccountService.getInstance();
    private final CounterPartyService partyService = CounterPartyService.getInstance();
    private final TransactionRepository transactionRepo; 
    private TransactionService(TransactionRepository repository) 
    {
    	this.transactionRepo = repository;
    	
    } //for actual product , initialization for appropriate objects can be added to constructor  

    public static TransactionService getInstance() {
        return INSTANCE;
    }

    public Transaction transfer(final Transfer tsr) 
    {
    	Validator.isTransferNull(tsr);
    	BigDecimal amount = tsr.getAmount();
    	String source_account_id = tsr.getSource();
    	String target_account_id = tsr.getTarget();
    	
    	Validator.validateIsAmountNegative(amount);
		Validator.isAccountIdNull(source_account_id);
		Validator.isAccountIdNull(target_account_id);
    	Validator.validateAccountIsDifferent(source_account_id, target_account_id);
    	
        Account fromAcc = accService.getAccount(source_account_id);
        
        Validator.isAccountExist(fromAcc);
        
        Validator.validateIsBalanceSufficient(fromAcc.getBalance(), amount);
        
        Account toAcc = accService.getAccount(target_account_id);
        boolean runNow = true;
        if(toAcc == null) 
        {
        	// Account might belong to OtherEntity/ Or bussines
        	// Look up for an account in Other couterEntry
        	toAcc = partyService.getAccount(target_account_id);
        	runNow = false; // need to create Payment 
        }
        
        Transaction transaction =  createTarnsaction(fromAcc, toAcc, amount ,tsr.getDescription());
        TransactionState state = transaction.getState();
        if(runNow)
        {
        	state = processTransfer(fromAcc, toAcc, amount) ;
    		transaction.setCompletedAt(LocalDateTime.now(Clock.systemDefaultZone()));
    		transaction.setState(state);
        }
        else
        {
        	LedgerService.getInstance().createPayment(transaction);//payment object creation for ledger management
        	transaction.setState(TransactionState.STARTED);
        }
        return transaction;
    }

	private Transaction createTarnsaction(Account source, Account target, BigDecimal amount, String desc) 
	{
		BigDecimal fee = calculateFees();// calcualte processing fees
		
		Transaction transaction = new Transaction(source.getId(), target.getId(), amount);
		transaction.setDescription(desc);
		transaction.setCurrencyCode(source.getCurrency());/* here currency code check and conversion can be added 
		                                                     Not considered for this task 			*/
		
		getTransactionRepo().add(transaction);
		return transaction;
	}

	private TransactionState processTransfer(Account fromAcc, Account toAcc, BigDecimal amount) 
    {
		TransactionState state = TransactionState.STARTED;
        try 
        {
        	final Lock debitLock = fromAcc.getLock();
            if (debitLock.tryLock(Account.WAIT_TIME, TimeUnit.MILLISECONDS)) 
            {
                try 
                {
                    final Lock creditLock = toAcc.getLock();
                    if (creditLock.tryLock(Account.WAIT_TIME, TimeUnit.MILLISECONDS)) 
                    {
                        try 
                        {
                            if (accService.debit(fromAcc, amount)) 
                            {
                                if (accService.credit(fromAcc, amount)) 
                                {
                                    state = TransactionState.COMPLETED;
                                    return state;
                                }
                            }
                            state = TransactionState.FAILED;
                        } finally { creditLock.unlock(); }
                    } 
                    else 
                    {
                        state = TransactionState.FAILED;
                    }
                } finally { debitLock.unlock(); }
            } else {
                state = TransactionState.FAILED;
            }
        } catch (InterruptedException e) 
        {
            state = TransactionState.FAILED;
            throw new ProcessingFailedException(e.getLocalizedMessage());
        }
        return state;
    }
	private BigDecimal calculateFees() 
	{
		return null; // processing fee calculation --- not implemented. 
	}

	public TransactionRepository getTransactionRepo()
	{
		return transactionRepo;
	}
	public Transaction getById(String id)
	{
		return transactionRepo.getById(id);
	}
}
