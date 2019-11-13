package com.sp.validator;

import java.math.BigDecimal;

import com.sp.dto.Account;
import com.sp.exception.InValidOperationException;
import com.sp.exception.InsufficientFundException;
import com.sp.model.Transfer;


public class Validator 
{
	private static final int ISO_CODE_LENGTH = 3;
	private static final int COUNTRY_CODE_LENGTH = 2;

	public static void validateNegativeAcountBalance(Account account) 
	{
	     if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) 
	     {
	            throw new InValidOperationException("Amount cannot be negative for " + account.getId());
	     }
	 }
	
	public static void validateIsAmountNegative(BigDecimal amount) 
	 {
        if (amount == null || BigDecimal.ZERO.compareTo(amount) > 0) {
            throw new InValidOperationException("Amount cannot be null or negative");
        }
	 }
	public static void validateIsBalanceSufficient(BigDecimal balance, BigDecimal amount) 
	{
       if (balance.compareTo(amount) < 0) 
       {
           throw new InsufficientFundException("Operation can't be performed :: Insufficient funds");
       }
	}
	public static boolean isNull(Object account)
	{
		return account==null;
	}
	 
	 public static void validateCurrencyCode(String isoCode) 
	 {
        if (isoCode.length() != ISO_CODE_LENGTH) 
        {
            throw new InValidOperationException("Currency code have to have length equals to " + ISO_CODE_LENGTH);
        }
	 }
	 public static void validateCountryCode(String countryCode) 
	 {
        if (countryCode.length() != COUNTRY_CODE_LENGTH) 
        {
            throw new InValidOperationException("Country code have to have length equals to " + COUNTRY_CODE_LENGTH);
        }
	 }
	 
	public static void validateAccountIsDifferent(String fromAccId, String toAccId) 
    {
        if (fromAccId.equalsIgnoreCase(toAccId)) 
        {
            throw new InValidOperationException("Accounts must be different");
        }
    }

	public static void isAccountIdNull(String accId) 
	{
		if (isNull(accId)) 
        {
            throw new InValidOperationException("Account Id cannot be null");
        }
	}

	public static void isAccountExist(Account account) 
	{
		if(isNull(account))
			throw new InValidOperationException("Account doesn't exist : " + account );
	}

	public static void isTransferNull(Transfer tsr) 
	{
		if (isNull(tsr)) 
        {
            throw new InValidOperationException("Transfer cannot be null");
        }
		
	}
}
