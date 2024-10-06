package org.example.project.dsaafinalproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.example.project.dsaafinalproject.ArrowRepresentation;
import org.example.project.dsaafinalproject.NodeRepresentation;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Simulation {
    private LinkedHashMap<String, NodeRepresentation> nodes = new LinkedHashMap<>();
    private List<NodeRepresentation> nodeList = new ArrayList<>();  // List to preserve insertion order
    private File selectedFile;

    public Simulation(File selectedFile) {
        this.selectedFile = selectedFile;
        if (selectedFile != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                // Node dimensions
                double nodeWidth = 127;
                double nodeHeight = 66;
                double yPos = (200 - nodeHeight) / 2;  // Vertically centered within the 200px height

                // Start at x = 0
                double xPos = 90;
                double gap = 90;  // Optional: Gap between nodes, adjust as necessary
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    for (int i = 0; i < values.length; i++) {
                        NodeRepresentation node = new NodeRepresentation(values[i], xPos, yPos);
                        nodes.put(values[i], node);
                        nodeList.add(node);  // Add nodes to the list in insertion order
                        xPos += nodeWidth + gap;
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startSimulation(AnchorPane pane) {
        Timeline timeline = new Timeline();
        ArrowRepresentation arrowRep = new ArrowRepresentation();

        int delayBetweenNodes = 1000;
        int index = 0;
        final NodeRepresentation[] previousNode = {null};  // Track previous node for arrow generation


        for (NodeRepresentation currentNode : nodeList) {  // Iterate through the list instead of the map
            // Create a KeyFrame for each node with a delay
            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(index * delayBetweenNodes),
                    e -> {
                        pane.getChildren().add(currentNode);  // Add the node to the pane

                        // If there is a previous node, add arrows between the current and previous nodes
                        if (previousNode[0] != null) {
                            arrowRep.addArrowsToPane(pane, previousNode[0], currentNode);

                        } else {
                            // If this is the first node, add a "Null" arrow on the left
                            System.out.println("YEah");
//                            pane.getChildren().add(arrowRep.createDirectionalArrow(currentNode, "Null", true));
                            arrowRep.createHeadArrow(pane, currentNode);
                            arrowRep.createNullArrow(pane, previousNode[0], currentNode,true);
                        }

                        // Set current node as previous for the next iteration
                        previousNode[0] = currentNode;
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
            index++;
        }

        // After all nodes have been added, add a "Null" arrow to the last node on the right
        timeline.setOnFinished(e -> {
            if (!nodeList.isEmpty()) {
                System.out.println("Yeah last na ni");
                NodeRepresentation lastNode = nodeList.get(nodeList.size() - 1);
                arrowRep.createNullArrow(pane, previousNode[0], lastNode,false);
            }
        });

        // Start the timeline animation
        timeline.play();
    }
}
