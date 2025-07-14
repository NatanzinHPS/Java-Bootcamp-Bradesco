package BancoDigital.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Banco {

    private List<Conta> contas = new ArrayList<>();


    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void listarContas() {
        contas.forEach(Conta::imprimirExtrato);
    }

    public List<Conta> buscarPorCliente(String nomeCliente) {
        return contas.stream()
            .filter(c -> c.getCliente().getNome().equalsIgnoreCase(nomeCliente))
            .collect(Collectors.toList());
    }

    public void listarContasOrdenadasPorSaldo() {
        contas.stream()
            .sorted((c1, c2) -> Double.compare(c2.getSaldo(), c1.getSaldo()))
            .forEach(Conta::imprimirExtrato);
    }
}