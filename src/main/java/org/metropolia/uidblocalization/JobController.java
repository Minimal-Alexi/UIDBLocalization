package org.metropolia.uidblocalization;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class JobController {
    int languageId;
    private MariaDBJdbc db;
    @FXML
    private Label labelTitle;
    @FXML
    private ComboBox<String> comboBoxLanguage;
    @FXML
    private ListView<String> listViewJobs;
    @FXML
    private TextField textFieldKeyItem, textFieldTranslation;
    @FXML
    private Button addButton;
    public void initialize() {
        languageId = 1;
        db = new MariaDBJdbc();
        comboBoxInitializer();
        resourceBundleInitialization(new Locale("en","US"));
        loadJobTitles();
        createJobInit();
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
                languageId = 1;
                loadJobTitles();
                break;
            }
            case "Española": {
                Locale locale = new Locale("es","ES");
                resourceBundleInitialization(locale);
                languageId = 2;
                loadJobTitles();
                break;
            }
            case "Français": {
                Locale locale = new Locale("fr","FR");
                resourceBundleInitialization(locale);
                languageId = 3;
                loadJobTitles();
                break;
            }
            case "中国人": {
                Locale locale = new Locale("zh","CN");
                resourceBundleInitialization(locale);
                languageId = 4;
                loadJobTitles();
                break;
            }
        }
    }
    private void resourceBundleInitialization(Locale locale){
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        labelTitle.setText(bundle.getString("labelTitle"));
        textFieldKeyItem.setPromptText(bundle.getString("promptTextFieldKeyItem"));
        textFieldTranslation.setPromptText(bundle.getString("promptTextFieldTranslation"));
        addButton.setText(bundle.getString("addButton"));
    }
    private void loadJobTitles(){
        ArrayList<String> jobTitles = db.retrieveJobTitles(languageId);
        listViewJobs.getItems().clear();
        listViewJobs.getItems().addAll(jobTitles);
    }
    private void createJobInit(){
        addButton.setOnAction(event -> {
            db.createJobTitle(languageId, textFieldKeyItem.getText(), textFieldTranslation.getText());
            db.retrieveJobTitles(languageId);
        });
    }

}