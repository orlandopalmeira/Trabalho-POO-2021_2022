/**
 * A classe SmartDevice é um contactor simples.
 * Permite ligar ou desligar circuitos. 
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class SmartDevice {

    private String id;
    private boolean on;

    /**
     * Constructor for objects of class SmartDevice
     */
    public SmartDevice() {
        this.id = "";
        this.on = false;
    }

    public SmartDevice(String id) {
        this.id = id;
        this.on = false;
    }

    public SmartDevice(String id, boolean b) {
        this.id = id;
        this.on = b;
    }

    public SmartDevice(SmartDevice sd) {
        this.id = sd.getID();
        this.on = sd.getOn();
    }

    public void turnOn() {
        this.on = true;
    }
    
    public void turnOff() {
        this.on = false;
    }
    
    public boolean getOn() {
        return this.on;
    }
    
    public void setOn(boolean b) {
        this.on = b;
    }
    
    public String getID() {
        return this.id;
    }

    public abstract double dailyConsumption();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartDevice that = (SmartDevice) o;
        return on == that.on && this.id.equals(that.id);
    }

    public abstract SmartDevice clone();

    public abstract String toString();
}
