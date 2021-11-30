package com.unip.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author vanessa
 */
public class ClientsTable {
    private ResultSet resultset;
    
    public void showTable(Statement statement) {
        /**
         * Printa todos os registros da tabela.
         */
        try{
            String query = "SELECT * FROM Clientes";
            this.resultset = statement.executeQuery(query);
            while(this.resultset.next()){
                System.out.println(
                    "IDCliente: " + this.resultset.getString("IDCliente") +
                    ", Nome: " + this.resultset.getString("Nome") +
                    ", RG: " + this.resultset.getString("RG") +
                    ", Endereco: " + this.resultset.getString("Endereco") +
                    ", Telefone: " + this.resultset.getString("Telefone") +
                    ", Data: " + this.resultset.getString("Data")
                );
            }
        }
        catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public void insertValues(Statement statement, String name, String rg, String address, String telephone) {
        /**
         * Realiza a inserção dos valores informados na tabela.
         */
        try{
            System.out.println("Trying to insert values: " + name + ", " + rg + ", " + address + ", " + telephone);
            String query = "INSERT INTO Clientes (Nome, RG, Endereco, Telefone)"
                + "VALUES ('" + name + "', '" + rg + "', '" + address + "', '" + telephone + "')";
            statement.executeUpdate(query);
            System.out.println("Successfully executed!");
        }
        catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public void updateValues(Statement statement, String name, String rg, String address, String telephone) {
        /**
         * Atualiza os valores de um cliente.
         */
        try{
            System.out.println("Trying to update values...");
            String query = "UPDATE Clientes SET Nome='" + name + "', Endereco='" + address + "', Telefone='" + telephone + "' WHERE RG='" + rg + "';";
            statement.executeUpdate(query);
            System.out.println("Successfully executed!");
                    
        }
        catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public void removeValues(Statement statement, String rg) {
        /**
         * Remove os dados de um cliente.
         */
        try{
            System.out.println("Trying to remove values...");
            String query = "DELETE FROM Clientes WHERE RG='" + rg + "';";
            statement.executeUpdate(query);    
            System.out.println("Successfully executed!");
        }
        catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}