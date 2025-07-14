package model;

import java.util.*;
import java.util.stream.Collectors;

public class Tabuleiro {
    private final List<List<Celula>> grade;

    public Tabuleiro(int[][] inicial) {
        grade = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<Celula> linha = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                if (inicial[i][j] != 0) {
                    linha.add(new CelulaFixa(i, j, inicial[i][j]));
                } else {
                    linha.add(new CelulaUsuario(i, j));
                }
            }
            grade.add(linha);
        }
    }

    public void exibir() {
        System.out.println("\n   0 1 2   3 4 5   6 7 8");
        System.out.println("  ┌───────┬───────┬───────┐");
        
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("  ├───────┼───────┼───────┤");
            }
            
            System.out.print(i + " │ ");
            
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("│ ");
                }
                
                Celula celula = grade.get(i).get(j);
                String exibicao = celula.exibir();
                
                if (!celula.ehEditavel()) {
                    System.out.print("\033[1;36m" + exibicao + "\033[0m ");
                } else {
                    System.out.print(exibicao + " ");
                }
            }
            System.out.println("│");
        }
        System.out.println("  └───────┴───────┴───────┘");
    }

    public boolean definirValorCelula(int linha, int coluna, int valor) {
        if (linha < 0 || linha >= 9 || coluna < 0 || coluna >= 9) {
            System.out.println("Posição inválida! Use valores entre 0 e 8.");
            return false;
        }
        
        if (valor < 1 || valor > 9) {
            System.out.println("Valor inválido! Use valores entre 1 e 9.");
            return false;
        }
        
        Celula celula = grade.get(linha).get(coluna);
        
        if (!celula.ehEditavel()) {
            System.out.println("Esta célula não pode ser editada!");
            return false;
        }
        
        if (ehValido(linha, coluna, valor)) {
            celula.definirValor(valor);
            System.out.println("Jogada válida!");
            return true;
        } else {
            System.out.println("Movimento viola as regras do Sudoku!");
            return false;
        }
    }

    public boolean ehValido(int linha, int coluna, int valor) {
        if (!grade.get(linha).get(coluna).ehEditavel()) {
            return false;
        }
        
        for (int j = 0; j < 9; j++) {
            if (j != coluna && grade.get(linha).get(j).obterValor() == valor) {
                return false;
            }
        }
        
        for (int i = 0; i < 9; i++) {
            if (i != linha && grade.get(i).get(coluna).obterValor() == valor) {
                return false;
            }
        }
        
        int inicioLinha = (linha / 3) * 3;
        int inicioColuna = (coluna / 3) * 3;
        
        for (int i = inicioLinha; i < inicioLinha + 3; i++) {
            for (int j = inicioColuna; j < inicioColuna + 3; j++) {
                if ((i != linha || j != coluna) && grade.get(i).get(j).obterValor() == valor) {
                    return false;
                }
            }
        }
        
        return true;
    }

    public boolean estaCompleto() {
        boolean completo = grade.stream()
                .flatMap(List::stream)
                .allMatch(celula -> celula.obterValor() != 0);
        
        if (!completo) {
            return false;
        }
        
        return ehTabuleiroValido();
    }
    
    private boolean ehTabuleiroValido() {
        for (int i = 0; i < 9; i++) {
            if (!ehGrupoValido(obterLinha(i))) {
                return false;
            }
        }
        
        for (int j = 0; j < 9; j++) {
            if (!ehGrupoValido(obterColuna(j))) {
                return false;
            }
        }

        for (int linhaCaixa = 0; linhaCaixa < 3; linhaCaixa++) {
            for (int colunaCaixa = 0; colunaCaixa < 3; colunaCaixa++) {
                if (!ehGrupoValido(obterCaixa(linhaCaixa * 3, colunaCaixa * 3))) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private boolean ehGrupoValido(List<Integer> grupo) {
        List<Integer> valoresNaoZero = grupo.stream()
                .filter(v -> v != 0)
                .collect(Collectors.toList());
        
        Set<Integer> valoresUnicos = new HashSet<>(valoresNaoZero);
        
        return valoresNaoZero.size() == valoresUnicos.size();
    }

    private List<Integer> obterLinha(int linha) {
        return grade.get(linha).stream()
                .map(Celula::obterValor)
                .collect(Collectors.toList());
    }

    private List<Integer> obterColuna(int coluna) {
        return grade.stream()
                .map(linha -> linha.get(coluna))
                .map(Celula::obterValor)
                .collect(Collectors.toList());
    }

    private List<Integer> obterCaixa(int linha, int coluna) {
        List<Integer> caixa = new ArrayList<>();
        int l0 = (linha / 3) * 3;
        int c0 = (coluna / 3) * 3;
        
        for (int i = l0; i < l0 + 3; i++) {
            for (int j = c0; j < c0 + 3; j++) {
                caixa.add(grade.get(i).get(j).obterValor());
            }
        }
        return caixa;
    }
    
    public String obterDica() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Celula celula = grade.get(i).get(j);
                if (celula.ehEditavel() && celula.obterValor() == 0) {
                    for (int valor = 1; valor <= 9; valor++) {
                        if (ehValido(i, j, valor)) {
                            return String.format("Dica: Tente o número %d na posição (%d, %d)", valor, i, j);
                        }
                    }
                }
            }
        }
        return "Não foi possível encontrar uma dica no momento.";
    }
}