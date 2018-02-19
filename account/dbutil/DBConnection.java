package com.wethink.account.dbutil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class DBConnection {
    public static void main(String[] args) throws SQLException {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }
        
        Connection conn = null;
        
        try {
        	conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/wethink?", "root", "test");
        	Statement stmt= (Statement) conn.createStatement();  
        	ResultSet rs=stmt.executeQuery("select * from user");  
        	while(rs.next())  
        		System.out.println(rs.getString(1)+"  "+rs.getString(2));  
//        	String query = "CREATE TABLE user (uid VARCHAR(20), name VARCHAR(20));";
//        	conn.nativeSQL(query);
        	System.out.println();
//        	conn.commit();
        	conn.close();
        }
        catch(Exception ex){
        	conn.close();
        	System.out.println("SQLException: " + ex.getMessage());
            
        }
    }
}
