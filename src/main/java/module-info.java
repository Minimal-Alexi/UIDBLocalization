module org.metropolia.uidblocalization {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.metropolia.uidblocalization to javafx.fxml;
    exports org.metropolia.uidblocalization;
}