package org.example.project.dsaafinalproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button importButton;

    @FXML
    private AnchorPane pane;

    private NodeRepresentation nodeRepresentation;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void initialize(){

        pane.getChildren().addAll(new NodeRepresentation("1", 200,200), new NodeRepresentation("2", 700,200));


        importButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select a Text File");
            fileChooser.setInitialDirectory(new File("C:\\"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt file", "*.txt"));

            System.out.println("hi");
        });

    }








}