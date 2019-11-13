package com.sp.dto;

public class Payment 
{
    private String amount;

    private String accountId;

   // private Receiver receiver;

    private String currency;

    private String requestId;
    
    private String desc;

    @Override
    public String toString()
    {
        return "Payment [Desc = "+ desc +", amount = "+amount+", account_id = " + accountId + ", "  + "receiver = receiver" + ", currency = "+currency+", request_id = "+requestId+"]";
    }

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
