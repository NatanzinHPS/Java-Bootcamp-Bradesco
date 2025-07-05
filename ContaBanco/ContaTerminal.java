package ContaBanco;

import java.util.Scanner;

public class ContaTerminal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Por favor, digite o número da conta: ");
        int numeroConta = scanner.nextInt();
        System.out.print("Informe o número da agência: ");
        String numeroAgencia = scanner.next();
        System.out.print("Por favor, digite o nome do cliente: ");
        String nomeCliente = scanner.next();
        System.out.print("Por favor, digite o saldo da conta: ");
        double saldoConta = scanner.nextDouble();
        System.out.printf("Olá %s, obrigado por criar uma conta em nosso banco, sua agência é %s, conta %d e seu saldo de R$%.2f já está disponível para saque.%n",
        nomeCliente, numeroAgencia, numeroConta, saldoConta);
        scanner.close();
    }
}