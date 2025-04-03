module org.metropolia.uidblocalization {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.metropolia.uidblocalization to javafx.fxml;
    exports org.metropolia.uidblocalization;
}