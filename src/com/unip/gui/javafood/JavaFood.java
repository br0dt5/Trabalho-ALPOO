package com.unip.gui.javafood;

import com.unip.dao.DaoOrder;
import javax.swing.SwingUtilities;

import jandl.wizard.Data;
import jandl.wizard.WizardBase;
import jandl.wizard.WizardCustom;
import jandl.wizard.WizardFactory;
import jandl.wizard.WizardText;
import java.util.Arrays;

public class JavaFood {
    private static DaoOrder dao = new DaoOrder();
    private static StringBuilder orderDescription = new StringBuilder();
    private static float total = 0;
    private static double finalPrice;
    
    public static void main(String[] args) throws Exception {
        // Primeira janela - Bem vindo!
        WizardBase wiz1 = WizardFactory.createBase("JavaFood");
        wiz1.setImage("!/com/unip/gui/resources/bemvindo.jpg");
        
        // Segunda janela - Selecione os ingredientes
        WizardCustom wiz2 = new WizardCustom("JavaFood - Lanche");
        wiz2.setImage("!/com/unip/gui/resources/Hamburguer.jpg");
        wiz2.addListPane("ing", "Pão", new String[]{
            "Pão de Hambúrguer",
            "Pão Quatro Queijos",
            "Pão Integral",
            "Pão Australiano"});
        wiz2.addListPane("ing", "Carne", new String[]{
            "Hambúrguer de Carne",
            "Filé de Frango",
            "Filé de Peixe"});
        wiz2.addListPane("ing", "Legumes e vegetais", new String[]{
            "Alface, Tomate",
            "Alface, Tomate, Cebola",
            "Alface, Tomate, Picles",
            "Alface, Tomate, Pimentão",
            "Alface, Tomate, Cebola, Pimentão",
            "Alface, Tomate, Cebola, Pimentão, Picles"});
        wiz2.addListPane("ing", "Molhos", new String[]{
            "Ketchup",
            "Mostarda",
            "Maionese",
            "Ketchup, Mostarda",
            "Ketchup, Mostarda, Maionese",
            "Barbecue"});
        wiz2.addListPane("ing", "Bebidas", new String[]{
            "Coca-Cola",
            "Pepsi",
            "Suco Natural",
            "Cerveja"});

        // Terceira janela - Confirmação do pedido
        WizardBase wiz3 = WizardFactory.createText("JavaFood - Confirmação",
                "!/com/unip/gui/resources/interrogacao.png", true);
        
        // Quarta janela - Dados do cliente
        String[] tag = { "Nome", "RG", "Endereço", "Telefone" };
        String[] label = { "Nome", "RG", "Endereço", "Telefone" };
        WizardBase wiz4 = WizardFactory.createField("JavaFood - Dados do Cliente", tag, label, label);
        wiz4.setImage("!/com/unip/gui/resources/formulario.png");
        
        // Quinta janela - Forma de pagamento
        WizardBase wiz5 = WizardFactory.createList(
                "Forma de pagamento",
                "pagamento",
                "Escolha a forma de pagamento:",
                new String[] {
                    "Dinheiro (5% de desconto)",
                    "Cartão de crédito",
                    "Cartão de crédito 2x (2% de juros)",
                    "Cartão de crédito 3x (5% de juros)"
                }
        );
        wiz5.setImage("!/com/unip/gui/resources/mao.jpg");
        
        // Sexta janela - Conclui pedido
        WizardBase wiz6 = WizardFactory.createText("JavaFood - Concluir pedido", 
                "!/com/unip/gui/resources/check.png", true);
        
        // Encadeamento
        wiz1.nextWizard(wiz2);
        wiz2.nextWizard(wiz3);
        wiz3.nextWizard(wiz4);
        wiz4.nextWizard(wiz5);
        wiz5.nextWizard(wiz6);
        
        // Pré processamento
        wiz3.addPreProcessor((wiz) -> wiz3PreProcessor(wiz));
        wiz5.addPostProcessor((wiz) -> wiz5PostProcessor(wiz));
        wiz6.addPreProcessor((wiz) -> wiz6PreProcessor(wiz));
        
        // Acionamento da aplicação
        SwingUtilities.invokeLater(() -> wiz1.setVisible(true));
    }

