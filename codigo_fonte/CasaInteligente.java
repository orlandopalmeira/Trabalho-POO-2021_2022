import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * A CasaInteligente faz a gestão dos SmartDevices que existem e dos 
 * espaços (as salas) que existem na casa.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CasaInteligente {
   
    private String morada;
    private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    private Map<String, List<String>> locations; // Espaço -> Lista codigo dos devices
    private Pessoa proprietario;
    private EnergyProvider fornecedor;

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
    }

    public CasaInteligente(String morada) {
        // initialise instance variables
        this.morada = morada;
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
        this.proprietario = null;
        this.fornecedor = null;
    }

    public CasaInteligente(String morada, Pessoa proprietario){
        // initialise instance variables
        this.morada = morada;
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
        this.proprietario = proprietario.clone();
        this.fornecedor = null;
    }

    public CasaInteligente(String morada, Pessoa proprietario, EnergyProvider fornecedor){
        // initialise instance variables
        this.morada = morada;
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
        this.proprietario = proprietario.clone();
        this.fornecedor = fornecedor.clone();
    }

    /**
     * Construtor de cópia.
     */
    public CasaInteligente(CasaInteligente ci){
        this.morada = ci.getMorada();
        this.devices = ci.getMapDevices();
        this.locations = ci.getMapLocations();
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
     * Devolve uma lista com os devices duma determinada sala.
     */
    public List<String> getListDevices(String divisao){
        if(this.locations.containsKey(divisao)){ // para evitar erros se a divisão não existir
            return this.locations.get(divisao).stream().collect(Collectors.toList());
        }
        else return null; 
    }

    /**
     * Devolve um map com as locations.
     */
    private Map<String, List<String>> getMapLocations() {
        Map<String, List<String>> r = new HashMap<>();
        this.locations.keySet().forEach(key -> r.put(key,this.locations.get(key).stream().collect(Collectors.toList())));
        return r;
    }

    /**
     * Devolve a morada da casa.
     */
    public String getMorada(){
        return this.morada;
    }
    
    public void setDeviceOn(String devCode) {
        if(this.existsDevice(devCode)){ // para evitar erros. O get pode dar null se não existir e o turnOn dá erro nesse caso
            this.devices.get(devCode).turnOn();
        }
    }
    
    public boolean existsDevice(String id) {
        return this.devices.containsKey(id);
    }
    
    public void addDevice(SmartDevice s) {
        if(!this.existsDevice(s.getID())){ // só adiciona se não existir
            this.devices.put(s.getID(), s.clone());
        }
    }

    public SmartDevice getDevice(String s) {
        if(this.devices.containsKey(s)){ // para evitar erros. O get pode dar null se não existir e o clone dá erro
            return this.devices.get(s).clone();
        }
        else return null;
    }
    
    public void setOn(String s, boolean b) {
        if(this.devices.containsKey(s)){ // para evitar erros. O get pode dar null se não existir e o setOn dá erro
            this.devices.get(s).setOn(b);
        }
    }
    
    public void setAllOn(boolean b) {
        this.devices.values().forEach(device -> device.setOn(b));
    }
    
    public void addRoom(String s) {
        if(!this.hasRoom(s)){ // só adicionamos se a divisão nao existir
            List<String> l = new ArrayList<String>();
            this.locations.put(s, l);
        }
    }
    
    public boolean hasRoom(String s) {
        return this.locations.containsKey(s);
    }
    
    public void addToRoom (String s1, String s2) {
        if(!this.roomHasDevice(s1, s2)){
            this.locations.get(s1).add(s2);
        }
    }
    
    public boolean roomHasDevice (String s1, String s2) {
        return this.locations.get(s1).contains(s2);
    }

    public EnergyProvider getFornecedor(){
        return this.fornecedor.clone();
    }

    public void setFornecedor(EnergyProvider fornecedor){
        this.fornecedor = fornecedor.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CasaInteligente ci = (CasaInteligente) o;
        return ci.morada.equals(morada) && 
               ci.devices.equals(devices) && 
               ci.locations.equals(locations) &&
               ci.proprietario.equals(proprietario);
    }

    @Override
    public CasaInteligente clone(){
        return new CasaInteligente(this);
    }
}
