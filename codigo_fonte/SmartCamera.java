public class SmartCamera extends SmartDevice{
    private int resX;
    private int resY;
    private float sizeOfFile;

    public SmartCamera() {
        // initialise instance variables
        super();
        this.resX = 0;
        this.resY = 0;
        this.sizeOfFile = 0;
    }

    public SmartCamera(String id) {
        // initialise instance variables
        super(id);
        this.resX = 0;
        this.resY = 0;
        this.sizeOfFile = 0;
    }

    public SmartCamera(String id, int resX, int resY, int size) {
        // initialise instance variables
        super(id);
        this.resX = resX;
        this.resY = resY;
        this.sizeOfFile = size;
    }

    public SmartCamera(SmartCamera sc) {
        super(sc);
        this.resX = sc.getResX();
        this.resY = sc.getResY();
        this.sizeOfFile = sc.getSizeOfFile();
    }

    public int getResX() {
        return resX;
    }

    public int getResY() {
        return resY;
    }

    public float getSizeOfFile() {
        return sizeOfFile;
    }

    public void setResX(int resX) {
        this.resX = resX;
    }

    public void setResY(int resY) {
        this.resY = resY;
    }

    public void setSizeOfFile(float sizeOfFile) {
        this.sizeOfFile = sizeOfFile;
    }

    public double dailyConsumption(){
        return this.sizeOfFile*resX*resY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartCamera that = (SmartCamera) o;
        return resX == that.resX && resY == that.resY && this.sizeOfFile == that.sizeOfFile;
    }

    @Override
    public String toString(){
        return String.format("{Device: SmartCamera, ID: %s, ON/OFF: %s, Dimensions: %dx%d, FileSize: %f}", this.getID(), this.getOn() ? "ON" : "OFF" ,this.resX,this.resY,this.sizeOfFile);
    }

    @Override
    public SmartDevice clone() {
        return new SmartCamera(this);
    }
}


