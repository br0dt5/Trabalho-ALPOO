package com.unip.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
/**
 *
 * @author vanessa
 */
public class DBConnect {
    private Connection connection;
    private Statement statement;
    
    public Statement connect() {
        /**
         * Realiza conexão com o banco solicitado
         */
        String server = "localhost";
        String db = "lanchonete";
        String user = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        
        try{
            Class.forName(driver);
            System.out.println("Trying to connect database...");
            this.connection = DriverManager.getConnection("jdbc:mysql://"+server+"/"+ db, user, password);
            this.statement = this.connection.createStatement();
            System.out.println("Successfully connected!");
            
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return statement;
    }
    
    public boolean isConnected(){
        /**
         * Verifica se a conexão está ativa.
         */
        return this.connection != null;
    }
    
    public void disconnect(){
        /**
         * Desconecta do banco de dados.
         */
        try{
            System.out.println("Disconnecting from database...");
            this.connection.close();
            System.out.println("Successfully disconnected!");
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
