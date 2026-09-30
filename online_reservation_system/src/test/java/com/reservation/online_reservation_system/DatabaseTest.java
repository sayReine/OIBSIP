package com.reservation.online_reservation_system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatabaseTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void initializeCreatesAndSeedsTheReservationDatabase() throws SQLException {
        Database database = Database.fromPath(temporaryDirectory.resolve("reservation-test.db"));

        database.initialize();

        try (Connection connection = database.openConnection();
             Statement statement = connection.createStatement();
             ResultSet users = statement.executeQuery("SELECT COUNT(*) FROM users")) {
            users.next();
            assertEquals(2, users.getInt(1));
        }

        try (Connection connection = database.openConnection();
             Statement statement = connection.createStatement();
             ResultSet trains = statement.executeQuery("SELECT COUNT(*) FROM trains")) {
            trains.next();
            assertEquals(5, trains.getInt(1));
        }
    }
}
