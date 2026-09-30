package com.reservation.online_reservation_system;

public class AdminUser extends User {
    public AdminUser(String username) {
        super(username, UserRole.ADMIN);
    }
}
