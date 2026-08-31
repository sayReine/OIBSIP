package com.reservation.online_reservation_system;

import java.util.Map;

public final class UserAuthenticationService {
    private static final Map<String, User> USERS = Map.of(
            "admin", new AdminUser("admin", "admin123"),
            "passenger", new PassengerUser("passenger", "pass123")
    );

    private UserAuthenticationService() {
    }

    public static User authenticate(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        User user = USERS.get(username.trim());
        if (user == null) {
            return null;
        }

        return user.getPassword().equals(password) ? user : null;
    }
}
