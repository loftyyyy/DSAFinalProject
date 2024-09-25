package org.example.project.dsaafinalproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    private Button importButton;

    @FXML
    private AnchorPane pane;

    private NodeRepresentation nodeRepresentation;
    private ArrowRepresentation arrowRepresentation;
    private Simulation simulation ;

    @FXML
    public void initialize() {


    }

    public void onImportButton(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a .txt file");

        // Set extension filter to accept only .txt files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Open the file chooser dialog
        Stage stage = (Stage) pane.getScene().getWindow(); // Get the current window
        File selectedFile = fileChooser.showOpenDialog(stage);
        simulation = new Simulation(selectedFile);
        if (selectedFile != null) {
            // Display the selected file path in the welcomeText label
            welcomeText.setText("Selected File: " + selectedFile.getAbsolutePath());
        } else {
            welcomeText.setText("No file selected.");
        }

    }

    public void simulate(){
        simulation.startSimulation(pane);



    }

}
