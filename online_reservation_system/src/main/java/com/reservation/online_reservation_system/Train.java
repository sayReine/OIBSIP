package com.reservation.online_reservation_system;

import java.util.Objects;

/**
 * A train available for booking.
 */
public record Train(int number, String name) {
    public Train {
        if (number <= 0) {
            throw new IllegalArgumentException("Train number must be positive.");
        }

        name = Objects.requireNonNull(name, "Train name is required.").trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Train name is required.");
        }
    }
}
