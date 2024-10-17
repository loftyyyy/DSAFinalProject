package org.example.project.dsaafinalproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos; // Add this import
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.*;
import java.util.*;

public class Simulation {
    private LinkedHashMap<String, NodeRepresentation> nodes = new LinkedHashMap<>();
    private List<NodeRepresentation> nodeList = new ArrayList<>();
    private File selectedFile;
    private boolean continueSimulation = true;

    private Label numberOfElements;
    private Label simulationRunningTime;

    private Label reverseSimulationRunningTime;

    private String remark;
    private Label welcomeText1;

    Timeline timeline = new Timeline();

    public Simulation(File selectedFile, Label welcomeText1, Label numberOfElements, Label simulationRunningTime, Label reverseSimulationRunningTime) {
        this.selectedFile = selectedFile;
        this.numberOfElements = numberOfElements;
        this.simulationRunningTime = simulationRunningTime;
        this.reverseSimulationRunningTime = reverseSimulationRunningTime;
        this.welcomeText1 = welcomeText1;

        if (selectedFile != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                double nodeWidth = 127;
                double nodeHeight = 66;
                double yPos = (200 - nodeHeight) / 2;
                double xPos = 90;
                double gap = 90;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    for (int i = 0; i < values.length; i++) {
                        NodeRepresentation node = new NodeRepresentation(values[i], xPos, yPos);
                        nodes.put(values[i], node);
                        nodeList.add(node);
                        xPos += nodeWidth + gap;
                    }
                }
                numberOfElements.setText("Number of elements: " + nodeList.size());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void startSimulation(AnchorPane pane) {
        // Clear timeline keyframes before adding new ones
        timeline.getKeyFrames().clear();

        if (nodeList.isEmpty()) {
            this.remark = "No action required (Empty List)";
            welcomeText1.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
        } else if (nodeList.size() == 1) {
            this.remark = "Single node remains unchanged";
            welcomeText1.setStyle("-fx-font-weight: bold; -fx-text-fill: yellow;");
        } else {
            this.remark = "Reversal successful";
            welcomeText1.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
        }

        if (nodeList.size() == 1 || nodeList.isEmpty()) {
            welcomeText1.setText(this.remark);
            welcomeText1.setAlignment(Pos.CENTER);
            welcomeText1.setMaxWidth(Double.MAX_VALUE);
            return;
        }

        long startTime = System.currentTimeMillis();
        ArrowRepresentation arrowRep = new ArrowRepresentation();

        int delayBetweenNodes = 1000;
        int index = 0;
        final NodeRepresentation[] previousNode = {null};

        if (continueSimulation) {
            for (NodeRepresentation currentNode : nodeList) {
                KeyFrame keyFrame = new KeyFrame(
                        Duration.millis(index * delayBetweenNodes),
                        e -> {
                            // Check if the node is already added to prevent duplicates
                            if (!pane.getChildren().contains(currentNode)) {
                                pane.getChildren().add(currentNode);
                            }

                            if (previousNode[0] != null) {
                                arrowRep.addArrowsToPane(pane, previousNode[0], currentNode);
                            } else {
                                arrowRep.createHeadArrow(pane, currentNode);
                                arrowRep.createNullArrow(pane, previousNode[0], currentNode, true);
                            }

                            previousNode[0] = currentNode;
                        }
                );
                timeline.getKeyFrames().add(keyFrame);
                index++;
            }
        } else {
            System.out.println("Stopped");
        }

        timeline.setOnFinished(e -> {
            if (!nodeList.isEmpty()) {
                NodeRepresentation lastNode = nodeList.get(nodeList.size() - 1);
                arrowRep.createNullArrow(pane, previousNode[0], lastNode, false);
            }
            long endTime = System.currentTimeMillis();
            simulationRunningTime.setText("Simulation Time: " + (endTime - startTime) + "ms");
        });

        timeline.play();
    }

    public void clearSimulation(AnchorPane pane, AnchorPane reversedPane, Label welcomeText, Label numberOfElements) {
        continueSimulation = false;
        welcomeText.setText("");
        numberOfElements.setText("");
        this.simulationRunningTime.setText("");
        this.reverseSimulationRunningTime.setText("");

        if (timeline != null) {
            timeline.stop();
        }
        nodes.clear();
        nodeList.clear();

        pane.getChildren().clear();
        reversedPane.getChildren().clear();
    }

    public void reverseSimulation(AnchorPane reversedPane) {
        long startTime = System.currentTimeMillis(); // Start time measurement
        nodes.clear();
        nodeList.clear();
        reversedPane.getChildren().clear(); // Clear the pane before adding new nodes

        ArrayList<String> valuesList = new ArrayList<>();
        if (selectedFile != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                double nodeWidth = 127;
                double nodeHeight = 66;
                double yPos = (200 - nodeHeight) / 2;
                double xPos = 90;
                double gap = 90;

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    valuesList.addAll(Arrays.asList(values));
                }

                if (valuesList.isEmpty()) {
                    this.remark = "No action required (Empty List)";
                    welcomeText1.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
                } else if (valuesList.size() == 1) {
                    this.remark = "Single node remains unchanged";
                    welcomeText1.setStyle("-fx-font-weight: bold; -fx-text-fill: yellow;");
                } else {
                    this.remark = "Reversal successful";
                    welcomeText1.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
                }

                Collections.reverse(valuesList);

                for (String value : valuesList) {
                    NodeRepresentation newNode = new NodeRepresentation(value, xPos, yPos);
                    nodes.put(value, newNode);
                    nodeList.add(newNode);
                    xPos += nodeWidth + gap;
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        if(valuesList.size() == 1 || valuesList.isEmpty()){
            welcomeText1.setText(this.remark);
            welcomeText1.setAlignment(Pos.CENTER);
            welcomeText1.setMaxWidth(Double.MAX_VALUE);
            return;

        }
        startSimulation(reversedPane);

        // Measure the time after the simulation completes
        timeline.setOnFinished(e -> {
            // Ensure the last node's null arrow is generated
            if (!nodeList.isEmpty()) {
                NodeRepresentation lastNode = nodeList.get(nodeList.size() - 1);
                NodeRepresentation previousNode = nodeList.get(nodeList.size() - 1);
                ArrowRepresentation arrowRep = new ArrowRepresentation();
                arrowRep.createNullArrow(reversedPane, previousNode, lastNode, false);
            }
            long endTime = System.currentTimeMillis();
            reverseSimulationRunningTime.setText("Reverse Simulation Time: " + (endTime - startTime) + " ms");

        });

        timeline.play();
    }
}