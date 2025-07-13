public class Main {
    
    public static void main(String[] args) {
        Iphone iphone = new Iphone();
        iphone.ligar(123456789);
        iphone.atender();
        iphone.iniciarCorreioVoz();
        iphone.tocar();
        iphone.pausar();
        iphone.selecionarMusica("Minha Música Favorita");
        iphone.exibirPagina("https://www.example.com");
        iphone.adicionarNovaAba();
        iphone.atualizarPagina();
    }
}
