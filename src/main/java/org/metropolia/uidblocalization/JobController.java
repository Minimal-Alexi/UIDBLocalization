package org.metropolia.uidblocalization;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class JobController {
    private MariaDBJdbc db;
    @FXML
    private Label labelTitle;
    @FXML
    private ComboBox<String> comboBoxLanguage;
    @FXML
    private ListView<String> listViewJobs;
    public void initialize() {
        db = new MariaDBJdbc();
        comboBoxInitializer();
        loadJobTitles(1);
    }
    private void comboBoxInitializer(){
        comboBoxLanguage.getItems().add("English");
        comboBoxLanguage.getItems().add("Española");
        comboBoxLanguage.getItems().add("Français");
        comboBoxLanguage.getItems().add("中国人");
        comboBoxLanguage.setValue("English");
        comboBoxLanguage.setOnAction(event -> {
            String selectedLanguage = comboBoxLanguage.getValue();
            languageChange(selectedLanguage);
        });
    }
    private void languageChange(String language){
        switch(language){
            case "English": {
                Locale locale = new Locale("en","US");
                resourceBundleInitialization(locale);
                loadJobTitles(1);
                break;
            }
            case "Española": {
                Locale locale = new Locale("es","ES");
                resourceBundleInitialization(locale);
                loadJobTitles(2);
                break;
            }
            case "Français": {
                Locale locale = new Locale("fr","FR");
                resourceBundleInitialization(locale);
                loadJobTitles(3);
                break;
            }
            case "中国人": {
                Locale locale = new Locale("zh","CN");
                resourceBundleInitialization(locale);
                loadJobTitles(4);
                break;
            }
        }
    }
    private void resourceBundleInitialization(Locale locale){
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        labelTitle.setText(bundle.getString("labelTitle"));
    }
    private void loadJobTitles(int languageId){
        ArrayList<String> jobTitles = db.retrieveJobTitles(languageId);
        listViewJobs.getItems().clear();
        listViewJobs.getItems().addAll(jobTitles);
    }

}