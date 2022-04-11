public class Main {
    public static void main(String[] args) {
        System.out.println("teste");

        CasaInteligente casaInte1 = new CasaInteligente("Gualtar");
        SmartBulb smartBul1 = new SmartBulb("b1");
        SmartSpeaker smartSpe1 = new SmartSpeaker("s1");
        SmartSpeaker smartSpe2 = new SmartSpeaker("s2");
        casaInte1.addDevice(smartBul1);
        casaInte1.addDevice(smartSpe1);
        casaInte1.addDevice(smartSpe2);
        casaInte1.addRoom("sala");
        casaInte1.addRoom("quarto");
        casaInte1.addToRoom("sala", "b1");
        casaInte1.addToRoom("sala", "s1");
        casaInte1.addToRoom("quarto", "s2");

        casaInte1.setAllinDivisionOn("sala",true);

        boolean stop = true;
    }
}
