/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

/**
 *
 * @author user
 */
public class procdure_ex {
        public static void main(String[] args) {   // do not forget to  use ojdbc6.jar  library, as well as   java.sql   package
        try {
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:hr/oracle@localhost:1521/XE");
            CallableStatement cstmt  = con.prepareCall("{ CALL  sum2(?,   ?,   ?) }");
             cstmt.registerOutParameter(3, Types.INTEGER);
            cstmt.setInt(1,   5);
            cstmt.setInt(2,   6);
            cstmt.execute( );
            System.out.println("result = " +  cstmt.getInt(3) );   	//result = 11  i.e. sum2(5,6) = 11
        } 
        catch (Exception e) {       System.out.println("error=" + e);     }  
    }
    
}
