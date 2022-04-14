import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
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
        List<CasaInteligente> houses = Generator.fileToHouses(devices_f,providers_f,people_f,houses_f);
        Map<String, EnergyProvider> providers = Generator.fileToProviders(providers_f);
        Simulator sim = new Simulator(houses,providers.values().stream().collect(Collectors.toList()));
        sim.startSimulation(LocalDate.parse("2022-04-14"),LocalDate.parse("2022-04-15"));
        assertEquals(4, sim.getBillsFromProvider("EDP").size());
        assertEquals(4, sim.getBillsFromProvider("Endesa").size());
        assertEquals(4, sim.getBillsFromProvider("GoldEnergy").size());
        assertEquals(4, sim.getBillsFromProvider("Iberdrola").size());
        assertEquals(4, sim.getBillsFromProvider("Galp").size());
    }

}