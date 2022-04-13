import java.time.LocalDate;
import java.util.Collection;
public class EnergyProvider implements Comparable<EnergyProvider>{
    private String name;
    private float price_kwh;
    private float tax;
    

    public EnergyProvider(){
        this.name = "";
        this.price_kwh = 0.0f;
        this.tax = 0.0f;
    }

    public EnergyProvider(String name){
        this.name = name;
        this.price_kwh = 0.15f;
        this.tax = 0.23f;
    }

    public EnergyProvider(String name, float price_kwh, float tax){
        this.name = name;
        this.price_kwh = price_kwh;
        this.tax = tax;
    }

    public EnergyProvider(EnergyProvider ep){
        this.name = ep.name;
        this.price_kwh = ep.price_kwh;
        this.tax = ep.tax;
    }

    /**
     * Devolve o nome deste fornecedor.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Altera o nome deste fornecedor.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devolve o preco por kWh deste fornecedor.
     */
    public float getPrice_kwh() {
        return this.price_kwh;
    }

    /**
     * Altera o preco por kWh deste fornecedor.
     */
    public void setPrice_kwh(float price_kwh) {
        this.price_kwh = price_kwh;
    }

    /**
     * Devolve o imposto aplicado por este fornecedor.
     */
    public float getTax() {
        return this.tax;
    }

    /**
     * Altera o imposto aplicado por este fornecedor.
     */
    public void setTax(float tax) {
        this.tax = tax;
    }

    /**
     * Calcula o custo de um dia de utilizacao de um certo dispositivo.
     */
    private double pricePerDayPerDevice(int numDevices, SmartDevice device){
        return this.price_kwh * device.dailyConsumption() * (1 + this.tax) * (numDevices > 10 ? 0.9f : 0.75f);
    }

    /**
     * Calcula o custo total de um dia de utilizacao de um conjunto de dispositivos.
     */
    public double pricePerDay(Collection<SmartDevice> devices){
        return devices.stream()
                      .mapToDouble(dev -> this.pricePerDayPerDevice(devices.size(), dev))
                      .sum();
    }

    /**
     * Emite a fatura para uma certa casa.
     */
    public Fatura emitirFatura(CasaInteligente casa, LocalDate start, LocalDate end){
        EnergyProvider ep = casa.getFornecedor();
        if(this.equals(ep)){
            return new Fatura(ep.name,casa,start,end);
        }else return null;
    }

    public String toLineFile(){
        return String.format("%s;%f;%f\n",this.name,this.price_kwh,this.tax);
    }

    @Override
    /**
     * Compara dois fornecedores.
     */
    public int compareTo(EnergyProvider ep){
        return this.name.compareTo(ep.name);
    }

    @Override
    /**
     * Verifica a igualdade entre dois fornecedores.
     */
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
    /**
     * Devolve uma string com informação relevante sobre este fornecedor.
     */
    public String toString() {
        return String.format("{EnergyProvider, Name = %d, price_kwh = %f, tax = %f}",
                             this.name,this.price_kwh,this.tax);
    }

    @Override
    /**
     * Copia este fornecedor.
     */
    public EnergyProvider clone(){
        return new EnergyProvider(this);
    }

}
