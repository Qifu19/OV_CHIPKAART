package com.data;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        
        try {
            // get an connection
            Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OV_Chipkaart", "postgres", "Qianfu19#2004");
            // create a statement
            Statement myStmt = myConn.createStatement();
            // execute sql query
            ResultSet myRs = myStmt.executeQuery("select * from reiziger");
            // process the result set, print all reizigers
            while (myRs.next()) {
                if(myRs.getString("tussenvoegsel") == null) {
                    System.out.println("#" + myRs.getString("reiziger_id") + 
                    ": " + myRs.getString("voorletters") + 
                    ". " + myRs.getString("achternaam") + 
                    " (" + myRs.getString("geboortedatum") + ")");
                }
                else
                System.out.println("#" + myRs.getString("reiziger_id") + 
                ": " + myRs.getString("voorletters") + 
                ". " + myRs.getString("tussenvoegsel") + 
                " " + myRs.getString("achternaam") + 
                " (" + myRs.getString("geboortedatum") + ")");
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

