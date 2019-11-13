package com.sp.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.ws.rs.ProcessingException;

import com.sp.dto.Account;
import com.sp.dto.AccountDetails;
import com.sp.exception.ProcessingFailedException;
import com.sp.repository.AccountRepotory;
import com.sp.validator.Validator;

public class AccountService 
{
    private static final AccountService INSTANCE = new AccountService(AccountRepotory.getInstance());

    private final AccountRepotory repo;

    private AccountService(AccountRepotory repository) 
    {
        this.repo = repository;
    }

    public static AccountService getInstance() {
        return INSTANCE;
    }

	public Collection<Account> getgetAllAccounts() 
	{
		return Collections.unmodifiableCollection(repo.getAll());
	}

	public Account getAccount(String id) 
	{
		return repo.getById(id);
	}
	
	public AccountDetails getAccountDetails(String id) 
	{
		return repo.getAccountDetailsBYId(id);
	}
	

	public Account addAccount(Account account) 
	{
		/*Assumption - validateKYC is not implemented as this should be handled by backOFfice
		 *  after for actual document validation. 
		 */
		
		//validateKYC()
		return repo.addAccount(account);
	}

	/*
	 * private boolean validateKYC() { return true; }
	 */

	public boolean debit(Account account, BigDecimal amount) 
    {
        try 
        {
        	Validator.isNull(amount);
        	Validator.validateIsAmountNegative(amount);
        	
        	BigDecimal balance = account.getBalance();
        	Lock lock = account.getLock();
        	if (lock.tryLock(Account.WAIT_TIME, TimeUnit.MILLISECONDS)) {
                try {
                    if (balance.compareTo(amount) > 0) {
                        balance = balance.subtract(amount);
                        return true;
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) 
        {
        	throw new ProcessingFailedException(e.getLocalizedMessage());
        }
        return false;
    }

    public boolean credit(Account account, BigDecimal amount)
    {
        try 
        {

        	Validator.isNull(amount);
        	Validator.validateIsAmountNegative(amount);

        	BigDecimal balance = account.getBalance();
        	Lock lock = account.getLock();
            if (lock.tryLock(Account.WAIT_TIME, TimeUnit.MILLISECONDS)) {
                try {
                    balance = balance.add(amount);
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) 
        {
        	throw new ProcessingException(e);
        }
        return true;
    }
}