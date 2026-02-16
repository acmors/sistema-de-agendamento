package com.sistesmareserva.exception;

public class StatusAlreadyExistsException extends RuntimeException{
    public StatusAlreadyExistsException(String message) {
        super(message);
    }
}
