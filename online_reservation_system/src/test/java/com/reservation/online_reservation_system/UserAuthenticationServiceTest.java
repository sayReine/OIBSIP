package com.reservation.online_reservation_system;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAuthenticationServiceTest {

    @Test
    void shouldAuthenticateAdminWithValidCredentials() {
        User authenticatedUser = UserAuthenticationService.authenticate("admin", "admin123");

        assertNotNull(authenticatedUser);
        assertEquals(UserRole.ADMIN, authenticatedUser.getRole());
        assertEquals("admin", authenticatedUser.getUsername());
        assertInstanceOf(AdminUser.class, authenticatedUser);
    }

    @Test
    void shouldAuthenticatePassengerWithValidCredentials() {
        User authenticatedUser = UserAuthenticationService.authenticate("passenger", "pass123");

        assertNotNull(authenticatedUser);
        assertEquals(UserRole.PASSENGER, authenticatedUser.getRole());
        assertEquals("passenger", authenticatedUser.getUsername());
        assertInstanceOf(PassengerUser.class, authenticatedUser);
    }

    @Test
    void shouldRejectInvalidCredentials() {
        User authenticatedUser = UserAuthenticationService.authenticate("admin", "wrong-pass");

        assertNull(authenticatedUser);
    }
}
