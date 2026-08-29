

CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
    );



CREATE TABLE IF NOT EXISTS trains (
                                      id SERIAL PRIMARY KEY,
                                      train_number INTEGER UNIQUE NOT NULL,
                                      train_name VARCHAR(100) NOT NULL
    );



CREATE TABLE IF NOT EXISTS reservations (
                                            pnr VARCHAR(20) PRIMARY KEY,
    passenger_name VARCHAR(100) NOT NULL,

    train_number INTEGER NOT NULL,

    class_type VARCHAR(30) NOT NULL,

    journey_date DATE NOT NULL,

    source_station VARCHAR(100) NOT NULL,

    destination_station VARCHAR(100) NOT NULL,

    CONSTRAINT fk_reservation_train
    FOREIGN KEY (train_number)
    REFERENCES trains(train_number)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
    );



INSERT INTO users (username, password)
VALUES ('admin', 'admin123')
    ON CONFLICT (username) DO NOTHING;


INSERT INTO trains (train_number, train_name)
VALUES
    (101, 'Kigali Express'),
    (102, 'Huye Express'),
    (103, 'Musanze Express'),
    (104, 'Rubavu Express'),
    (105, 'Kayonza Express')
    ON CONFLICT (train_number) DO NOTHING;