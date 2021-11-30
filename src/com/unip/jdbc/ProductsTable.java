package com.unip.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author luanz
 */
public class ProductsTable {
    private ResultSet resultset;
    
    public void showTable(Statement statement) {
        /**
         * Printa todos os registros da tabela.
         */
        try{
            String query = "SELECT * FROM Produtos";
            this.resultset = statement.executeQuery(query);
            while(this.resultset.next()){
                System.out.println(
                    "IDProduto: " + this.resultset.getString("IDProduto") +
                    ", Nome: " + this.resultset.getString("Nome") +
                    ", Preço: " + this.resultset.getString("Preco") +
                    ", Categoria: " + this.resultset.getString("Categoria")
                );
            }
        }
        catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    
}
