package org.starmap.view;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.starmap.controller.StarMapController;
import org.starmap.model.Constellation;
import org.starmap.model.Star;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StarMapView extends Canvas {
    private final StarMapController controller;
    private final PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
    private final Map<String, Color> constellationColors = new HashMap<>();
    private Star currentHoveredStar = null;
    private Star selectedStar;
    private int clickCount = 0;
    private double offsetX, offsetY;
    private boolean showAxis = false; // Flaga określająca, czy rysować osie współrzędnych


    public StarMapView(StarMapController controller) {
        this.controller = controller;
        this.setWidth(1024); // Set canvas width
        this.setHeight(768); // Set canvas height
        drawMap();
        initializeConstellationColors();
        addMouseMotionListener();
        setOnMousePressed(event -> handleMousePressed(event.getX(), event.getY()));
        setOnMouseDragged(event -> handleMouseDragged(event.getX(), event.getY()));
        setOnMouseReleased(event -> handleMouseReleased());
        setOnMouseClicked(event -> handleMouseClicked(event.getX(), event.getY()));
    }

    private void initializeConstellationColors() {
        List<Constellation> constellations = controller.getConstellations();
        for (Constellation constellation : constellations) {
            int hash = constellation.getName().hashCode();
            Random rand = new Random(hash); // Use hash as a seed for random generator
            Color color = new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1);
            constellationColors.put(constellation.getName(), color);
        }
    }

    public void drawMap() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight()); // Set background to black

        if (showAxis) {
            drawAxis(gc); // Rysowanie osi współrzędnych, jeśli flaga jest ustawiona na true
        }
        drawStars();
        drawConstellations();
    }

    private void drawStars() {
        GraphicsContext gc = getGraphicsContext2D();
        List<Star> stars = controller.getStars();
        for (Star star : stars) {
            double brightnessScale = star.getBrightness() / 2.0; // Scale brightness
            double starSize = 2 + (5 - brightnessScale); // Calculate star size
            Color starColor = Color.hsb(60, 0.5, 1 - 0.2 * brightnessScale); // Color based on brightness
            drawStar(gc, star.getXPosition(), star.getYPosition(), starSize, starColor);
        }
    }

    private void drawStar(GraphicsContext gc, double x, double y, double size, Color color) {
        double[] xPoints = new double[10];
        double[] yPoints = new double[10];
        for (int i = 0; i < 10; i++) {
            double angle = Math.PI / 5 * i;
            double radius = i % 2 == 0 ? size : size / 2;
            xPoints[i] = x + radius * Math.sin(angle);
            yPoints[i] = y - radius * Math.cos(angle);
        }
        gc.setStroke(color);
        gc.strokePolyline(xPoints, yPoints, 10);
    }

    private void drawConstellations() {
        GraphicsContext gc = getGraphicsContext2D();
        List<Constellation> constellations = controller.getConstellations();

        for (Constellation constellation : constellations) {
            Color lineColor = constellationColors.getOrDefault(constellation.getName(), Color.BLUE);
            gc.setStroke(lineColor);
            gc.setLineWidth(1);
            gc.setFill(lineColor);
            gc.setFont(new Font("Arial", 14));

            List<Star> starsInConstellation = constellation.getStars();
            for (int i = 0; i < starsInConstellation.size() - 1; i++) {
                Star start = starsInConstellation.get(i);
                Star end = starsInConstellation.get(i + 1);
                gc.strokeLine(start.getXPosition(), start.getYPosition(), end.getXPosition(), end.getYPosition());
            }

            // Rysuj nazwę konstelacji obok pierwszej gwiazdy
            if (!starsInConstellation.isEmpty()) {
                Star firstStar = starsInConstellation.get(0);
                gc.fillText(constellation.getName(), firstStar.getXPosition(), firstStar.getYPosition() - 15);
            }
        }
    }

    public void addOptionsButton(Group root) {
        Button optionsButton = new Button("Options");
        optionsButton.setOnAction(e -> {
            showAxis = !showAxis; // Zmiana stanu flagi przy kliknięciu przycisku
            clearCanvas();
            drawMap();
        });

        VBox optionsLayout = new VBox(); // Tworzenie VBox
        optionsLayout.getChildren().add(optionsButton);
        optionsLayout.setPadding(new Insets(10));
        optionsLayout.setAlignment(Pos.TOP_RIGHT); // Pozycjonowanie przycisku w prawym górnym rogu

        // Dodawanie przycisku do kontenera przekazanego jako argument
        root.getChildren().add(optionsLayout);
    }

    private void drawAxis(GraphicsContext gc) {
        gc.setStroke(Color.WHITE);
        gc.strokeLine(0, getHeight() / 2, getWidth(), getHeight() / 2); // Rysowanie osi X
        gc.strokeLine(getWidth() / 2, 0, getWidth() / 2, getHeight()); // Rysowanie osi Y
    }

    private void addMouseMotionListener() {
        this.setOnMouseMoved(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            Star foundStar = null;

            List<Star> stars = controller.getStars();
            for (Star star : stars) {
                if (Math.abs(mouseX - star.getXPosition()) < 10 && Math.abs(mouseY - star.getYPosition()) < 10) {
                    foundStar = star;
                    break;
                }
            }

            if (foundStar != null && foundStar != currentHoveredStar) {
                currentHoveredStar = foundStar;
                pause.stop(); // Zatrzymaj poprzednie opóźnienie
                drawStarName(foundStar);
            } else if (foundStar == null && currentHoveredStar != null) {
                pause.setOnFinished(e -> {
                    hideStarName();
                    currentHoveredStar = null;
                });
                pause.playFromStart();
            }
        });
    }

    private void handleMouseClicked(double x, double y) {
        selectedStar = findStar(x, y);
        if (selectedStar != null) {
            clickCount++;
            if (clickCount == 2) {
                openStarEditor(selectedStar);
                clickCount = 0; // Zresetowanie licznika po dwukrotnym kliknięciu
            }
        }
    }

    private void handleMousePressed(double x, double y) {
        selectedStar = findStar(x, y);
        if (selectedStar != null) {
            offsetX = selectedStar.getXPosition() - x;
            offsetY = selectedStar.getYPosition() - y;
        }
    }

    private void handleMouseDragged(double x, double y) {
        if (selectedStar != null) {
            selectedStar.setXPosition(x + offsetX);
            selectedStar.setYPosition(y + offsetY);
            clearCanvas();
            drawMap();
        }
    }

    private void handleMouseReleased() {
        selectedStar = null;
    }

    private Star findStar(double x, double y) {
        List<Star> stars = controller.getStars();
        for (Star star : stars) {
            double distance = Math.sqrt(Math.pow(x - star.getXPosition(), 2) + Math.pow(y - star.getYPosition(), 2));
            if (distance < 10) {
                return star;
            }
        }
        return null;
    }

    private void drawStarName(Star star) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillText(star.getName(), star.getXPosition() + 10, star.getYPosition() - 10);
    }

    private void hideStarName() {
        if (currentHoveredStar != null) {
            pause.setOnFinished(e -> {
                clearCanvas();
                drawMap(); // Rysuj wszystko od nowa
            });
            pause.playFromStart();
        }
    }

    private void addStar() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Star");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter star name:");

        dialog.showAndWait().ifPresent(starName -> {
            Star newStar = new Star(starName, 100, 100, 2.0); // Przykładowe wartości
            controller.addStar(newStar); // Dodanie nowej gwiazdy przez kontroler
            clearCanvas();
            drawMap();
        });
    }

    private void removeStar() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Remove Star");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter star name to remove:");

        dialog.showAndWait().ifPresent(starName -> {
            // Usunięcie gwiazdy przez kontroler, zakładając metodę usuwającą gwiazdę po nazwie
            controller.removeStar(starName);
            clearCanvas();
            drawMap();
        });
    }

    public void openConstellationEditor() {
        Stage constellationStage = new Stage();
        constellationStage.setTitle("Edit the constellation");

        VBox editorLayout = new VBox(10); // VBox dla układu pionowego
        editorLayout.setPadding(new Insets(10));

        // Pobranie listy nazw gwiazdozbiorów do wyboru
        List<Constellation> constellations = controller.getConstellations();

        ComboBox<String> constellationComboBox = new ComboBox<>();
        for (Constellation constellation : constellations) {
            constellationComboBox.getItems().add(constellation.getName());
        }


        // Tworzenie przycisków do operacji na konstelacji
        Button addStarButton = new Button("Add Star");
        Button removeStarButton = new Button("Remove Star");
        Button moveStarButton = new Button("Move Star");
        Button changeNameButton = new Button("Change Name");

        // Logika dla przycisków (akcje po kliknięciu)
        addStarButton.setOnAction(e -> addStar());
        removeStarButton.setOnAction(e -> removeStar());
        //moveStarButton.setOnAction(e -> moveStar());
        //changeNameButton.setOnAction(e -> changeName());

        editorLayout.getChildren().addAll(constellationComboBox, addStarButton, removeStarButton, moveStarButton, changeNameButton);

        Scene editorScene = new Scene(editorLayout, 300, 200);
        constellationStage.setScene(editorScene);
        constellationStage.show();
    }

    // Metoda zmieniająca nazwę gwiazdozbioru
    private void changeName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Constellation Name");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter current constellation name:");

        dialog.showAndWait().ifPresent(currentConstellationName -> {
            TextInputDialog newNameDialog = new TextInputDialog();
            newNameDialog.setTitle("Change Constellation Name");
            newNameDialog.setHeaderText(null);
            newNameDialog.setContentText("Enter new constellation name:");

            newNameDialog.showAndWait().ifPresent(newConstellationName -> {
                // Zmiana nazwy gwiazdozbioru przez kontroler
                controller.changeConstellationName(currentConstellationName, newConstellationName);
                clearCanvas();
                drawMap();
            });
        });
    }


    private void openStarEditor(Star star) {
        Stage starEditorStage = new Stage();
        starEditorStage.setTitle("Edit Star");

        VBox editorLayout = new VBox(10);
        editorLayout.setPadding(new Insets(10));

        TextField nameField = new TextField();
        nameField.setPromptText("Enter new star name");

        TextField brightnessField = new TextField();
        brightnessField.setPromptText("Enter new brightness (0.0 - 5.0)");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String newName = nameField.getText();
            String brightnessText = brightnessField.getText();

            if (!newName.isEmpty()) {
                star.setName(newName);
            }

            if (!brightnessText.isEmpty()) {
                try {
                    double newBrightness = Double.parseDouble(brightnessText);
                    if (newBrightness >= 0.0 && newBrightness <= 5.0) {
                        star.setBrightness(newBrightness);
                    } else {
                        System.out.println("Brightness value should be between 0.0 and 5.0");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid brightness value");
                }
            }

            clearCanvas();
            drawMap();
            starEditorStage.close();
        });

        editorLayout.getChildren().addAll(nameField, brightnessField, saveButton);

        Scene starEditorScene = new Scene(editorLayout, 300, 200);
        starEditorStage.setScene(starEditorScene);
        starEditorStage.show();
    }


    private void clearCanvas() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
    }
}