

import java.util.*;
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

    /**
     * Constructor for objects of class CasaInteligente
     */
    public CasaInteligente() {
        // initialise instance variables
        this.morada = "";
        this.devices = new HashMap();
        this.locations = new HashMap();
    }

    public CasaInteligente(String morada) {
        // initialise instance variables
        this.morada = morada;
        this.devices = new HashMap();
        this.locations = new HashMap();
    }

    /**
     * (ADDED) Construtor de cópia.
     */
    public CasaInteligente(CasaInteligente ci){
        this.morada = ci.getMorada();
        this.devices = ci.getMapDevices();
        this.locations = ci.getMapLocations();
    }

    /**
     * (ADDED) Devolve um map com os devices.
     */
    private Map<String, SmartDevice> getMapDevices() {
        Map<String, SmartDevice> r = new HashMap<>();
        for (String k : this.devices.keySet()){
            r.put(k, this.devices.get(k).clone());
        }
        return r;
    }

    /**
     * (ADDED) Devolve uma lista com os devices duma determinada sala.
     */
    private List<String> getListDevices(String chave){
        List r = this.locations.get(chave).stream().collect(Collectors.toList());
        return r;
    }

    /**
     * (ADDED) Devolve um map com as locations.
     */
    private Map<String, List<String>> getMapLocations() {
        Map<String, List<String>> r = new HashMap<>();
        for (String k : this.locations.keySet()){
            r.put(k, this.locations.get(k).stream().collect(Collectors.toList()));
        }
        return r;
    }

    /**
     * (ADDED) Devolve a morada da casa.
     */
    public String getMorada(){
        return this.morada;
    }
    
    public void setDeviceOn(String devCode) {
        this.devices.get(devCode).turnOn();
    }
    
    public boolean existsDevice(String id) {return this.devices.containsKey(id);}
    
    public void addDevice(SmartDevice s) {
        this.devices.put(s.getID(), s.clone());
    }

    public SmartDevice getDevice(String s) {
        if (this.devices.containsKey(s)) {
            return this.devices.get(s).clone();
        }
        else return null;
    }
    
    public void setOn(String s, boolean b) {
        this.devices.get(s).setOn(b);
    }
    
    public void setAllOn(boolean b) {
        for(String s : this.devices.keySet()){
            setOn(s, b);
        }
    }
    
    public void addRoom(String s) {
        List<String> l = new ArrayList<String>();
        this.locations.put(s, l);
    }
    
    public boolean hasRoom(String s) {
        return this.locations.containsKey(s);
    }
    
    public void addToRoom (String s1, String s2) {
        this.locations.get(s1).add(s2);
    }
    
    public boolean roomHasDevice (String s1, String s2) {
        return this.locations.get(s1).contains(s2);
    }

    // (ADDED)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CasaInteligente ci = (CasaInteligente) o;
        return ci.morada.equals(morada) && ci.devices.equals(devices) && ci.locations.equals(locations);
    }

    // (ADDED)
    public CasaInteligente clone(){
        return new CasaInteligente(this);
    }
}
