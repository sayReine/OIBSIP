package com.reservation.online_reservation_system;

public class AdminUser extends User {
    public AdminUser(String username, String password) {
        super(username, password, UserRole.ADMIN);
    }
}
