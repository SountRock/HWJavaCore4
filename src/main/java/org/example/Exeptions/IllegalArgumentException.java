package org.example.Exeptions;

public class IllegalArgumentException extends Exception{
    public IllegalArgumentException(String message) {
        super(message);
    }

    public static void testArguments(String exceptionMessage, double ... args) throws IllegalArgumentException {
        for (double arg : args) {
            if(arg < 0) throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
