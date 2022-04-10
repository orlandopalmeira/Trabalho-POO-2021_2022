/**
 * Uma SmartBulb é uma lâmpada inteligente que além de ligar e desligar (já que
 * é subclasse de SmartDevice) também permite escolher a intensidade da iluminação 
 * (a cor da mesma).
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SmartBulb extends SmartDevice {
    public static final int WARM = 2;
    public static final int NEUTRAL = 1;
    public static final int COLD = 0;
    
    private int tone;

    /**
     * Constructor for objects of class SmartBulb
     */
    public SmartBulb() {
        // initialise instance variables
        super();
        this.tone = NEUTRAL;
    }

    public SmartBulb(String id, int tone) {
        // initialise instance variables
        super(id);
        this.tone = tone;
    }

    public SmartBulb(String id) {
        // initialise instance variables
        super(id);
        this.tone = NEUTRAL;
    }

    public SmartBulb(SmartBulb sb) {
        super(sb);
        this.tone = NEUTRAL;
    }

    public void setTone(int t) {
        if (t>WARM) this.tone = WARM;
        else if (t<COLD) this.tone = COLD;
        else this.tone = t;
    }

    public int getTone() {
        return this.tone;
    }

    public boolean equals (Object o){
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        SmartBulb s = (SmartBulb) o;
        return (super.equals(o) && this.tone == s.getTone());
    }

    @Override
    public SmartDevice clone() {
        return new SmartBulb(this);
    }
}
