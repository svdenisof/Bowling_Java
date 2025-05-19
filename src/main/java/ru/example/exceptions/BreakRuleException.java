package ru.example.exceptions;

public class BreakRuleException extends RuntimeException {

    public BreakRuleException(Exception ex)
    {
        super(ex);
    }

    public BreakRuleException(String message)
    {
        super(message);
    }

    public BreakRuleException(String message, Exception ex)
    {
        super(message,ex);
    }
}
