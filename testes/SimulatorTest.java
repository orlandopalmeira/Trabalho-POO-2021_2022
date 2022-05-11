import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public class SimulatorTest {
    
    @Test
    public void sampleTest() throws FileNotFoundException{
        File devices_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/devices_test.txt");
        File providers_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/providers_test.txt");
        File people_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/people_test.txt");
        File houses_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/houses_test.txt");
        Map<String, EnergyProvider> providers = Generator.fileToProviders(providers_f);
        List<CasaInteligente> houses = Generator.fileToHouses(devices_f,people_f,houses_f);
        Simulator sim = new Simulator(houses,providers.values().stream().collect(Collectors.toList()));
        sim.startSimulation(LocalDateTime.parse("2022-04-14T10:00"),LocalDateTime.parse("2022-04-15T10:00"),new ArrayList<>());
        boolean test = true;
        assertEquals(4, sim.getBillsFromProvider("EDP").size());
        for(Fatura f: sim.getBillsFromProvider("EDP")){
            if(!f.getProviderName().equals("EDP")){
                test = false; break;
            }
        }
        assertTrue(test);
        assertEquals(4, sim.getBillsFromProvider("Endesa").size());
        for(Fatura f: sim.getBillsFromProvider("Endesa")){
            if(!f.getProviderName().equals("Endesa")){
                test = false; break;
            }
        }
        assertTrue(test);
        assertEquals(4, sim.getBillsFromProvider("GoldEnergy").size());
        for(Fatura f: sim.getBillsFromProvider("GoldEnergy")){
            if(!f.getProviderName().equals("GoldEnergy")){
                test = false; break;
            }
        }
        assertTrue(test);
        assertEquals(4, sim.getBillsFromProvider("Iberdrola").size());
        for(Fatura f: sim.getBillsFromProvider("Iberdrola")){
            if(!f.getProviderName().equals("Iberdrola")){
                test = false; break;
            }
        }
        assertTrue(test);
        assertEquals(4, sim.getBillsFromProvider("Galp").size());
        for(Fatura f: sim.getBillsFromProvider("Galp")){
            if(!f.getProviderName().equals("Galp")){
                test = false; break;
            }
        }
        assertTrue(test);
    }

    @Test
    public void testConsumptionOrder() throws FileNotFoundException{
        File devices_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/devices_test.txt");
        File providers_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/providers_test.txt");
        File people_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/people_test.txt");
        File houses_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/houses_test.txt");
        Map<String, EnergyProvider> providers = Generator.fileToProviders(providers_f);
        List<CasaInteligente> houses = Generator.fileToHouses(devices_f,people_f,houses_f);
        Simulator sim = new Simulator(houses,providers.values().stream().collect(Collectors.toList()));
        sim.startSimulation(LocalDateTime.parse("2022-04-14T10:00"),LocalDateTime.parse("2022-04-15T10:00"),new ArrayList<>());
        List<CasaInteligente> consumptionOrder = sim.getConsumptionOrder();
        boolean test = true;
        for (int i = 0; i+1 < consumptionOrder.size(); i++){
            if(consumptionOrder.get(i).getTotalConsumption() < 
               consumptionOrder.get(i+1).getTotalConsumption()){
                   test = false;
                   break;
            }
        }
        assertTrue(test);
    }

    @Test
    public void testBiggestProvider() throws FileNotFoundException{
        File devices_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/devices_test.txt");
        File providers_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/providers_test.txt");
        File people_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/people_test.txt");
        File houses_f = new File("/home/orlando/Desktop/Trabalho-POO-2021_2022/files/houses_test.txt");
        Map<String, EnergyProvider> providers = Generator.fileToProviders(providers_f);
        List<CasaInteligente> houses = Generator.fileToHouses(devices_f,people_f,houses_f);
        Simulator sim = new Simulator(houses,providers.values().stream().collect(Collectors.toList()));
        sim.startSimulation(LocalDateTime.parse("2022-04-14T10:00"),LocalDateTime.parse("2022-04-15T10:00"),new ArrayList<>());
        EnergyProvider ep = sim.getBiggestProvider();
        assertTrue(ep.getName().equals("Galp"));
    }

}