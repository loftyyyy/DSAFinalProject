package org.example.project.dsaafinalproject;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class    ArrowRepresentation {
    private static final double ARROW_VERTICAL_OFFSET = 10; // Vertical offset to separate the arrows above and below the middle
    private static final double LABEL_OFFSET = 10; // Offset for the label

    private static final double ARROW_LENGTH = 50;


    public void createHeadArrow(Pane pane, NodeRepresentation firstNode) {
        double startX, startY, endX, endY, width, middleTopX, middleTopY, x, y;
        int size = 10; // O(1)

        Label headLabel = new Label("Head"); // O(1)

        // Get the starting X and Y coordinates of the node (top-left corner)
        startX = firstNode.getLayoutX(); // O(1)
        startY = firstNode.getLayoutY(); // O(1)
        width = firstNode.getWidth(); // O(1)

        // Calculate the middle-top X and Y coordinates of the node
        middleTopX = startX + (width / 2) + 60; // O(1)
        middleTopY = startY; // O(1)

        // The end position of the arrow (adjust for how far you want the arrow to extend)
        endX = middleTopX; // O(1)
        endY = middleTopY - 50; // O(1)

        // Create the line representing the arrow
        Line arrow = new Line(middleTopX, middleTopY, endX, endY); // O(1)

        // Optional: Set arrow stroke and other styling
        arrow.setStroke(Color.BLACK); // O(1)
        arrow.setStrokeWidth(2); // O(1)
        x = middleTopX; // O(1)
        y = middleTopY; // O(1)

        Polygon arrowHead = new Polygon(); // O(1)
        arrowHead.getPoints().addAll(x, y, x - size / 2, y - size, x + size / 2, y - size); // O(1)
        arrowHead.setFill(Color.BLACK); // O(1)

        headLabel.setLayoutX(endX + 5); // O(1)
        headLabel.setLayoutY(endY - 3); // O(1)

        // Add the arrow to the pane
        pane.getChildren().addAll(arrow, arrowHead, headLabel); // O(1)
    }

    public void createNullArrow(Pane pane, NodeRepresentation currentNode, NodeRepresentation nextNode, boolean isLeft) {
        double startX, startY, endX, endY; // O(1)
        Label nullLabel = new Label("Null"); // O(1)
        Polygon arrowHead; // O(1)

        // Determine start and end positions based on whether it's the left or right arrow
        if (isLeft) { // O(1) for comparison
            startX = nextNode.getLayoutX(); // O(1)
            startY = nextNode.getLayoutY() + 66 / 2; // O(1)
            endX = startX - 90; // O(1)
            endY = startY; // O(1)

            arrowHead = createArrowHead(endX, endY, false); // O(1)
        } else {
            startX = currentNode.getLayoutX() + 127; // O(1)
            startY = currentNode.getLayoutY() + 66 / 2; // O(1)
            endX = startX + 90; // O(1)
            endY = startY; // O(1)

            arrowHead = createArrowHead(endX, endY, true); // O(1)
        }

        // Create the arrow line
        Line arrow = new Line(startX, startY, endX, endY); // O(1)
        arrow.setStroke(Color.BLACK); // O(1)
        arrow.setStrokeWidth(2); // O(1)

        // Calculate the midpoint of the arrow for label placement
        double midX = (startX + endX) / 2; // O(1)
        double midY = (startY + endY) / 2; // O(1)

        // Position the label at the midpoint, slightly above the arrow
        nullLabel.setLayoutX(midX - nullLabel.getWidth() / 2); // O(1)
        nullLabel.setLayoutY(midY - 20); // O(1)

        // Add the arrow, arrowhead, and label to the pane
        pane.getChildren().addAll(arrow, arrowHead, nullLabel); // O(1)
    }


    public Line createDirectionalArrow(NodeRepresentation currentNode, String labelText, boolean isLeft) {
        double startX, startY; // O(1)
        double labelX, labelY; // O(1)

        if (isLeft) { // O(1) for comparison
            startX = currentNode.getLayoutX(); // O(1)
        } else {
            startX = currentNode.getLayoutX() + currentNode.getWidth(); // O(1)
        }

        startY = currentNode.getLayoutY() + 66 / 2; // O(1)

        // Calculate the label position
        labelX = startX + (isLeft ? -LABEL_OFFSET : LABEL_OFFSET); // O(1)
        labelY = startY; // O(1)

        // Create the line arrow
        Line arrow = new Line(startX, startY, labelX, labelY); // O(1)
        arrow.setStroke(Color.BLACK); // O(1)
        arrow.setStrokeWidth(2); // O(1)

        Label label = new Label(labelText); // O(1)
        label.setLayoutX(labelX); // O(1)
        label.setLayoutY(labelY - 10); // O(1)

        return arrow; // O(1)
    }

    // Method to create a "next" arrow starting just above the middle-right side of the current node
    public Line createNextArrow(NodeRepresentation currentNode, NodeRepresentation nextNode) {
        double startX = currentNode.getLayoutX() + currentNode.getWidth(); // O(1)
        double startY = currentNode.getLayoutY() + 66 / 2 - ARROW_VERTICAL_OFFSET; // O(1)

        double endX = nextNode.getLayoutX(); // O(1)
        double endY = nextNode.getLayoutY() + 66 / 2 - ARROW_VERTICAL_OFFSET; // O(1)

        Line arrow = new Line(startX, startY, endX, endY); // O(1)
        arrow.setStroke(Color.BLACK); // O(1)
        arrow.setStrokeWidth(2); // O(1)

        return arrow; // O(1)
    }


    // Method to create a "previous" arrow starting just below the middle-left side of the current node
    public Line createPrevArrow(NodeRepresentation currentNode, NodeRepresentation prevNode) {
        double startX = currentNode.getLayoutX(); // O(1)
        double startY = currentNode.getLayoutY() + 66 / 2 + ARROW_VERTICAL_OFFSET; // O(1)

        double endX = prevNode.getLayoutX() + prevNode.getWidth(); // O(1)
        double endY = prevNode.getLayoutY() + 66 / 2 + ARROW_VERTICAL_OFFSET; // O(1)

        Line arrow = new Line(startX, startY, endX, endY); // O(1)
        arrow.setStroke(Color.BLACK); // O(1)
        arrow.setStrokeWidth(2); // O(1)

        return arrow; // O(1)
    }


    // Method to create an arrowhead for a given arrow
    private Polygon createArrowHead(double x, double y, boolean isNext) {
        Polygon arrowHead = new Polygon(); // O(1)
        double size = 10; // O(1)

        if (isNext) { // O(1) for comparison
            arrowHead.getPoints().addAll(x, y, x - size, y - size / 2, x - size, y + size / 2); // O(1)
        } else {
            arrowHead.getPoints().addAll(x, y, x + size, y - size / 2, x + size, y + size / 2); // O(1)
        }
        arrowHead.setFill(Color.BLACK); // O(1)

        return arrowHead; // O(1)
    }


    public void addArrowsToPane(Pane pane, NodeRepresentation currentNode, NodeRepresentation nextNode) {
        Line nextArrow = createNextArrow(currentNode, nextNode); // O(1)
        Line prevArrow = createPrevArrow(nextNode, currentNode); // O(1)

        Polygon nextArrowHead = createArrowHead(nextArrow.getEndX(), nextArrow.getEndY(), true); // O(1)
        Polygon prevArrowHead = createArrowHead(prevArrow.getEndX(), prevArrow.getEndY(), false); // O(1)

        pane.getChildren().addAll(nextArrow, nextArrowHead, prevArrow, prevArrowHead); // O(1)
    }



}
