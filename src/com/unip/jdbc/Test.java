package com.unip.jdbc;

import java.sql.Statement;

/**
 *
 * @author vanessa
 */
public class Test {
    public static void main(String[] args) {
        DBConnect connect = new DBConnect();
        ClientsTable clients = new ClientsTable();
        OrdersTable orders = new OrdersTable();
        
        Statement statement = connect.connect();
        
        if(connect.isConnected()){
//            clients.insertValues(statement, "Vanessa", "123456789", "Rua São João Batista, 643, Santa Libânia", "(11)95304-2797");
//            clients.showTable(statement);
//            clients.updateValues(statement, "Vanessa Molinari", "123456789",  "Rua São João Batista, 643, Santa Libânia", "(11)95304-2797");
//            clients.removeValues(statement, "123456789");
            orders.insertValues(statement, 1, "123456789", "Pão comum, hambúrguer de carne, alface, cebola, pimentão, ketchup, mostarda, maionese, coca-cola", (float) 45.00, "Cartão");
            orders.showTable(statement);
            connect.disconnect();
        }
        else{
            System.err.println("Can't connect to database.");
        }
    }
}
