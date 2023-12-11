package com.example.ultraspringmvc.sql;

import java.sql.*;

public class Connexion {
    private Connection con = null;

    public Connexion() {
    }

    public Connection getConnection() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/evaluation", "evaluation",
                    "evaluation");
        } catch (Exception e) {
            throw e;
        }
        return this.con;
    }
}