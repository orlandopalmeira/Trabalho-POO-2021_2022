/**
 * Um SmartSpeaker é um SmartDevice que além de ligar e desligar permite também
 * reproduzir som.
 * Consegue ligar-se a um canal (por simplificação uma rádio online) e permite
 * a regulação do seu nível de volume.
 * @author (your name)
 * @version (a version number or a date)
 */
public class SmartSpeaker extends SmartDevice {
    public static final int MAX = 20;
    
    private int volume;
    private String channel;
    private String marca; // added this


    /**
     * Constructor for objects of class SmartSpeaker
     */
    public SmartSpeaker() {
        super(); // chama o construtor da superclasse.
        // initialise instance variables
        this.volume = 0;
        this.channel = "";
    }

    public SmartSpeaker(String s) {
        super(s); // chama o construtor da superclasse com o determinado parametro.
        // initialise instance variables
        this.volume = 10;
        this.channel = "";
    }


    public SmartSpeaker(String cod, String channel, int i) {
        // initialise instance variables
        super(cod); // chama o construtor da superclasse com o determinado parametro.
        this.channel = channel;
        if (i >= 0 && i <= MAX){
            this.volume = i;
        }
        else {
            this.volume = 0;
        }
    }


    public SmartSpeaker(SmartSpeaker ss){
        super(ss);
        this.volume = ss.getVolume();
        this.channel = ss.getChannel();
    }


    public void volumeUp() {
        if (this.volume<MAX) this.volume++;
    }

    public void volumeDown() {
        if (this.volume>0) this.volume--;
    }

    public int getVolume() {
        return this.volume;
    }
    
    public String getChannel() {return this.channel;}

    public void setChannel(String c) {
        this.channel = c;
    }

    public boolean equals (Object o){
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        SmartSpeaker s = (SmartSpeaker) o;
        return (super.equals(o) && this.volume == s.getVolume() && this.channel == s.getChannel());
    }

    @Override
    public SmartDevice clone() {
        return new SmartSpeaker(this);
    }
}
