package org.metropolia.uidblocalization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MariaDBJdbc {
    private String url;
    private String user;
    private String password;
    public MariaDBJdbc() {
        url = "jdbc:mariadb://localhost:3306/job_localization";
        user = "root";
        password = "password";
    }
    // example code
/*    public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3306/mydatabase";
        String user = "root";
        String password = "password";

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

}
