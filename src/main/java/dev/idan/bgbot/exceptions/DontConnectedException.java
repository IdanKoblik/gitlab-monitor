package dev.idan.bgbot.exceptions;

public class DontConnectedException extends RuntimeException {
    public DontConnectedException(String message) {
        super(message);
    }
}
