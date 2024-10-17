package org.example.project.dsaafinalproject;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

public class NodeRepresentation extends Pane {
    private Label elementLabel;

    public NodeRepresentation(String elementData, double x, double y) {
        // Set dimensions of the entire node
        this.setPrefSize(127, 66);

        // Create a rectangle with rounded corners (radius 42) for the whole node
        Rectangle nodeBox = new Rectangle(127, 66);
        nodeBox.setArcWidth(42);
        nodeBox.setArcHeight(42);
        nodeBox.setStroke(Color.BLACK);
        nodeBox.setFill(Color.TRANSPARENT); // Transparent background for the entire node

        // Background rectangle for the "Previous" section (only rounded on the left side)
//        Rectangle previousSection = new Rectangle(42, 66); // First third (left side)
//        previousSection.setArcWidth(42); // Radius only on the left
//        previousSection.setArcHeight(42);
//        previousSection.setFill(Color.LIGHTBLUE); // Background color for "Previous"
//        previousSection.setStroke(Color.TRANSPARENT);
//
//        // Background rectangle for the "Next" section (only rounded on the right side)
//        Rectangle nextSection = new Rectangle(42, 66); // Last third (right side)
//        nextSection.setFill(Color.LIGHTGREEN); // Background color for "Next"
//        nextSection.setStroke(Color.TRANSPARENT);
//        nextSection.setLayoutX(85); // Position in the last third of the node
//        nextSection.setArcWidth(42); // Radius only on the right side
//        nextSection.setArcHeight(42);

        // Create labels for "Previous", "Element", and "Next"
// Create labels for "Previous", "Element", and "Next"
        Label previousLabel = new Label("Prev");
        previousLabel.setRotate(270); // Rotate vertically
        previousLabel.setPrefHeight(66);
        previousLabel.setPrefWidth(42);
        previousLabel.setTextAlignment(TextAlignment.CENTER);
        previousLabel.setStyle("-fx-border-color: transparent; -fx-alignment: center; -fx-font-weight: bold;"); // Make text bold

// Element label that will hold the node data (middle)
        elementLabel = new Label(elementData);
        elementLabel.setPrefWidth(43);
        elementLabel.setPrefHeight(66);
        elementLabel.setStyle("-fx-border-color: transparent; -fx-alignment: center; -fx-font-weight: bold;"); // Make text bold
        elementLabel.setTextAlignment(TextAlignment.CENTER);

        Label nextLabel = new Label("Next");
        nextLabel.setRotate(90); // Rotate vertically
        nextLabel.setPrefHeight(66);
        nextLabel.setPrefWidth(42);
        nextLabel.setTextAlignment(TextAlignment.CENTER);
        nextLabel.setStyle("-fx-border-color: transparent; -fx-alignment: center; -fx-font-weight: bold;"); // Make text bold


        // Draw vertical lines to separate sections
        Line line1 = new Line(42, 0, 42, 66); // Divider after "Previous"
        Line line2 = new Line(85, 0, 85, 66); // Divider before "Next"

        // Add all components to the Pane
        this.getChildren().addAll(nodeBox,  previousLabel, elementLabel, nextLabel, line1, line2);

        // Set positions of the labels and lines inside the rectangle
        previousLabel.setLayoutX(0);
        previousLabel.setLayoutY(0);

        elementLabel.setLayoutX(42);
        elementLabel.setLayoutY(0);

        nextLabel.setLayoutX(85);
        nextLabel.setLayoutY(0);

        // Set positions of the lines
        line1.setLayoutX(0);
        line1.setLayoutY(0);

        line2.setLayoutX(0);
        line2.setLayoutY(0);

        // Set position of the entire node in the parent Pane
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    // Method to update the element label
    public void setElementData(String data) {
        elementLabel.setText(data);
    }
}
