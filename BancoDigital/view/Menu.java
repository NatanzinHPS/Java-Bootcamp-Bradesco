package BancoDigital.view;

import java.util.Scanner;

import BancoDigital.model.Banco;
import BancoDigital.model.Conta;
import BancoDigital.service.ContaTipo;

public class Menu {

    private final Banco banco;
    private final Scanner scanner;

    public Menu(Banco banco) {
        this.banco = banco;
        this.scanner = new Scanner(System.in);
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n=== Banco Digital ===");
            System.out.println("1. Criar Conta");
            System.out.println("2. Listar Contas");
            System.out.println("3. Depositar");
            System.out.println("4. Sacar");
            System.out.println("5. Transferir");
            System.out.println("6. Relatório por Cliente");
            System.out.println("7. Relatório por Saldo");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1 -> criarConta();
                case 2 -> banco.listarContas();
                case 3 -> depositar();
                case 4 -> sacar();
                case 5 -> transferir();
                case 6 -> buscarCliente();
                case 7 -> banco.listarContasOrdenadasPorSaldo();
                case 0 -> System.out.println("Saindo................");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private void criarConta() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();

        System.out.print("Tipo de conta (corrente/poupanca): ");
        String tipo = scanner.nextLine();

        Conta conta = ContaTipo.criarConta(tipo, nome);
        banco.adicionarConta(conta);
        System.out.println("Conta criada com sucesso.");
    }

    private Conta buscarContaPorNumero() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        scanner.nextLine(); 
        return banco.getContas().stream()
                .filter(c -> c.getNumero() == numero)
                .findFirst()
                .orElse(null);
    }

    private void depositar() {
        Conta conta = buscarContaPorNumero();
        if (conta != null) {
            System.out.print("Valor do depósito: ");
            double valor = scanner.nextDouble();
            conta.depositar(valor);
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private void sacar() {
        Conta conta = buscarContaPorNumero();
        if (conta != null) {
            System.out.print("Valor do saque: ");
            double valor = scanner.nextDouble();
            conta.sacar(valor);
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private void transferir() {
        System.out.println("Conta origem");
        Conta origem = buscarContaPorNumero();

        System.out.println("Conta destino");
        Conta destino = buscarContaPorNumero();

        if (origem != null && destino != null) {
            System.out.print("Valor da transferência: ");
            double valor = scanner.nextDouble();
            origem.transferir(destino, valor);
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private void buscarCliente() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        banco.buscarPorCliente(nome).forEach(Conta::imprimirExtrato);
    }
}
