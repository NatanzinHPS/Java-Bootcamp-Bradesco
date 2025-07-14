import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        int[][] tabuleiroInicial = gerarTabuleiroValido();
        JogoSudoku jogo = new JogoSudoku(tabuleiroInicial);
        jogo.iniciar();
    }

    private static int[][] gerarTabuleiroValido() {
        int[][] tabuleiro = new int[9][9];
        if (preencherTabuleiro(tabuleiro)) {
            return removerNumeros(tabuleiro, 40);
        }
        return obterTabuleiroPadrao();
    }

    private static boolean preencherTabuleiro(int[][] tabuleiro) {
        for (int linha = 0; linha < 9; linha++) {
            for (int coluna = 0; coluna < 9; coluna++) {
                if (tabuleiro[linha][coluna] == 0) {
                    List<Integer> numeros = new ArrayList<>();
                    for (int i = 1; i <= 9; i++) {
                        numeros.add(i);
                    }
                    Collections.shuffle(numeros);

                    for (int numero : numeros) {
                        if (ehValido(tabuleiro, linha, coluna, numero)) {
                            tabuleiro[linha][coluna] = numero;
                            if (preencherTabuleiro(tabuleiro)) {
                                return true;
                            }
                            tabuleiro[linha][coluna] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static int[][] removerNumeros(int[][] tabuleiroCompleto, int numerosParaRemover) {
        int[][] quebracabeca = new int[9][9];
        for (int i = 0; i < 9; i++) {
            quebracabeca[i] = Arrays.copyOf(tabuleiroCompleto[i], 9);
        }

        List<int[]> posicoes = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                posicoes.add(new int[]{i, j});
            }
        }
        Collections.shuffle(posicoes);

        int removidos = 0;
        for (int[] posicao : posicoes) {
            if (removidos >= numerosParaRemover) break;

            int linha = posicao[0];
            int coluna = posicao[1];
            int backup = quebracabeca[linha][coluna];
            quebracabeca[linha][coluna] = 0;

            if (temSolucaoUnica(quebracabeca)) {
                removidos++;
            } else {
                quebracabeca[linha][coluna] = backup;
            }
        }

        return quebracabeca;
    }

    private static boolean ehValido(int[][] tabuleiro, int linha, int coluna, int numero) {
        for (int i = 0; i < 9; i++) {
            if (tabuleiro[linha][i] == numero) {
                return false;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (tabuleiro[i][coluna] == numero) {
                return false;
            }
        }

        int inicioLinha = (linha / 3) * 3;
        int inicioColuna = (coluna / 3) * 3;
        for (int i = inicioLinha; i < inicioLinha + 3; i++) {
            for (int j = inicioColuna; j < inicioColuna + 3; j++) {
                if (tabuleiro[i][j] == numero) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean temSolucaoUnica(int[][] tabuleiro) {
        int[][] tabuleiroTeste = new int[9][9];
        for (int i = 0; i < 9; i++) {
            tabuleiroTeste[i] = Arrays.copyOf(tabuleiro[i], 9);
        }
        return resolverSudoku(tabuleiroTeste);
    }

    private static boolean resolverSudoku(int[][] tabuleiro) {
        for (int linha = 0; linha < 9; linha++) {
            for (int coluna = 0; coluna < 9; coluna++) {
                if (tabuleiro[linha][coluna] == 0) {
                    for (int numero = 1; numero <= 9; numero++) {
                        if (ehValido(tabuleiro, linha, coluna, numero)) {
                            tabuleiro[linha][coluna] = numero;
                            if (resolverSudoku(tabuleiro)) {
                                return true;
                            }
                            tabuleiro[linha][coluna] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static int[][] obterTabuleiroPadrao() {
        return new int[][] {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
    }
}