import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EnergyProviderTest {
    
    @Test
    public void testConstructor(){
        EnergyProvider provider = new EnergyProvider();
        assertTrue(provider != null);
        provider = new EnergyProvider("EDP",10,0.23f);
        assertTrue(provider != null);
        EnergyProvider provider2 = new EnergyProvider(provider);
        assertTrue(provider2 != null);
    }

    @Test
    public void testGetName(){
        EnergyProvider provider = new EnergyProvider();
        assertEquals("",provider.getName());
        provider = new EnergyProvider("EDP",10,0.23f);
        assertEquals("EDP",provider.getName());
    }

    @Test
    public void testSetName(){
        EnergyProvider provider = new EnergyProvider();
        assertEquals("",provider.getName());
        provider.setName("EDP");
        assertEquals("EDP",provider.getName());
    }
}
