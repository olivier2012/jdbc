package jdbc;

import java.sql.*;

public class Jdbc {

    public static void main(String[] args) {

        try //try-with- resource block, Connection is closed automatically
                (
                Connection con = DriverManager.getConnection( //use connection string with login and password

                        "jdbc:oracle:thin:hr/oracle@localhost:1521/XE"
                );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM T_stud");) {

            while (rs.next()) {

                String nm = rs.getString("fname"); //read String data from field &quot;name&quot;

                System.out.println(nm);

            }

        } catch (SQLException ex) {

            System.out.println("error = " + ex);

        }

    }


}
