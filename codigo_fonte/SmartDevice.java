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

    /**
     * Liga o dispositivo.
     */
    public void turnOn() {
        this.on = true;
    }
    
    /**
     * Desliga o dispositivo.
     */
    public void turnOff() {
        this.on = false;
    }
    
    /**
     * Devolve o estado do dispositivo.
     */
    public boolean getOn() {
        return this.on;
    }
    
    /**
     * Altera o estado do dispositivo.
     */
    public void setOn(boolean b) {
        this.on = b;
    }
    
    /**
     * Devolve o ID do dispositivo.
     */
    public String getID() {
        return this.id;
    }

    /**
     * Retorna o consumo diario de um dispositivo.
     */
    public abstract double dailyConsumption();

    /**
     * Retorna o consumo total de um dispositivo desde o inicio da contagem.
     */
    public double getTotalConsumption(){
        return Math.round(this.totalConsumption*1000.0)/1000.0; // arredonda para tres casas decimais
    }

    /**
     * Atualiza o consumo de um dispositivo.
     */
    public void updateTotalConsumption(){
        this.totalConsumption += this.dailyConsumption();
    }

    /**
     * Reinicia a contagem de consumo do dispositivo.
     */
    public void resetTotalConsumption(){
        this.totalConsumption = 0.0;
    }

    /**
     * Retorna uma string com os dados do dispositivos para ser guardada
     * em ficheiro.
     */
    public abstract String toLineFile();

    @Override
    /**
     * Verifica a igualdade entre dois dispositivos.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartDevice that = (SmartDevice) o;
        return on == that.on && this.id.equals(that.id);
    }

    @Override
    /**
     * Copia um dispositivo.
     */
    public abstract SmartDevice clone();

    @Override
    /**
     * Devolve uma string com informação relevante sobre este dispositivo.
     */
    public abstract String toString();
}
