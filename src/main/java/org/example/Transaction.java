package org.example;

import org.example.Exeptions.InsufficientFundsException;

public class Transaction {
    public static void transaction(Account accountIn, Account accountOut, double sumOfMoney) throws  InsufficientFundsException {
        accountOut.pullMoney(accountIn.pullMoney(sumOfMoney));
    }
}
