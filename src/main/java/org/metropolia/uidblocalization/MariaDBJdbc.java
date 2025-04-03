package org.metropolia.uidblocalization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                "    AND jt_trans.language_id = \" + languageId + \"\n" +
                "LEFT JOIN job_translation jt_default \n" +
                "    ON jt.id = jt_default.job_id \n" +
                "    AND jt_default.language_id = 1;\n";
        try(Connection connection = DriverManager.getConnection(url,user,password)){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<String> jobTitles = new ArrayList<>();
            while(resultSet.next()){
                jobTitles.add(resultSet.getString("localized_job_title"));
            }
            return jobTitles;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
