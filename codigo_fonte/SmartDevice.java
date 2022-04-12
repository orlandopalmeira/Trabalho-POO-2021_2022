/**
 * A classe SmartDevice é um contactor simples.
 * Permite ligar ou desligar circuitos. 
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class SmartDevice implements Comparable<SmartDevice> {

    private String id;
    private boolean on;
    private double totalConsumption;

    /**
     * Constructor for objects of class SmartDevice
     */
    public SmartDevice() {
        this.id = "";
        this.on = false;
        this.totalConsumption = 0.0;
    }

    public SmartDevice(String id) {
        this.id = id;
        this.on = false;
        this.totalConsumption = 0.0;
    }

    public SmartDevice(String id, boolean b) {
        this.id = id;
        this.on = b;
        this.totalConsumption = 0.0;
    }

    public SmartDevice(SmartDevice sd) {
        this.id = sd.getID();
        this.on = sd.getOn();
        this.totalConsumption = sd.totalConsumption;
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

    public double getTotalConsumption(){
        return this.totalConsumption;
    }

    public void incrementTotalConsumption(double increment){
        if(increment >= 0.0){
            this.totalConsumption += increment;
        }
    }

    public void resetTotalConsumption(){
        this.totalConsumption = 0.0;
    }

    @Override
    public int compareTo(SmartDevice dev){
        return this.id.compareTo(dev.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartDevice that = (SmartDevice) o;
        return on == that.on && this.id.equals(that.id);
    }

    @Override
    public abstract SmartDevice clone();

    @Override
    public abstract String toString();
}
