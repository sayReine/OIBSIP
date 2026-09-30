package com.reservation.online_reservation_system;

public abstract class User {
    private final String username;
    private final UserRole role;

    protected User(String username, UserRole role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }
}
