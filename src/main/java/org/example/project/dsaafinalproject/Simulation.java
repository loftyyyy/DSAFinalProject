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

    Timeline timeline = new Timeline();

    public Simulation(File selectedFile) {
        this.selectedFile = selectedFile;
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
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startSimulation(AnchorPane pane) {
        ArrowRepresentation arrowRep = new ArrowRepresentation();

        int delayBetweenNodes = 1000;
        int index = 0;
        final NodeRepresentation[] previousNode = {null};

        if (continueSimulation) {
            for (NodeRepresentation currentNode : nodeList) {
                KeyFrame keyFrame = new KeyFrame(
                        Duration.millis(index * delayBetweenNodes),
                        e -> {
                            pane.getChildren().add(currentNode);

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
        });

        timeline.play();
    }

    public void clearSimulation(AnchorPane pane, AnchorPane reversedPane, Label welcomeText) {
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

    public void reverseSimulation(AnchorPane reversedPane, Label welcomeText1) {
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

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    valuesList.addAll(Arrays.asList(values));
                }

                String remark;
                if (valuesList.isEmpty()) {
                    remark = "No action required (Empty List)";
                    welcomeText1.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
                } else if (valuesList.size() == 1) {
                    remark = "Single node remains unchanged";
                    welcomeText1.setStyle("-fx-font-weight: bold; -fx-text-fill: yellow;");
                } else {
                    remark = "Reversal successful";
                    welcomeText1.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
                }

                Collections.reverse(valuesList);

                for (String value : valuesList) {
                    NodeRepresentation node = new NodeRepresentation(value, xPos, yPos);
                    nodes.put(value, node);
                    nodeList.add(node);
                    xPos += nodeWidth + gap;
                }

                welcomeText1.setText(remark);
                welcomeText1.setAlignment(Pos.CENTER);
                welcomeText1.setMaxWidth(Double.MAX_VALUE);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        startSimulation(reversedPane);
    }
}