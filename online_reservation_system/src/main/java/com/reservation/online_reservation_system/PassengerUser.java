package com.reservation.online_reservation_system;

public class PassengerUser extends User {
    public PassengerUser(String username) {
        super(username, UserRole.PASSENGER);
    }
}
