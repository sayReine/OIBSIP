package com.reservation.online_reservation_system;

/**
 * Signals that the local reservation database could not be prepared or queried.
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
