package com.sp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account 
{
    public static final long WAIT_TIME = 15L;
	private final String id;
    private String number;
    private BigDecimal balance;
    private String currency; // code(like GBP,USD) can be used to calculate fees for bank transactions of type exchange.
    private boolean active;
    @JsonIgnore
    private LocalDateTime created;
    @JsonIgnore
    private LocalDateTime lastUpdated;
    @JsonIgnore
    private final transient Lock lock;

    public Account() 
    {
    	this.id = UUID.randomUUID().toString();
    	this.lock = new ReentrantLock();
    	 this.active = true;//activeBydefault
    }
    
    public Account(String number, BigDecimal balance, String currency, boolean active) 
    {
        this.id = UUID.randomUUID().toString();
        this.number = number;
        this.balance = balance;
        this.currency = currency;
        this.active = active;
        this.lock = new ReentrantLock();
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() 
    {
        try 
        {
            lock.lock();
            return balance;
        } 
        finally 
        {
            lock.unlock();
        }
    }
    
   public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Lock getLock() {
		return lock;
	}
	
	@Override
    public String toString() 
	{
		return String.format("Account{"
			 + "id=%d, currency=%s, number=%s, active=%s, balance=%s}",
                id, currency, number, active, balance);
    }
	@Override
	public int hashCode() 
	{
		return System.identityHashCode(this.id); // this will be useful  to get account from couterparty account list
	}

	public static long getWaitTime() {
		return WAIT_TIME;
	}

	public String getNumber() {
		return number;
	}

}
