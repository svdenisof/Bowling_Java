package ru.example.exceptions;

import java.io.Serial;

public class BreakRuleException extends IllegalArgumentException {

    @Serial
    private static final long serialVersionUID = 6274567456405867843L;

    public BreakRuleException(final Exception ex) {
        super(ex);
    }

    public BreakRuleException(final String message) {
        super(message);
    }

    public BreakRuleException(final String message, final Exception ex) {
        super(message, ex);
    }
}
