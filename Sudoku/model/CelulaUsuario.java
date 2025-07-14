package model;

public class CelulaUsuario extends Celula {
    public CelulaUsuario(int linha, int coluna) {
        super(linha, coluna);
        this.valor = 0;
    }

    @Override
    public boolean ehEditavel() {
        return true;
    }
}