
package jdbc;

import java.sql.*;


public class Exer4 {
    public static void main(String[] args) throws SQLException {

        try (
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:hr/oracle@localhost:1521/XE");
                Statement stat = con.createStatement();
            ) 
        {
           
            ResultSet rs = stat.executeQuery("SELECT * FROM Books");

            while (rs.next()) {
                String nm = rs.getString("name");
                for(int i = 0; i<nm.length()-1; i++){
                    if (nm.charAt(i) == nm.charAt(i+1)){
                        System.out.println(nm);
                        break;
                    }
                }
                
            }

            rs.close();

        } catch (SQLException ex) {

            System.out.println("error =" +ex);

            ex.printStackTrace();

        }

    }//main
    
}
