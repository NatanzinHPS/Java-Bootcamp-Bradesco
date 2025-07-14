package model;

public abstract class Celula {
    protected int valor;
    protected final int linha;
    protected final int coluna;

    public Celula(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int obterValor() {
        return valor;
    }

    public abstract boolean ehEditavel();

    public void definirValor(int valor) {
        if (ehEditavel()) {
            this.valor = valor;
        }
    }

    public String exibir() {
        return valor == 0 ? "." : String.valueOf(valor);
    }
}