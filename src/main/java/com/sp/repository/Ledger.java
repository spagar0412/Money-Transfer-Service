package com.sp.repository;

import java.util.concurrent.ConcurrentHashMap;

import com.sp.dto.Payment;

public class Ledger 
{
	public Ledger(ConcurrentHashMap<String, Payment> concurrentHashMap) 
	{
		// TODO Auto-generated constructor stub
	}

	private static final Ledger INSTANCE = new Ledger(new ConcurrentHashMap<String, Payment>());

}
