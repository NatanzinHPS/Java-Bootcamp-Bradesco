import model.Tabuleiro;
import java.util.Scanner;

public class JogoSudoku {
    private final Tabuleiro tabuleiro;
    private final Scanner scanner = new Scanner(System.in);
    private int movimentos = 0;

    public JogoSudoku(int[][] tabuleiroInicial) {
        this.tabuleiro = new Tabuleiro(tabuleiroInicial);
    }

    public void iniciar() {
        System.out.println("Bem-vindo ao Sudoku!");
        System.out.println("═══════════════════════════════════════");
        System.out.println("Instruções:");
        System.out.println("   • Digite 'ajuda' para ver comandos");
        System.out.println("   • Digite 'dica' para obter uma dica");
        System.out.println("   • Digite 'sair' para sair");
        System.out.println("   • Para jogar: linha coluna valor (ex: 0 0 5)");
        System.out.println("═══════════════════════════════════════");

        while (!tabuleiro.estaCompleto()) {
            tabuleiro.exibir();
            System.out.println("\n Movimentos realizados: " + movimentos);
            System.out.print("Sua jogada: ");
            
            String entrada = scanner.nextLine().trim().toLowerCase();
            
            if (entrada.isEmpty()) {
                continue;
            }
            
            if (entrada.equals("ajuda")) {
                mostrarAjuda();
                continue;
            }
            
            if (entrada.equals("dica")) {
                System.out.println(tabuleiro.obterDica());
                continue;
            }
            
            if (entrada.equals("sair")) {
                System.out.println("Obrigado por jogar! Até a próxima!");
                return;
            }
            
            if (!processarMovimento(entrada)) {
                System.out.println("Comando inválido! Digite 'ajuda' para ver os comandos disponíveis.");
            }
        }

        System.out.println("\n PARABÉNS! VOCÊ COMPLETOU O SUDOKU! ");
        System.out.println("════════════════════════════════════════");
        tabuleiro.exibir();
        System.out.printf(" Total de movimentos: %d%n", movimentos);
        
        String desempenho = obterMensagemDesempenho(movimentos);
        System.out.println(" " + desempenho);
        System.out.println("════════════════════════════════════════");
    }
    
    private boolean processarMovimento(String entrada) {
        String[] partes = entrada.split("\\s+");
        
        if (partes.length != 3) {
            return false;
        }
        
        try {
            int linha = Integer.parseInt(partes[0]);
            int coluna = Integer.parseInt(partes[1]);
            int valor = Integer.parseInt(partes[2]);
            
            if (tabuleiro.definirValorCelula(linha, coluna, valor)) {
                movimentos++;
                return true;
            }
            movimentos++;
            return true; 
            
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void mostrarAjuda() {
        System.out.println("\n COMANDOS DISPONÍVEIS:");
        System.out.println("═══════════════════════════════════════");
        System.out.println("Para fazer uma jogada:");
        System.out.println("Digite: linha coluna valor");
        System.out.println("Exemplo: 0 1 5 (coloca 5 na linha 0, coluna 1)");
        System.out.println();
        System.out.println("dica   - Obtém uma dica");
        System.out.println("ajuda  - Mostra esta ajuda");
        System.out.println("sair   - Sai do jogo");
        System.out.println();
        System.out.println("REGRAS DO SUDOKU:");
        System.out.println("   • Cada linha deve ter números de 1 a 9");
        System.out.println("   • Cada coluna deve ter números de 1 a 9");
        System.out.println("   • Cada caixa 3x3 deve ter números de 1 a 9");
        System.out.println("   • Números em azul são fixos e não podem ser alterados");
        System.out.println("═══════════════════════════════════════");
    }
    
    private String obterMensagemDesempenho(int movimentos) {
        if (movimentos <= 40) {
            return "EXCELENTE! Você é um mestre do Sudoku! ";
        } else if (movimentos <= 45) {
            return "MUITO BOM! Ótimo desempenho! ";
        } else if (movimentos <= 50) {
            return "BOM TRABALHO! Continue praticando! ";
        } else {
            return "PARABÉNS! Persistência é a chave do sucesso! ";
        }
    }
}