package ru.example.exceptions;

import java.io.Serial;

public class BowlingGameException extends IllegalArgumentException {

    @Serial
    private static final long serialVersionUID = -4671192612978178782L;

    public BowlingGameException(final String message) {
        super(message);
    }

    public BowlingGameException(final Exception ex) {
        super(ex);
    }

    public BowlingGameException(final String message, final Exception ex) {
        super(message, ex);
    }
}
