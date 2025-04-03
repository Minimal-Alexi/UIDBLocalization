package org.metropolia.uidblocalization;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class JobController {
    @FXML
    private Label labelTitle;
    @FXML
    private ComboBox<String> comboBoxLanguage;
    @FXML
    private ListView<String> listViewJobs;
    public void initialize() {
        comboBoxInitializer();
    }
    private void comboBoxInitializer(){
        comboBoxLanguage.getItems().add("English");
        comboBoxLanguage.getItems().add("Española");
        comboBoxLanguage.getItems().add("Français");
        comboBoxLanguage.getItems().add("中国人");
        comboBoxLanguage.setValue("English");
        comboBoxLanguage.setOnAction(event -> {
            String selectedLanguage = comboBoxLanguage.getValue();
        });
    }
    private void languageChange(String language){
        switch(language){
            case "English": {

            }
        }
    }

}