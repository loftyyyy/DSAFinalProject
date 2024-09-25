package org.example.project.dsaafinalproject;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class ArrowRepresentation {
    private static final double ARROW_VERTICAL_OFFSET = 10; // Vertical offset to separate the arrows above and below the middle
    private static final double LABEL_OFFSET = 10; // Offset for the label



    public Line createDirectionalArrow(NodeRepresentation currentNode, String labelText, boolean isLeft) {
        double startX, startY;
        double labelX, labelY;

        // Determine the starting point based on the boolean flag
        if (isLeft) {
            startX = currentNode.getLayoutX(); // Start from the left side of the current node
        } else {
            startX = currentNode.getLayoutX() + currentNode.getWidth(); // Start from the right side of the current node
        }

        startY = currentNode.getLayoutY() + 66 / 2 - ARROW_VERTICAL_OFFSET; // Center of the current node with offset

        // Calculate the label position (you can adjust the positioning as needed)
        labelX = startX + (isLeft ? -LABEL_OFFSET : LABEL_OFFSET); // Position the label offset from the arrow start
        labelY = startY; // Align vertically with the arrow

        // Create the line arrow
        Line arrow = new Line(startX, startY, labelX, labelY);
        arrow.setStroke(Color.BLACK);
        arrow.setStrokeWidth(2);

        // Create the label node
        Label label = new Label(labelText);
        label.setLayoutX(labelX);
        label.setLayoutY(labelY - 10); // Center the label vertically with a slight adjustment

        // Add the label to the pane if needed (you can pass the pane reference)
        // pane.getChildren().add(label);

        return arrow;
    }
    // Method to create a "next" arrow starting just above the middle-right side of the current node
    public Line createNextArrow(NodeRepresentation currentNode, NodeRepresentation nextNode) {
        // Start just above the middle-right side of the current node
        double startX = currentNode.getLayoutX() + currentNode.getWidth();
        double startY = currentNode.getLayoutY() + 66 / 2 - ARROW_VERTICAL_OFFSET; // Slight upward shift

        // End just above the middle-left side of the next node
        double endX = nextNode.getLayoutX();
        double endY = nextNode.getLayoutY() + 66 / 2 - ARROW_VERTICAL_OFFSET;

        Line arrow = new Line(startX, startY, endX, endY);
        arrow.setStroke(Color.BLACK);
        arrow.setStrokeWidth(2);

        return arrow;
    }

    // Method to create a "previous" arrow starting just below the middle-left side of the current node
    public Line createPrevArrow(NodeRepresentation currentNode, NodeRepresentation prevNode) {
        // Start just below the middle-left side of the current node
        double startX = currentNode.getLayoutX();
        double startY = currentNode.getLayoutY() + 66 / 2 + ARROW_VERTICAL_OFFSET; // Slight downward shift

        // End just below the middle-right side of the previous node
        double endX = prevNode.getLayoutX() + prevNode.getWidth();
        double endY = prevNode.getLayoutY() + 66 / 2 + ARROW_VERTICAL_OFFSET;

        Line arrow = new Line(startX, startY, endX, endY);
        arrow.setStroke(Color.BLACK);
        arrow.setStrokeWidth(2);

        return arrow;
    }

    // Method to create an arrowhead for a given arrow
    private Polygon createArrowHead(double x, double y, boolean isNext) {
        Polygon arrowHead = new Polygon();
        double size = 10; // Size of the arrowhead
        if (isNext) {
            arrowHead.getPoints().addAll(x, y, x - size, y - size / 2, x - size, y + size / 2);
        } else {
            arrowHead.getPoints().addAll(x, y, x + size, y - size / 2, x + size, y + size / 2);
        }
        arrowHead.setFill(Color.BLACK);
        return arrowHead;
    }

    public void addArrowsToPane(Pane pane, NodeRepresentation currentNode, NodeRepresentation nextNode) {
        // Create straight arrows for next and previous, with vertical offset
        Line nextArrow = createNextArrow(currentNode, nextNode);
        Line prevArrow = createPrevArrow(nextNode, currentNode); // Reverse direction for the previous arrow

        // Create arrowheads for both ends
        Polygon nextArrowHead = createArrowHead(nextArrow.getEndX(), nextArrow.getEndY(), true);
        Polygon prevArrowHead = createArrowHead(prevArrow.getEndX(), prevArrow.getEndY(), false);

        // Add everything to the pane
        pane.getChildren().addAll(nextArrow, nextArrowHead, prevArrow, prevArrowHead);
    }
}
