public class EnergyProvider{
    private String name;
    private float price_kwh;
    private float tax;

    EnergyProvider(){
        this.name = "";
        this.price_kwh = 0.0f;
        this.tax = 0.0f;
    }

    EnergyProvider(String name, float price_kwh, float tax){
        this.name = name;
        this.price_kwh = price_kwh;
        this.tax = tax;
    }

    EnergyProvider(EnergyProvider ep){
        this.name = ep.name;
        this.price_kwh = ep.price_kwh;
        this.tax = ep.tax;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice_kwh() {
        return this.price_kwh;
    }

    public void setPrice_kwh(float price_kwh) {
        this.price_kwh = price_kwh;
    }

    public float getTax() {
        return this.tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float pricePerDay(int num_devices, float consumption){
        return num_devices > 10 ? (price_kwh * consumption * (1 + tax)) * 0.9f : 
                                  (price_kwh * consumption * (1 + tax)) * 0.75f; 
    }

    @Override
    public boolean equals(Object o) {
        if (o == this){
            return true;
        }
        if (o == null || (this.getClass() != o.getClass())) {
            return false;
        }
        EnergyProvider ep = (EnergyProvider) o;
        return this.name.equals(ep.name) && 
               this.price_kwh == ep.price_kwh && 
               this.tax == ep.tax;
    }

    @Override
    public String toString() {
        return String.format("{EnergyProvider, Name = %d, price_kwh = %f, tax = %f}",
                             this.name,this.price_kwh,this.tax);
    }

    @Override
    public EnergyProvider clone(){
        return new EnergyProvider(this);
    }

}
