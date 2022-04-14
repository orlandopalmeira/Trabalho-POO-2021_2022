import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public class Main {
    public static void main(String[] args) throws IOException  {
         if(args[0].equals("-f")){ // recebe um ficheiro como argumento
            File devices_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/devices_test.txt");
            File providers_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/providers_test.txt");
            File people_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/people_test.txt");
            File houses_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/houses_test.txt");
            List<CasaInteligente> houses = Generator.fileToHouses(devices_f,providers_f,people_f,houses_f);
            Map<String, EnergyProvider> providers = Generator.fileToProviders(providers_f);
            Simulator sim = new Simulator(houses,providers.values().stream().collect(Collectors.toList()));
            sim.startSimulation(LocalDate.parse("2022-04-01"),LocalDate.parse("2022-04-29"));
            for(Fatura f : sim.getBillsFromProvider("EDP")){
                System.out.println(f.printFatura());
            }
        }else{
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
        }
        
        
    }
}
