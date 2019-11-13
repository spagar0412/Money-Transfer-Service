package com.sp.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sp.dto.Transaction;

public class TransactionRepository implements RepositoryTemplate<Transaction>
{
	private static final TransactionRepository INSTANCE = new TransactionRepository(new ConcurrentHashMap<String, Transaction>());
   
	private final Map<String, Transaction> transactions;

    private TransactionRepository(Map<String, Transaction> transactions) 
    {
        this.transactions = transactions;
    }
    
    @Override
    public Transaction getById(String id) 
    {
        return transactions.get(id);
    }

    @Override
    public int size() 
    {
        return transactions.size();
    }

    public Transaction add(Transaction transaction) 
    {
        transactions.putIfAbsent(transaction.getId(), transaction);
        return transaction;
    }

	public static TransactionRepository getInstance() {
		return INSTANCE;
	}

}
