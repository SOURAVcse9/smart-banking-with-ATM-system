package bank.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Connn {
    Connection c;
    Statement s;

    public Connn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c=DriverManager.getConnection("jdbc:mysql://localhost:3306/bankSystem","root","@Sourav.0");
            s=c.createStatement();
            
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    
    
}
