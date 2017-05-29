
package jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

public class callstoredfunction {
      public static void main(String[] args) {
        try{
          Connection con= DriverManager.getConnection("jdbc:oracle:thin:hr/oracle@localhost:1521/XE");
          CallableStatement cstmt=con.prepareCall("{?=call tax(?,?,?)}");
          cstmt.registerOutParameter(1,Types.FLOAT);
          cstmt.setInt(3,450);
          cstmt.setFloat(2,0.15f);
          cstmt.setInt(4,2);
          /*good idea to call */
          System.out.println(cstmt.getParameterMetaData().getParameterCount());
          cstmt.execute();
          System.out.println("result= "+cstmt.getFloat(1));
         }catch(Exception e){
         System.out.println("Error = " +e);
         }
      }
      
}
