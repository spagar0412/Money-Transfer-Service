package com.sp.service;

import java.util.Collection;

import com.sp.dto.Account;
import com.sp.dto.CounterParty;
import com.sp.repository.CounterPartyRepository;

public class CounterPartyService 
{

    private static final CounterPartyService INSTANCE = new CounterPartyService(CounterPartyRepository.getInstance());

    private final CounterPartyRepository repo;

    private CounterPartyService(CounterPartyRepository repository) 
    {
        this.repo = repository;
    }

    public static CounterPartyService getInstance() {
        return INSTANCE;
    }

    public Collection<CounterParty> getAll() 
    {
        return repo.getAll();
    }

	public CounterParty getCounterParty(String id) 
	{
		return repo.getById(id);
	}
	
	public void addCounterParty(CounterParty party) 
	{
		/*Assumption - validateKYC is not implemented as this should be handled by backOFfice
		 *  after for actual document validation. 
		 */
		//validateKYC()
		repo.add(party);
	}

	/*
	 * private boolean validateKYC() { return true; }
	 */

	public Account getAccount(String target_account_id ) 
	{
		//lookUp for couterParty Account
		return repo.getAccountById(target_account_id );
		
	}


}
