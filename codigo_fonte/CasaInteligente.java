import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A CasaInteligente faz a gestão dos SmartDevices que existem e dos 
 * espaços (as salas) que existem na casa.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CasaInteligente{
   
    private String morada;
    private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    private Map<String, Set<String>> locations; // Espaço -> Lista codigo dos devices
    private Pessoa proprietario;
    private String fornecedor;
    private double totalConsumption;
    private double totalCost;

    /**
     * Constructor for objects of class CasaInteligente
     */
    public CasaInteligente() {
        // initialise instance variables
        this.morada = "";
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
        this.proprietario = null;
        this.fornecedor = null;
        this.totalConsumption = 0.0;
        this.totalCost = 0.0;
    }

    public CasaInteligente(String morada) {
        // initialise instance variables
        this.morada = morada;
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
        this.proprietario = null;
        this.fornecedor = null;
        this.totalConsumption = 0.0;
        this.totalCost = 0.0;
    }

    public CasaInteligente(String morada, Pessoa proprietario, String fornecedor){
        // initialise instance variables
        this.morada = morada;
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
        this.proprietario = proprietario.clone();
        this.fornecedor = fornecedor;
        this.totalConsumption = 0.0;
        this.totalCost = 0.0;
    }

    /**
     * Construtor de cópia.
     */
    public CasaInteligente(CasaInteligente ci){
        this.morada = ci.getMorada();
        this.devices = ci.getMapDevices();
        this.locations = ci.getMapLocations();
        this.proprietario = ci.getProprietario();
        this.fornecedor = ci.getFornecedor();
        this.totalConsumption = ci.getTotalConsumption();
        this.totalCost = ci.getTotalCost();
    }

    /**
     * Devolve a morada da casa.
     */
    public String getMorada(){
        return this.morada;
    }
     /**
      * Altera a morada da casa.
      */
    public void setMorada(String m){
        this.morada = m;
    }

    /**
     * Devolve um map com os devices.
     */
    private Map<String, SmartDevice> getMapDevices() {
        Map<String, SmartDevice> r = new HashMap<>();
        this.devices.keySet().forEach(key -> r.put(key,this.devices.get(key).clone()));
        return r;
    }

    /**
     * Devolve um conjunto com os devices duma determinada sala.
     */
    public Set<String> getListDevices(String divisao){
        if(this.locations.containsKey(divisao)){ // para evitar erros se a divisão não existir
            return this.locations.get(divisao).stream().collect(Collectors.toSet());
        }
        else return null;
    }

    /**
     * Devolve uma lista com os devices.
     */
    public List<SmartDevice> getDevices(){
        List<SmartDevice> result = new ArrayList<>();
        this.devices.values().forEach(device -> result.add(device.clone()));
        return result;
    }

    /**
     * Devolve um map com as locations.
     */
    private Map<String, Set<String>> getMapLocations() {
        Map<String, Set<String>> r = new HashMap<>();
        this.locations.keySet().forEach(room -> r.put(room,this.locations.get(room).stream().collect(Collectors.toSet())));
        return r;
    }

    /**
     * Devolve o proprietario da casa.
     */
    public Pessoa getProprietario(){
        return this.proprietario.clone();
    }

    /**
     * Devolve o nif do proprietario.
     */
    public int getOwnerNif(){
        return this.proprietario.getNif();
    }

    /**
     * Devolve o nome do proprietario.
     */
    public String getOwnerName(){
        return this.proprietario.getNome();
    }

    /**
     * Altera o proprietario da casa.
     */
    public void setProprietario(Pessoa p){
        this.proprietario = p.clone();
    }

    /**
     * Devolve o nome do seu fornecedor de energia.
     */
    public String getFornecedor(){
        return this.fornecedor;
    }
    
    /**
     * Altera o fornecedor de energia.
     */
    public void setFornecedor(String fornecedor){
        this.fornecedor = fornecedor;
    }


    /**
     * Liga um dispositivo.
     */
    public void setDeviceOn(String devCode) {
        if(this.existsDevice(devCode)){
            this.devices.get(devCode).turnOn();
        }
    }

    /**
     * Verifica a existencia de um dispositivo.
     */
    public boolean existsDevice(String id) {
        return this.devices.containsKey(id);
    }
    
    /**
     * Adiciona um dispositivo.
     */
    public void addDevice(SmartDevice dev) {
        if(!this.existsDevice(dev.getID())){
            this.devices.put(dev.getID(), dev.clone());
        }
    }

    /**
     * Adiciona um dispositivo numa reparticao.
     */
    public void addDevice(SmartDevice dev, String room){
        if(!this.existsDevice(dev.getID())){
            if(this.hasRoom(room)){
                this.devices.put(dev.getID(), dev.clone());
                this.locations.get(room).add(dev.getID());
            }else{
                this.addRoom(room);
                this.devices.put(dev.getID(), dev.clone());
                this.locations.get(room).add(dev.getID());
            }
        }
    }

    /**
     * Retira um dispositivo da casa.
     */
    public void removeDevice(String devID){
        if(this.devices.containsKey(devID)){
            this.locations.values().forEach(devsInRoom -> devsInRoom.remove(devID));
            this.devices.remove(devID);
        }
    }

    /**
     * Devolve o dispositivo solicitado.
     */
    public SmartDevice getDevice(String idDev) {
        if(this.devices.containsKey(idDev)){
            return this.devices.get(idDev).clone();
        }
        else return null;
    }

    /**
     * Altera o estado do dispositivo solicitado.
     */
    public void setOn(String idDev, boolean b) {
        if(this.devices.containsKey(idDev)){
            this.devices.get(idDev).setOn(b);
        }
    }

    /**
     * Liga todos os dispositivos.
     */
    public void setAllOn(boolean b) {
        this.devices.values().forEach(device -> device.setOn(b));
    }

    /**
     * Altera o estado de todos os dispositivos.
     */
    public void setAllinDivisionOn(String room, boolean b){
        this.locations.get(room).forEach(devID -> setOn(devID,b));
    }

    /**
     * Adiciona uma reparticao a casa.
     */
    public void addRoom(String room) {
        if(!this.hasRoom(room)){
            this.locations.put(room, new HashSet<>());
        }
    }

    /**
     * Verifica a existencia de uma reparticao.
     */
    public boolean hasRoom(String room) {
        return this.locations.containsKey(room);
    }

    /**
     * Adiciona um dispositivo numa reparticao.
    */
    public void addToRoom (String room, String devID) {
        if(!this.roomHasDevice(room, devID)){
            this.locations.get(room).add(devID);
        }
    }

    /**
     * Verifica a existencia de um dispositivo numa reparticao.
    */
    public boolean roomHasDevice (String room, String devID) {
        return this.locations.get(room).contains(devID);
    }

    /**
     * Retorna o consumo total desta casa.
     */
    public double getTotalConsumption(){
        return Math.round(this.totalConsumption*100.0)/100.0;
    }

    /**
     * Retorna o custo total associado ao consumo desta casa.
     */
    public double getTotalCost(){
        return Math.round(this.totalCost*100.0)/100.0;
    }

    /**
     * Reinicia os contadores de consumo e custo desta casa bem como os contadores dos seus dispositivos.
     */
    public void resetConsumptionAndCost(){
        this.totalCost = 0.0f;
        this.totalConsumption = 0.0f;
        this.devices.values().forEach(device -> device.resetTotalConsumption());
    }

    /**
     * Atualiza os contadores de consumo e custo associado a passagem de um dia.
     */
    public void passTime(EnergyProvider fornecedor){
        if(this.fornecedor.equals(fornecedor.getName())){
            this.devices.values().forEach(device -> device.updateTotalConsumption());
            this.totalConsumption = this.devices.values().stream()
                                                        .mapToDouble(device -> device.getTotalConsumption())
                                                        .sum();
            this.totalCost += fornecedor.pricePerDay(this.devices.values());
        }
    }

    public String toLineFile(){
        StringBuilder sb = new StringBuilder();
        // Morada
        sb.append(String.format("%s;",this.morada));
        // Devices por sala
        sb.append("[");
        for(String room : this.locations.keySet()){
            sb.append(String.format("(%s:[",room));
            for(String devID: this.locations.get(room)){
                sb.append(String.format("%s.",devID));
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("]),");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("];");
        // NIF proprietario 
        sb.append(String.format("%d;",this.proprietario.getNif()));
        // Fornecedor
        sb.append(String.format("%s\n",this.fornecedor));
        return sb.toString();
    }

    @Override
    /**
     * Verifica a igualdade entre duas casas.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CasaInteligente ci = (CasaInteligente) o;
        return this.morada.equals(ci.morada) && 
               this.devices.equals(ci.devices) && 
               this.locations.equals(ci.locations) &&
               this.proprietario.equals(ci.proprietario) &&
               this.fornecedor.equals(ci.fornecedor);
    }
    
    @Override
    /**
     * Copia esta casa.
     */
    public CasaInteligente clone(){
        return new CasaInteligente(this);
    }
}
