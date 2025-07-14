package model;

public class CelulaFixa extends Celula {
    public CelulaFixa(int linha, int coluna, int valor) {
        super(linha, coluna);
        this.valor = valor;
    }

    @Override
    public boolean ehEditavel() {
        return false;
    }
}