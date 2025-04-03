package org.metropolia.uidblocalization;

import java.sql.*;
import java.util.ArrayList;

import io.github.cdimascio.dotenv.Dotenv;

public class MariaDBJdbc {
    private String url;
    private String user;
    private String password;
    public MariaDBJdbc() {
        Dotenv dotenv = Dotenv.load();
        url = "jdbc:mariadb://localhost:3306/job_localization";
        user = "root";
        password = dotenv.get("PASSWORD");
    }
    // example code
/*    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM my_table")) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    public ArrayList<String> retrieveJobTitles(int languageId){
        String query = "SELECT \n" +
                "    jt.id AS job_id, \n" +
                "    COALESCE(jt_trans.translation_text, jt_default.translation_text, jt.name) AS localized_job_title\n" +
                "FROM job_title jt\n" +
                "LEFT JOIN job_translation jt_trans \n" +
                "    ON jt.id = jt_trans.job_id \n" +
                "    AND jt_trans.language_id = " + languageId + "\n" +
                "LEFT JOIN job_translation jt_default \n" +
                "    ON jt.id = jt_default.job_id \n" +
                "    AND jt_default.language_id = 1;\n";
        try(Connection connection = DriverManager.getConnection(url,user,password)){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<String> jobTitles = new ArrayList<>();
            while(resultSet.next()){
                jobTitles.add(resultSet.getString("localized_job_title"));
                System.out.println(jobTitles);
            }
            return jobTitles;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean createJobTitle(int languageId, String jobTitle, String localizedJobTitle) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Step 1: Check if the job title already exists
            String checkJobTitleQuery = "SELECT id FROM job_title WHERE name = ?";
            PreparedStatement checkJobStmt = connection.prepareStatement(checkJobTitleQuery);
            checkJobStmt.setString(1, jobTitle);
            ResultSet resultSet = checkJobStmt.executeQuery();

            int jobId;
            if (resultSet.next()) {
                // Job title exists, retrieve its ID
                jobId = resultSet.getInt("id");
            } else {
                // Step 2: Insert new job title since it doesn't exist
                String insertJobTitleQuery = "INSERT INTO job_title (name) VALUES (?)";
                PreparedStatement insertJobStmt = connection.prepareStatement(insertJobTitleQuery, Statement.RETURN_GENERATED_KEYS);
                insertJobStmt.setString(1, jobTitle);
                insertJobStmt.executeUpdate();

                // Get the generated job ID
                ResultSet generatedKeys = insertJobStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    jobId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve job ID.");
                }
            }
            if(languageId != 1)
            {
                String insertTranslationQuery = "INSERT INTO job_translation (language_id, job_id, translation_text) VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE translation_text = VALUES(translation_text)";
                PreparedStatement insertTranslationStmt = connection.prepareStatement(insertTranslationQuery);
                insertTranslationStmt.setInt(1, languageId);
                insertTranslationStmt.setInt(2, jobId);
                insertTranslationStmt.setString(3, localizedJobTitle);
                insertTranslationStmt.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
