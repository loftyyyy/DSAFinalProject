package org.example.project.dsaafinalproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Simulation {
    private HashMap<String, NodeRepresentation> nodes = new HashMap<>();
    private File selectedFile;

    public Simulation(File selectedFile){
        this.selectedFile = selectedFile;
        if (selectedFile != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    for (int i = 0; i < values.length; i++) {
                        nodes.put(values[i], new NodeRepresentation(values[i], (250 * i), 200));
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startSimulation(Pane pane) {
        Timeline timeline = new Timeline();
        ArrowRepresentation arrowRep = new ArrowRepresentation();

        int delayBetweenNodes = 1000;
        int index = 0;
        final NodeRepresentation[] previousNode = {null}; // Track previous node for arrow generation

        for (Map.Entry<String, NodeRepresentation> entry : nodes.entrySet()) {
            NodeRepresentation currentNode = entry.getValue();

            // Create a KeyFrame for each node with a delay
            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(index * delayBetweenNodes),
                    e -> {
                        pane.getChildren().add(currentNode); // Add the node to the pane

                        // If there is a previous node, add arrows between the current and previous nodes
                        if (previousNode[0] != null) {
                            arrowRep.addArrowsToPane(pane, previousNode[0], currentNode);
                        }

                        // Set current node as previous for the next iteration
                        previousNode[0] = currentNode;
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
            index++;
        }

        // Start the timeline animation
        timeline.play();
    }
}
