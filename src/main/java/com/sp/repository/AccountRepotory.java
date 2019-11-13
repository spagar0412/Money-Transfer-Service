package com.sp.repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sp.dto.Account;
import com.sp.dto.AccountDetails;

public class AccountRepotory implements RepositoryTemplate<Account>
{

    private static final AccountRepotory INSTANCE = new AccountRepotory(new ConcurrentHashMap<String, Account>());
    private final Map<String, Account> accounts; /* for product -here In-memory Databases(like H2) can be used 
                                                    for better transaction mgmt as well as in ledgerService.java */ 
    
    private AccountRepotory(Map<String, Account> accounts) 
    {
        this.accounts = accounts;
    }

	public static AccountRepotory getInstance() {
        return INSTANCE;
    }

    public Account getById(String id) {
        return accounts.get(id);
    }

    public Collection<Account> getAll() {
        return accounts.values();
    }

    public Account addAccount(Account account) 
    {
        Account accountExists = accounts.putIfAbsent(account.getId(), account);
        if (accountExists != null) {
           System.out.println("Duplicate account"); 
           /* for real product --> duplicate account exception and exceptionMapper call 
               can be added here */
        }

        return getById(account.getId());
    }

    public void removeAll() {
        synchronized (accounts) {
            accounts.clear();
        }
    }

	public void updateAccount(Account account) 
	{
		accounts.put(account.getId(), account);}


	public AccountDetails getAccountDetailsBYId(String id) 
	{
		//notImplemeted for this task
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
}
