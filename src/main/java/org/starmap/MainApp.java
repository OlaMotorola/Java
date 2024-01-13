package org.starmap;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.starmap.controller.StarMapController;
import org.starmap.view.StarMapView;

// Main application class for the star map
public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StarMapController controller = new StarMapController("src/main/resources/stars.json");
        StarMapView view = new StarMapView(controller);


        Group root = new Group(); // Create Group container
        root.getChildren().add(view); // Add StarMapView to container
        view.addOptionsButton(root);


        Scene scene = new Scene(root, 1024, 768); // Create Scene with Group container

        primaryStage.setTitle("Star Map");
        primaryStage.setScene(scene);

        Button editTheConstellation = new Button("Edit the constellation");
        editTheConstellation.setOnAction(e -> {
            view.openConstellationEditor();
        });

        //Button editTheStar = new Button("Edit the star");
        //editTheStar.setOnAction(e -> {
        //view.openStarEditor();
        //});

        // Kontener dla przycisków
        HBox buttonsLayout = new HBox(); // Użyj HBox dla przycisków w poziomie
        buttonsLayout.getChildren().addAll(editTheConstellation);
        root.getChildren().add(buttonsLayout);

        primaryStage.show();
        view.drawMap(); // Call this after the scene is shown
    }
}
