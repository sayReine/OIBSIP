module com.reservation.online_reservation_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.reservation.online_reservation_system to javafx.fxml;
    exports com.reservation.online_reservation_system;
}
