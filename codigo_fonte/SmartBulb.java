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
    private double dimension;
    

    /**
     * Constructor for objects of class SmartBulb
     */
    public SmartBulb() {
        // initialise instance variables
        super();
        this.tone = NEUTRAL;
        this.dimension = 0;
    }

    public SmartBulb(String id) {
        // initialise instance variables
        super(id);
        this.tone = NEUTRAL;
        this.dimension = 0;
    }

    public SmartBulb(String id, int tone) {
        // initialise instance variables
        super(id);
        this.tone = tone;
        this.dimension = 0;
    }

    public SmartBulb(String id, int tone, double dimension){
        // initialise instance variables
        super(id);
        this.tone = tone;
        this.dimension = dimension;
    }

    public SmartBulb(SmartBulb sb) {
        super(sb);
        this.tone = NEUTRAL;
        this.dimension = sb.dimension;
    }

    /**
     * Altera a tonalidade desta lâmpada.
     */
    public void setTone(int t) {
        if (t > WARM) this.tone = WARM;
        else if (t < COLD) this.tone = COLD;
        else this.tone = t;
    }

    /**
     * Devolve a tonalidade desta lâmpada.
     */
    public int getTone() {
        return this.tone;
    }

    /**
     * Devolve a dimensão desta lâmpada.
     */
    public double getDimension(){
        return this.dimension;
    }

    @Override
    public String toLineFile(){
        //TYPE;ID(string);ON/OFF(bool);TONE(int);DIMENSION(double)
        return String.format("SmartBulb;%s;%b;%d;%f\n",this.getID(),this.getOn(),this.tone,this.dimension);
    }

    @Override
    public double dailyConsumption(){
        return this.getOn() ? ((3.0 + (double)this.tone) * 24.0) / 1000.0 : 0.0;
    }

    @Override
    public boolean equals (Object o){
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        SmartBulb s = (SmartBulb) o;
        return (super.equals(o) && this.tone == s.getTone() && this.dimension == s.getDimension());
    }

    @Override
    public String toString(){
        return String.format("{Device: SmartBulb, ID: %s, ON/OFF: %s, tone: %d, dimension: %f}", this.getID(), this.getOn() ? "ON" : "OFF" ,this.tone, this.dimension);
    }

    @Override
    public SmartDevice clone() {
        return new SmartBulb(this);
    }
}

