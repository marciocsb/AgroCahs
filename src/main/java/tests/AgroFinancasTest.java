package tests;

import model.*;

public class AgroFinancasTest {

    public static void main(String[] args) {

        AgroFinancas sistema = new AgroFinancas();

        Usuario joao = new Usuario("João Silva", "123.456.789-00");
        Usuario maria = new Usuario("Maria Santos", "987.654.321-00");

        sistema.adicionarLavoura(joao, new Lavoura("Milho", 20));
        sistema.adicionarLavoura(joao, new Lavoura("Trigo", 10));
        sistema.adicionarLavoura(maria, new Lavoura("Soja", 15));

        System.out.println("Lavouras cadastradas de João:");
        for (Lavoura l : sistema.getLavouras(joao)) {
            System.out.println(l.getNome());
        }

        System.out.println("\nLavouras cadastradas de Maria:");
        for (Lavoura l : sistema.getLavouras(maria)) {
            System.out.println(l.getNome());
        }

        sistema.removerLavoura(joao, "Trigo");

        System.out.println("\nDepois da remoção da lavoura Trigo de João:");
        for (Lavoura l : sistema.getLavouras(joao)) {
            System.out.println(l.getNome());
        }
    }
}