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
    public ArrayList<String> retrieveJobTitles(){
        try(Connection connection = DriverManager.getConnection(url,user,password)){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name from job_title");
            ArrayList<String> jobTitles = new ArrayList<>();
            while(resultSet.next()){
                jobTitles.add(resultSet.getString("name"));
            }
            return jobTitles;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
