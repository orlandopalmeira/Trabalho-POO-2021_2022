import java.util.Collection;
public class EnergyProvider implements Comparable<EnergyProvider>{
    private String name;
    private float price_kwh;
    private float tax;
    private double volumeFaturacao;

    public EnergyProvider(){
        this.name = "";
        this.price_kwh = 0.0f;
        this.tax = 0.0f;
        this.volumeFaturacao = 0.0f;
    }

    public EnergyProvider(String name){
        this.name = name;
        this.price_kwh = 0.15f;
        this.tax = 0.23f;
        this.volumeFaturacao = 0.0f;
    }

    public EnergyProvider(String name, float price_kwh, float tax){
        this.name = name;
        this.price_kwh = price_kwh;
        this.tax = tax;
        this.volumeFaturacao = 0.0f;
    }

    public EnergyProvider(EnergyProvider ep){
        this.name = ep.name;
        this.price_kwh = ep.price_kwh;
        this.tax = ep.tax;
        this.volumeFaturacao = 0.0f;
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

    private double pricePerDayPerDevice(int numDevices, SmartDevice device){
        return this.price_kwh * device.dailyConsumption() * (1 + this.tax) * (numDevices > 10 ? 0.9f : 0.75f);
    }

    public double pricePerDay(Collection<SmartDevice> devices){
        return devices.stream()
                      .mapToDouble(dev -> this.pricePerDayPerDevice(devices.size(), dev))
                      .sum();
    }

    public double getVolumeFaturacao(){
        return this.volumeFaturacao;
    }

    public void updateVolumeFaturacao(Collection<CasaInteligente> casas){
        this.volumeFaturacao = casas.stream()
                                    .filter(casa -> casa.getFornecedor().equals(this))
                                    .mapToDouble(casa -> casa.getTotalCost())
                                    .sum();
    }

    public void resetVolumeFaturacao(){
        this.volumeFaturacao = 0.0f;
    }

    @Override
    public int compareTo(EnergyProvider ep){
        return this.name.compareTo(ep.name);
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
