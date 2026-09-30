package com.reservation.online_reservation_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Authenticates a user against the local SQLite database.
 */
public final class UserAuthenticationService {
    private static final String AUTHENTICATE_USER = """
            SELECT username, role
            FROM users
            WHERE username = ? AND password = ?
            """;

    private final Database database;

    public UserAuthenticationService(Database database) {
        this.database = Objects.requireNonNull(database, "Database is required.");
    }

    /**
     * Returns the authenticated user, or {@code null} when the credentials are invalid.
     */
    public User authenticate(String username, String password) {
        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            return null;
        }

        try (Connection connection = database.openConnection();
             PreparedStatement statement = connection.prepareStatement(AUTHENTICATE_USER)) {
            statement.setString(1, username.trim());
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                return createUser(resultSet.getString("username"), resultSet.getString("role"));
            }
        } catch (SQLException exception) {
            throw new DatabaseException("Could not authenticate the user.", exception);
        }
    }

    private User createUser(String username, String roleValue) {
        try {
            return switch (UserRole.valueOf(roleValue)) {
                case ADMIN -> new AdminUser(username);
                case PASSENGER -> new PassengerUser(username);
            };
        } catch (IllegalArgumentException exception) {
            throw new DatabaseException("The stored user role is invalid.", exception);
        }
    }
}
