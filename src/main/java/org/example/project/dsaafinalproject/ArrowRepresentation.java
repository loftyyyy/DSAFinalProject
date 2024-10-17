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
        double startX, startY, endX, endY, width, middleTopX, middleTopY,x,y;
        int size = 10;

        Label headLabel = new Label("Head");

        // Get the starting X and Y coordinates of the node (top-left corner)
        startX = firstNode.getLayoutX();  // Top-left corner X
        startY = firstNode.getLayoutY();  // Top-left corner Y
        width = firstNode.getWidth();     // Width of the node

        // Calculate the middle-top X and Y coordinates of the node
        middleTopX = startX + (width / 2) + 60;  // Start at the center of the node's top side
        System.out.println(middleTopX);
        middleTopY = startY;                // Y stays the same at the top edge of the node

        // The end position of the arrow (adjust for how far you want the arrow to extend)
        endX = middleTopX;                  // The arrow moves vertically, so X stays the same
        endY = middleTopY - 50;             // Move upwards by 20 units (for a downward-pointing arrow, add instead of subtract)

        // Create the line representing the arrow
        Line arrow = new Line(middleTopX, middleTopY, endX, endY);

        // Optional: Set arrow stroke and other styling
        arrow.setStroke(Color.BLACK);
        arrow.setStrokeWidth(2);
        x = middleTopX;
        y = middleTopY;

        Polygon arrowHead = new Polygon();
        arrowHead.getPoints().addAll(x, y, x - size / 2, y - size, x + size / 2, y - size);
        arrowHead.setFill(Color.BLACK);


        headLabel.setLayoutX(endX + 5);
        headLabel.setLayoutY(endY - 3);

        // Add the arrow to the pane
        pane.getChildren().addAll(arrow, arrowHead, headLabel);
    }
    public void createNullArrow(Pane pane, NodeRepresentation currentNode, NodeRepresentation nextNode, boolean isLeft) {
        double startX, startY, endX, endY;
        Label nullLabel = new Label("Null");
        Polygon arrowHead;

        // Determine start and end positions based on whether it's the left or right arrow
        if (isLeft) {
            // Start at the middle-left side of the next node
            startX = nextNode.getLayoutX();
            startY = nextNode.getLayoutY() + 66 / 2; // Slight downward shift

            // End just below the middle-right side of the previous node
            endX = startX - 90;
            endY = startY;

            arrowHead = createArrowHead(endX, endY, false); // Arrow pointing left

        } else {
// Start just above the middle-right side of the current node
            startX = currentNode.getLayoutX() + 127;
            System.out.println(currentNode.getLayoutX());
            System.out.println(nextNode.getLayoutX());
            startY = currentNode.getLayoutY() + 66 / 2; // Slight upward shift

            endX = startX + 90;
            endY = startY;
            arrowHead = createArrowHead(endX, endY, true); // Arrow pointing right
        }

        // Create the arrow line
        Line arrow = new Line(startX, startY, endX, endY);
        arrow.setStroke(Color.BLACK);
        arrow.setStrokeWidth(2);

        // Calculate the midpoint of the arrow for label placement
        double midX = (startX + endX) / 2;
        double midY = (startY + endY) / 2;

        // Position the label at the midpoint, slightly above the arrow
        nullLabel.setLayoutX(midX - nullLabel.getWidth() / 2); // Center horizontally
        nullLabel.setLayoutY(midY - 20); // Place slightly above the arrow

        // Add the arrow, arrowhead, and label to the pane
        pane.getChildren().addAll(arrow, arrowHead, nullLabel);
    }
public Line createDirectionalArrow(NodeRepresentation currentNode, String labelText, boolean isLeft) {
        double startX, startY;
        double labelX, labelY;

        // Determine the starting point based on the boolean flag
        if (isLeft) {
            startX = currentNode.getLayoutX(); // Start from the left side of the current node
        } else {
            startX = currentNode.getLayoutX() + currentNode.getWidth(); // Start from the right side of the current node
        }

        startY = currentNode.getLayoutY() + 66 / 2; // Center of the current node with offset

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
