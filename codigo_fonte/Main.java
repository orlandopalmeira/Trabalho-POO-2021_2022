public class Main {
    /**
     * Comandos que devemos ter na simulação;
     * 1) alterar o preco de um fornecedor;
     * 2) alterar o imposto de um fornecedor;
     * 3) alterar o fornecedor de uma casa;
     * 4) ligar todos os dispositivos de uma casa;
     * 5) desligar todos os dispositivos de uma casa;
     * 6) ligar todos os dispositivos de uma repartição da casa;
     * 7) desligar todos os dispositivos de uma repartição da casa;
     * 8) ligar todos os dispositivos de todas as casas;
     * 9) desligar todos os dispositivos de todas as casas;
     * 10) ligar um dispositivo de uma casa;
     * 11) desligar um dispositivo de uma casa;
     */
    public static void main(String[] args) {
        Interface prog = new Interface();
        prog.run();      
    }
}
