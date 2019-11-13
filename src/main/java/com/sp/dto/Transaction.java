package com.sp.dto;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import com.sp.enums.TransactionState;;

final public class Transaction 
{
	private final String id;
    private final String source;
    private final String target;
    private final BigDecimal amount;
    private String currencyCode; //three letter Code like (INR,GBP,USD)
    private String description;
    private TransactionState state;// pending, completed, declined or failed
    private LocalDateTime createdAt; 
    private LocalDateTime completedAt;
    private String type;// can be used for to calculates fees inter bank transaction.
    private String initiatedBy;//to identify is transaction made by bank on behalf of user like payByCheque or ECS

    public Transaction(String source, String target, BigDecimal amount) 
    {
        this.id = UUID.randomUUID().toString();
        this.source = source;
        this.target = target;
        this.amount = amount;
        this.state = TransactionState.CREATED;
        this.createdAt = LocalDateTime.now(Clock.systemDefaultZone());
    }

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TransactionState getState() {
		return state;
	}

	public void setState(TransactionState state) {
		this.state = state;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(LocalDateTime completedAt) {
		this.completedAt = completedAt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInitiatedBy() {
		return initiatedBy;
	}

	public void setInitiatedBy(String initiatedBy) {
		this.initiatedBy = initiatedBy;
	}

	public String getId() {
		return id;
	}

	public String getSource() {
		return source;
	}

	public String getTarget() {
		return target;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	 @Override
	 public String toString() 
	 {
	        return "Transaction | amount=" + amount + " " +
	                "from sourceAccount='" + source + '\'' +
	                "to targetAccount='" + target;
	 }
}
