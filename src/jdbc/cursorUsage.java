/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author user
 */
public class cursorUsage {
    public static void main(String[ ] args) {   // do not forget to  use ojdbc6.jar  library
        try {            
            Connection con = DriverManager.getConnection(  "jdbc:oracle:thin:hr/oracle@localhost:1521/XE" );
            //can be used also string  "begin ? := sel(); end;"     instead of     "{ CALL ? := sel }"
            CallableStatement  cstmt=con.prepareCall("{ CALL ? := sel }"); 
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.execute();
	//Retrieves the value of the designated parameter as an Object in the Java programming language 
	//here a ResultSet is returned
            ResultSet rs = (ResultSet) cstmt.getObject(1); 

            while (rs.next( )){
                   System.out.println(rs.getString("lname"));
            }
        } 
        catch (Exception e) {
            System.out.println("er=" + e);
        }
    }
}

