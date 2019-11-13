package com.sp.repository;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sp.dto.Account;
import com.sp.dto.CounterParty;

public class CounterPartyRepository implements RepositoryTemplate<CounterParty> 
{
	private static final CounterPartyRepository INSTANCE = new CounterPartyRepository(new ConcurrentHashMap<String, CounterParty>());
	
	private final Map<String, CounterParty> parties;
	
	public CounterPartyRepository(Map<String, CounterParty> parties) 
	{
		this.parties = parties;
	}

	@Override
    public CounterParty getById(String id) 
    {
        return parties.get(id);
    }

    @Override
    public int size() 
    {
        return parties.size();
    }

    public CounterParty add(CounterParty party) 
    {
        parties.putIfAbsent(party.getId(), party);
        return party;
    }
    
	public static CounterPartyRepository getInstance() 
	{
		return INSTANCE;
	}

	public Map<String, CounterParty> getRepo() 
	{
		return parties;
	}

	public Collection<CounterParty> getAll() 
	{
		// TODO Auto-generated method stub
		return parties.values();
	}

	/*assumption --> implemented simple traversal. 
	* it can be optimized by using account hashcode */
	public Account getAccountById(String target_account_id) 
	{
		for (Iterator iterator = parties.values().iterator(); iterator.hasNext();) 
		{
			CounterParty party = (CounterParty) iterator.next();
			for (int i = 0; i < party.getAccountList().size(); i++) 
			{
				Account acc = party.getAccountList().get(i);
				if(acc !=null && acc.getId().contentEquals(target_account_id))
				{
					return acc; 
				}
			}
		}
		return null;		
	}

}
