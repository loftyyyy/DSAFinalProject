package org.example.project.dsaafinalproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.*;
import java.util.*;

public class Simulation {
    private LinkedHashMap<String, NodeRepresentation> nodes = new LinkedHashMap<>();
    private List<NodeRepresentation> nodeList = new ArrayList<>();  // List to preserve insertion order
    private File selectedFile;
    private boolean continueSimulation = true;

    Timeline timeline = new Timeline();

    public Simulation(File selectedFile) {
        this.selectedFile = selectedFile;
        if (selectedFile != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                // Node dimensions
                double nodeWidth = 127;
                double nodeHeight = 66;
                double yPos = (200 - nodeHeight) / 2;  // Vertically centered within the 200px height

                // Start at x = 90 (instead of 0 for some margin)
                double xPos = 90;
                double gap = 90;  // Gap between nodes
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    for (String value : values) {
                        NodeRepresentation node = new NodeRepresentation(value, xPos, yPos);
                        nodes.put(value, node);
                        nodeList.add(node);  // Add nodes to the list in insertion order
                        xPos += nodeWidth + gap;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startSimulation(AnchorPane pane) {
        ArrowRepresentation arrowRep = new ArrowRepresentation();
        timeline.getKeyFrames().clear();  // Clear any existing frames before starting

        int delayBetweenNodes = 1000;
        int index = 0;
        final NodeRepresentation[] previousNode = {null};  // Track previous node for arrow generation

        if (continueSimulation) {
            for (NodeRepresentation currentNode : nodeList) {  // Iterate through the list instead of the map
                KeyFrame keyFrame = new KeyFrame(
                        Duration.millis(index * delayBetweenNodes),
                        e -> {
                            pane.getChildren().add(currentNode);  // Add the node to the pane

                            // Add arrows between nodes
                            if (previousNode[0] != null) {
                                arrowRep.addArrowsToPane(pane, previousNode[0], currentNode);
                            } else {
                                // First node: add a "Null" arrow on the left
                                arrowRep.createHeadArrow(pane, currentNode);
                                arrowRep.createNullArrow(pane, previousNode[0], currentNode, true);
                            }

                            // Set current node as previous for the next iteration
                            previousNode[0] = currentNode;
                        }
                );
                timeline.getKeyFrames().add(keyFrame);
                index++;
            }
        }

        // After all nodes, add "Null" arrow to the last node
        timeline.setOnFinished(e -> {
            if (!nodeList.isEmpty()) {
                NodeRepresentation lastNode = nodeList.get(nodeList.size() - 1);
                arrowRep.createNullArrow(pane, previousNode[0], lastNode, false);
            }
        });

        timeline.play();
    }

    public void clearSimulation(AnchorPane pane, AnchorPane reversedPane, Label welcomeText) {
        // Reset the state of the simulation
        continueSimulation = false;
        welcomeText.setText("");
        if (timeline != null) {
            timeline.stop();
        }
        nodes.clear();
        nodeList.clear();
        pane.getChildren().clear();
        reversedPane.getChildren().clear();
    }

    public void reverseSimulation(AnchorPane reversedPane) {
        // Clear previous nodes before starting reverse
        nodes.clear();
        nodeList.clear();

        if (selectedFile != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                double nodeWidth = 127;
                double nodeHeight = 66;
                double yPos = (200 - nodeHeight) / 2;
                double xPos = 90;
                double gap = 90;
                ArrayList<String> valuesList = new ArrayList<>();

                // Read file and collect node values
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    valuesList.addAll(Arrays.asList(values));
                }
                Collections.reverse(valuesList);  // Reverse the order of the nodes

                for (String value : valuesList) {
                    NodeRepresentation node = new NodeRepresentation(value, xPos, yPos);
                    nodes.put(value, node);
                    nodeList.add(node);  // Add nodes to the list in reversed order
                    xPos += nodeWidth + gap;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Start the reversed simulation on the reversedPane
        startSimulation(reversedPane);
    }
}
