package com.reservation.online_reservation_system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class UserAuthenticationServiceTest {
    @TempDir
    Path temporaryDirectory;

    private UserAuthenticationService authenticationService() {
        Database database = Database.fromPath(temporaryDirectory.resolve("authentication-test.db"));
        database.initialize();
        return new UserAuthenticationService(database);
    }

    @Test
    void shouldAuthenticateAdminWithValidCredentials() {
        User authenticatedUser = authenticationService().authenticate("admin", "admin123");

        assertNotNull(authenticatedUser);
        assertEquals(UserRole.ADMIN, authenticatedUser.getRole());
        assertEquals("admin", authenticatedUser.getUsername());
        assertInstanceOf(AdminUser.class, authenticatedUser);
    }

    @Test
    void shouldAuthenticatePassengerWithValidCredentials() {
        User authenticatedUser = authenticationService().authenticate("passenger", "pass123");

        assertNotNull(authenticatedUser);
        assertEquals(UserRole.PASSENGER, authenticatedUser.getRole());
        assertEquals("passenger", authenticatedUser.getUsername());
        assertInstanceOf(PassengerUser.class, authenticatedUser);
    }

    @Test
    void shouldRejectInvalidCredentials() {
        User authenticatedUser = authenticationService().authenticate("admin", "wrong-pass");

        assertNull(authenticatedUser);
    }

    @Test
    void shouldRejectSqlInjectionAttemptedAsUsername() {
        User authenticatedUser = authenticationService().authenticate("admin' OR '1'='1", "anything");

        assertNull(authenticatedUser);
    }
}
