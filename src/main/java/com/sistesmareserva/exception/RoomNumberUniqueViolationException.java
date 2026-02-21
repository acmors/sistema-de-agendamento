package com.sistesmareserva.exception;

public class RoomNumberUniqueViolationException extends RuntimeException {
    public RoomNumberUniqueViolationException(String message) {
        super(message);
    }
}
