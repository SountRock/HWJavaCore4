package org.example.Exeptions;

public class InsufficientFundsException extends Exception{
    public InsufficientFundsException(String message) {
        super(message);
    }

    public static void testArguments(String exceptionMessage, double arg1, double arg2) throws InsufficientFundsException {
        if(arg1 > arg2) throw new InsufficientFundsException(exceptionMessage);
    }
}
