package com.reservation.online_reservation_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Database database = Database.defaultDatabase();
        database.initialize();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        fxmlLoader.setControllerFactory(controllerType -> {
            if (controllerType == HelloController.class) {
                return new HelloController(new UserAuthenticationService(database));
            }
            throw new IllegalArgumentException("Unsupported controller: " + controllerType.getName());
        });
        Scene scene = new Scene(fxmlLoader.load(), 420, 280);
        stage.setTitle("Online Reservation System");
        stage.setScene(scene);
        stage.show();
    }
}
