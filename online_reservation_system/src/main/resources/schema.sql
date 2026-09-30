

CREATE TABLE IF NOT EXISTS users (
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL,
    role TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS trains (
    train_number INTEGER PRIMARY KEY,
    train_name TEXT NOT NULL
);

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
);
