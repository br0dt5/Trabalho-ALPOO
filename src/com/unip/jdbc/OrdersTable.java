package com.unip.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author vanessa
 */
public class OrdersTable {
    private ResultSet resultset;
    
    ClientsTable clients = new ClientsTable();
    
    public void showTable(Statement statement) {
        /**
         * Printa todos os registros da tabela.
         */
        try{
            String query = "SELECT * FROM Pedidos";
            this.resultset = statement.executeQuery(query);
            while(this.resultset.next()){
                System.out.println(
                    "IDPedido: " + this.resultset.getString("IDPedido") +
                    ", IDCliente: " + this.resultset.getString("IDCliente") +
                    ", RGCliente: " + this.resultset.getString("RGCliente") +
                    ", Descricao: " + this.resultset.getString("Descricao") +
                    ", Valor: " + this.resultset.getString("Valor") +
                    ", Pagamento: " + this.resultset.getString("Pagamento") +
                    ", Data: " + this.resultset.getString("Data")
                );
            }
        }
        catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public void insertValues(Statement statement, int clientID, String rg, String description, float value, String payment) {
        /**
         * Insere as informações do novo pedido na tabela.
         */
        try{
            System.out.println("Trying to insert values: " + rg + ", " + description + ", " + value + ", " + payment);
            String query = "INSERT INTO Pedidos (IDCliente, RGCliente, Descricao, Valor, Pagamento)"
                + "VALUES (" + clientID + ", '" + rg + "', '" + description + "', " + value + ", '" + payment + "');";
            statement.executeUpdate(query);
            System.out.println("Successfully executed!");
        }
        catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}