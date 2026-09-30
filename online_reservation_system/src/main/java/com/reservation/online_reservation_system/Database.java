package com.reservation.online_reservation_system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * Owns the application's embedded SQLite database and its initial seed data.
 */
public final class Database {
    private static final List<Train> DEFAULT_TRAINS = List.of(
            new Train(101, "Kigali Express"),
            new Train(102, "Huye Express"),
            new Train(103, "Musanze Express"),
            new Train(104, "Rubavu Express"),
            new Train(105, "Kayonza Express")
    );

    private static final String CREATE_USERS = """
            CREATE TABLE IF NOT EXISTS users (
                username TEXT PRIMARY KEY,
                password TEXT NOT NULL,
                role TEXT NOT NULL
            )
            """;

    private static final String CREATE_TRAINS = """
            CREATE TABLE IF NOT EXISTS trains (
                train_number INTEGER PRIMARY KEY,
                train_name TEXT NOT NULL
            )
            """;

    private static final String CREATE_RESERVATIONS = """
            CREATE TABLE IF NOT EXISTS reservations (
                pnr TEXT PRIMARY KEY,
                passenger_name TEXT NOT NULL,
                train_number INTEGER NOT NULL,
                train_name TEXT NOT NULL,
                class_type TEXT NOT NULL,
                journey_date TEXT NOT NULL,
                source_station TEXT NOT NULL,
                destination_station TEXT NOT NULL,
                FOREIGN KEY (train_number) REFERENCES trains(train_number)
                    ON UPDATE CASCADE ON DELETE RESTRICT
            )
            """;

    private static final String INSERT_USER = """
            INSERT OR IGNORE INTO users (username, password, role)
            VALUES (?, ?, ?)
            """;

    private static final String INSERT_TRAIN = """
            INSERT OR IGNORE INTO trains (train_number, train_name)
            VALUES (?, ?)
            """;

    private final String jdbcUrl;

    private Database(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    /**
     * Gets the on-disk database used by the desktop application.
     */
    public static Database defaultDatabase() {
        return fromPath(Path.of(System.getProperty("user.dir"), "data", "reservation-system.db"));
    }

    /**
     * Creates a database wrapper for an explicit database file, primarily useful in tests.
     */
    public static Database fromPath(Path databasePath) {
        Objects.requireNonNull(databasePath, "Database path is required.");
        Path normalizedPath = databasePath.toAbsolutePath().normalize();
        Path parent = normalizedPath.getParent();

        try {
            if (parent != null) {
                Files.createDirectories(parent);
            }
        } catch (IOException exception) {
            throw new DatabaseException("Could not create the database directory.", exception);
        }

        return new Database("jdbc:sqlite:" + normalizedPath.toString().replace('\\', '/'));
    }

    /**
     * Opens a connection with SQLite foreign-key enforcement enabled.
     */
    public Connection openConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcUrl);
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        }
        return connection;
    }

    /**
     * Creates all tables and inserts the built-in demonstration credentials and routes.
     */
    public void initialize() {
        try (Connection connection = openConnection(); Statement statement = connection.createStatement()) {
            statement.execute(CREATE_USERS);
            statement.execute(CREATE_TRAINS);
            statement.execute(CREATE_RESERVATIONS);
            seedUsers(connection);
            seedTrains(connection);
        } catch (SQLException exception) {
            throw new DatabaseException("Could not initialize the reservation database.", exception);
        }
    }

    private void seedUsers(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            insertUser(statement, "admin", "admin123", UserRole.ADMIN);
            insertUser(statement, "passenger", "pass123", UserRole.PASSENGER);
        }
    }

    private void insertUser(PreparedStatement statement, String username, String password, UserRole role)
            throws SQLException {
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, role.name());
        statement.executeUpdate();
    }

    private void seedTrains(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_TRAIN)) {
            for (Train train : DEFAULT_TRAINS) {
                statement.setInt(1, train.number());
                statement.setString(2, train.name());
                statement.executeUpdate();
            }
        }
    }
}
