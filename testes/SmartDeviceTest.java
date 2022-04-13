import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SmartDeviceTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SmartDeviceTest {
    /**
     * Default constructor for test class SmartDeviceTest
     */
    public SmartDeviceTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    @Test
    public void testContructor() {
        // SmartBulb
        SmartBulb smartbulb1 = new SmartBulb();
        assertTrue(smartbulb1!=null);
        smartbulb1 = new SmartBulb("b1");
        assertTrue(smartbulb1!=null);
        // SmartSpeaker
        SmartSpeaker smartspeak1 = new SmartSpeaker();
        assertTrue(smartspeak1!=null);
        smartspeak1 = new SmartSpeaker("s1");
        assertTrue(smartspeak1!=null);
        // SmartCamera
        SmartCamera smartcam1 = new SmartCamera();
        assertTrue(smartcam1!=null);
        smartcam1 = new SmartCamera("c1");
        assertTrue(smartcam1!=null);
    }
    
    @Test
    public void testGetID() {
        // SmartBulb
        SmartBulb smartBulb1 = new SmartBulb();
        assertEquals("", smartBulb1.getID());
        smartBulb1 = new SmartBulb("b1");
        assertEquals("b1", smartBulb1.getID());
        // SmartSpeaker
        SmartSpeaker smartspeak1 = new SmartSpeaker();
        assertEquals("", smartspeak1.getID());
        smartspeak1 = new SmartSpeaker("s1");
        assertEquals("s1", smartspeak1.getID());
        // SmartBulb
        SmartCamera smartcam1 = new SmartCamera();
        assertEquals("", smartcam1.getID());
        smartcam1 = new SmartCamera("c1");
        assertEquals("c1", smartcam1.getID());
    }

    @Test
    public void testSetOn() {
        // SmartBulb
        SmartDevice smartbulb1 = new SmartBulb("b1");
        smartbulb1.setOn(true);
        assertTrue(smartbulb1.getOn());
        smartbulb1.setOn(false);
        assertFalse(smartbulb1.getOn());
        // SmartSpeaker
        SmartDevice smartspeak1 = new SmartSpeaker("b1");
        smartspeak1.setOn(true);
        assertTrue(smartspeak1.getOn());
        smartspeak1.setOn(false);
        assertFalse(smartspeak1.getOn());
        // SmartCamera
        SmartDevice smartcam1 = new SmartCamera("b1");
        smartcam1.setOn(true);
        assertTrue(smartcam1.getOn());
        smartcam1.setOn(false);
        assertFalse(smartcam1.getOn());
    }
    
    @Test
    public void testGetOn() {
        // SmartBulb
        SmartBulb smartDev1 = new SmartBulb("b1");
        assertFalse(smartDev1.getOn());
        smartDev1.setOn(false);
        assertFalse(smartDev1.getOn());
        smartDev1.setOn(true);
        assertTrue(smartDev1.getOn());
        // SmartSpeaker
        SmartDevice smartspeak1 = new SmartSpeaker("s1");
        assertFalse(smartspeak1.getOn());
        smartspeak1.setOn(false);
        assertFalse(smartspeak1.getOn());
        smartspeak1.setOn(true);
        assertTrue(smartspeak1.getOn());
        // SmartCamera
        SmartDevice smartCam1 = new SmartCamera("c1");
        assertFalse(smartCam1.getOn());
        smartCam1.setOn(false);
        assertFalse(smartCam1.getOn());
        smartCam1.setOn(true);
        assertTrue(smartCam1.getOn());
    }

    @Test
    public void testTotalConsumption(){
        SmartBulb bulb = new SmartBulb("b1", SmartBulb.NEUTRAL, 10);
        SmartSpeaker speaker = new SmartSpeaker("s1", "RUM", 10, "SAMSUNG");
        SmartCamera camera = new SmartCamera("c1", 1920, 1080, 100);
        // Ligados
        bulb.turnOn();
        speaker.turnOn();
        camera.turnOn();
        for (int i = 0; i < 100; i++) {
            bulb.updateTotalConsumption();
            speaker.updateTotalConsumption();
            camera.updateTotalConsumption();
        }
        assertEquals(9.6,bulb.getTotalConsumption());
        assertEquals(72.0,speaker.getTotalConsumption());
        assertEquals(497664000.0,camera.getTotalConsumption());
        // Desligados
        bulb.resetTotalConsumption();
        speaker.resetTotalConsumption();
        camera.resetTotalConsumption();
        bulb.turnOff();
        speaker.turnOff();
        camera.turnOff();
        assertEquals(0.0,bulb.getTotalConsumption());
        assertEquals(0.0,speaker.getTotalConsumption());
        assertEquals(0.0,camera.getTotalConsumption());
        for (int i = 0; i < 100; i++) {
            bulb.updateTotalConsumption();
            speaker.updateTotalConsumption();
            camera.updateTotalConsumption();
        }
        assertEquals(0.0,bulb.getTotalConsumption());
        assertEquals(0.0,speaker.getTotalConsumption());
        assertEquals(0.0,camera.getTotalConsumption());
    }

}
