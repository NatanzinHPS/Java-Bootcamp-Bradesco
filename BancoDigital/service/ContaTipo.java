package BancoDigital.service;

import BancoDigital.model.Cliente;
import BancoDigital.model.Conta;
import BancoDigital.model.ContaCorrente;
import BancoDigital.model.ContaPoupanca;

public class ContaTipo {
    public static Conta criarConta(String tipo, String nomeCliente) {
        Cliente cliente = new Cliente(nomeCliente);
        if (tipo.equalsIgnoreCase("corrente")) {
            return new ContaCorrente(cliente);
        } else if (tipo.equalsIgnoreCase("poupanca")) {
            return new ContaPoupanca(cliente);
        } else {
            throw new IllegalArgumentException("Tipo de conta inválido.");
        }
    }
}