module org.example.project.dsaafinalproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.project.dsaafinalproject to javafx.fxml;
    exports org.example.project.dsaafinalproject;
}