    public static void wiz3PreProcessor(WizardBase wizard) {
        String bread = getValuesFromListpane(0);
        String meat = getValuesFromListpane(1);
        String[] vegetables = getValuesFromListpane(2).split(", ");
        System.out.println(Arrays.toString(vegetables));
        String[] sauces = getValuesFromListpane(3).split(", ");
        String drink = getValuesFromListpane(4);
        
        total += dao.getItemPrice(bread);
        total += dao.getItemPrice(meat);
        for (String vegetable : vegetables) {
            total += dao.getItemPrice(vegetable);
        }
        for (String sauce : sauces) {
            total += dao.getItemPrice(sauce);
        }
        total += dao.getItemPrice(drink);
        
        orderDescription.append("Descrição do pedido:\n");
        orderDescription.append(String.format("\nPão: %s;", bread));
        orderDescription.append(String.format("\nCarne: %s;", meat));
        orderDescription.append(String.format("\nLegumes e Vegetais: %s;", Arrays.toString(vegetables).replace("[", "").replace("]", "")));
        orderDescription.append(String.format("\nMolhos: %s;", Arrays.toString(sauces).replace("[", "").replace("]", "")));
        orderDescription.append(String.format("\nBebida: %s", drink));
        orderDescription.append(String.format("\n\n\nValor: R$%s", total));

        WizardText wizardText = (WizardText)wizard;
        wizardText.setText(orderDescription.toString());
    }

    private static String getValuesFromListpane(int paneNumber) {
        Data data = Data.instance();
        return data.getAsString("Wizard2.ListPane" +
                paneNumber + ".ing.selectedValues").replace("[", "").replace("]", "");
    }

    private static void wiz5PostProcessor(WizardBase wiz) {
        String rg = getContentFromFieldpane("RG");
        
        if(!dao.isClientRegistered(rg)) {
            String name = getContentFromFieldpane("Nome");
            String address = getContentFromFieldpane("Endereço");
            String telephone = getContentFromFieldpane("Telefone");
            
            dao.insertNewClient(name, rg, address, telephone);
        }
        
        Data data = Data.instance();
        String payment = data.getAsString("Wizard5.listPane0.pagamento.selectedValues").replace("[", "").replace("]", "");
        
        switch(payment) {
            case "Dinheiro (5% de desconto)" -> {
                finalPrice = total * 0.95;
            }
            case "Cartão de crédito" -> {
                finalPrice = total;
            }
            case "Cartão de crédito 2x (2% de juros)" -> {
                finalPrice = total * 1.02;
            }
            case "Cartão de crédito 3x (5% de juros)" -> {
                finalPrice = total * 1.05;
            }
        }
        
        dao.registerNewOrder(rg, orderDescription.toString(), (float) finalPrice, payment);
    }
    
    private static String getContentFromFieldpane(String fieldName) {
        Data data = Data.instance();
        return data.getAsString("Wizard4.fieldPane0." + fieldName);
    }

    public static void wiz6PreProcessor(WizardBase wizard) {
        Data data = Data.instance();
        String payment = data.getAsString("Wizard5.listPane0.pagamento.selectedValues").replace("[", "").replace("]", "");
        
        StringBuilder sb = new StringBuilder();
        sb.append("Confirmação do pedido\n");
        sb.append(String.format("\nNome do cliente: %s;",
                getContentFromFieldpane("Nome")));
        sb.append(String.format("\nRG: %s;",
                getContentFromFieldpane("RG")));
        sb.append(String.format("\nEndereço de entrega: %s.",
                getContentFromFieldpane("Endereço")));
        sb.append(String.format("\n\n\nValor do pedido: R$%s", total));
        sb.append(String.format("\nForma de Pagamento: %s", payment));
        
        switch(payment) {
            case "Dinheiro (5% de desconto)" -> {
                sb.append(String.format("\nTotal: R$%.2f", finalPrice));
            }
            case "Cartão de crédito" -> {
                sb.append(String.format("\nTotal: R$%.2f", finalPrice));
            }
            case "Cartão de crédito 2x (2% de juros)" -> {
                sb.append(String.format("\nTotal: 2x de R$%.2f", finalPrice/2));
            }
            case "Cartão de crédito 3x (5% de juros)" -> {
                sb.append(String.format("\nTotal: 3x de R$%.2f", finalPrice/3));
            }
        }
        
        WizardText wizardtext = (WizardText)wizard;
        wizardtext.setText(sb.toString());
    }
}