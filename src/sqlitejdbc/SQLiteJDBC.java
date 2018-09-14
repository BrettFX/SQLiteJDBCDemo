/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlitejdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Brett
 * 
 * Reference: https://www.tutorialspoint.com/sqlite/sqlite_java.htm
 */
public class SQLiteJDBC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        Connection c = null;
        Statement stmt = null;
        
        // Delete the database after processing
        File dbFile = new File("test.db");
        if (dbFile.delete()) {
            System.out.println("Successfully deleted datatbase.");
        } else {
            System.err.println("Could not delete database.");
        }
        
        try {
           Class.forName("org.sqlite.JDBC");
           c = DriverManager.getConnection("jdbc:sqlite:test.db");
           c.setAutoCommit(false);
           System.out.println("Opened database successfully");

           stmt = c.createStatement();
           
           // Begin table creation
           String sql = "CREATE TABLE COMPANY " +
                          "(ID INT PRIMARY KEY     NOT NULL," +
                          " NAME           TEXT    NOT NULL, " + 
                          " AGE            INT     NOT NULL, " + 
                          " ADDRESS        CHAR(50), " + 
                          " SALARY         REAL)";
           stmt.executeUpdate(sql);
           // End table creation
           
            // Begin insertions
            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                  "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
            stmt.executeUpdate(sql);
           
            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                  "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );"; 
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                     "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );"; 
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                     "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );"; 
            stmt.executeUpdate(sql);
           // End insertions
            
            // Begin query
            ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
      
            while ( rs.next() ) {
               int id = rs.getInt("id");
               String  name = rs.getString("name");
               int age  = rs.getInt("age");
               String  address = rs.getString("address");
               float salary = rs.getFloat("salary");

               System.out.println( "ID = " + id );
               System.out.println( "NAME = " + name );
               System.out.println( "AGE = " + age );
               System.out.println( "ADDRESS = " + address );
               System.out.println( "SALARY = " + salary );
               System.out.println();
            }
            rs.close();
            // End Query
            
           stmt.close();
           c.commit();
           c.close();
        } catch ( ClassNotFoundException | SQLException e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }
        
        System.out.println("Table created successfully");
   }
}
