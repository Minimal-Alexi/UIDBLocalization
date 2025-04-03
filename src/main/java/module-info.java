module org.metropolia.uidblocalization {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;


    opens org.metropolia.uidblocalization to javafx.fxml;
    exports org.metropolia.uidblocalization;
}