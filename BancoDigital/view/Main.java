package BancoDigital.view;

import BancoDigital.model.Banco;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();
        Menu menu = new Menu(banco);
        menu.exibir();
    }
}