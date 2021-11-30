package com.unip.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import com.unip.jdbc.DBConnect;
import com.unip.jdbc.OrdersTable;
import com.unip.jdbc.ClientsTable;

/**
 *
 * @author luanz
 */
public class DaoOrder {
    private Statement statement;
    private ResultSet resultset;
    
    private DBConnect db = new DBConnect();
    private ClientsTable clients = new ClientsTable();
    private OrdersTable orders = new OrdersTable();
    
    public void connect() {
        statement = db.connect();
    }
    
    private boolean isConnected() {
        if(statement != null)
            return true;
        return false;
    }
    
    /**
    * Verifica se o cliente já existe na tabela 'clientes', usando seu rg.
    */
    public boolean isClientRegistered(String rg){
        try {
            if(!isConnected())
                connect();
            System.out.println("Verifying client");
            String query = "SELECT * FROM Clientes WHERE RG='" + rg + "';";
            this.resultset = statement.executeQuery(query);
            boolean isEmpty = !this.resultset.next();
            
            if(!isEmpty)
                System.out.println("Client already registered.");
            else
                System.out.println("Client not registered.");
            
            return !isEmpty;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        return false;
    }
    
    public int getClientID(String rg) {
        try {
            if(!isConnected())
                connect();
            String query = "SELECT IDCliente FROM Clientes WHERE RG='" + rg + "';";
            this.resultset = statement.executeQuery(query);
            this.resultset.next();
            int id = this.resultset.getInt("IDCliente");
            return id;
        } catch (Exception e) {
            System.err.println(e);
        }
        return 0;
    }
    
    public void insertNewClient(String name, String rg, String address, String telephone) {
        if(!isConnected())
                connect();
        clients.insertValues(statement, name, rg, address, telephone);
    }
    
    public void registerNewOrder(String rg, String description, float value, String payment) {
        if(!isConnected())
                connect();
        int clientID = getClientID(rg);
        orders.insertValues(statement, clientID, rg, description, value, payment);
    }
    
    public float getItemPrice(String item) {
        try  {
            if(!isConnected())
                connect();
            String query = "SELECT Preco FROM Produto WHERE Nome='" + item +"';";
            this.resultset = statement.executeQuery(query);
            this.resultset.next();
            float itemPrice = this.resultset.getFloat("Preco");
            return itemPrice;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage() + item);
        }
        return 0;
    }
}
