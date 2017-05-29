package jdbc;

import java.sql.*;

public class MyPreparedStatement_ex2 {

    public static void main(String[] args) throws SQLException {

        try (
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:hr/oracle@localhost:1521/XE");
                PreparedStatement prepSt = con.prepareStatement("SELECT * FROM T_Stud WHERE BIRTHDT BETWEEN ? AND ? order by BIRTHDT");
            ) 
        {
           // prepSt.setInt(1, 200); //hard-coded value 200 of parameter
           // prepSt.setInt(2, 300);
            prepSt.setString(1, "1-FEB-1995");
            prepSt.setString(2, "1-FEB-2000");
            try (ResultSet rs = prepSt.executeQuery()) {
                while (rs.next()) {
                    String fnm = rs.getString("Fname");
                    String lnm = rs.getString("Lname");
                    String bdate = rs.getString("Birthdt");
                    System.out.println(fnm +" "+lnm +" "+bdate);
                }
            }

        } catch (SQLException ex) {

            System.out.println("error =" +ex);


        }

    }//main
}
