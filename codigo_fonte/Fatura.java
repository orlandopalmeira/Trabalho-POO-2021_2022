import java.time.LocalDate;

public class Fatura {
    private static int last_id = 0;

    private String provider_name;
    private CasaInteligente casa;
    private LocalDate start;
    private LocalDate end;
    private double montante;
    private int id;

    public Fatura(String provider_name, CasaInteligente casa, LocalDate start, LocalDate end) {
        this.provider_name = provider_name;
        this.casa = casa.clone();
        this.id = ++Fatura.last_id;
        this.montante = this.casa.getTotalCost();
        this.start = start;
        this.end = end;
    }

    private Fatura(Fatura f){
        this.provider_name = f.provider_name;
        this.casa = f.casa.clone();
        this.montante = f.montante;
        this.id = f.id;
        this.start = f.start;
        this.end = f.end;
    }

    public double getMontante(){
        return this.montante;
    }

    public Pessoa getCliente(){
        return this.casa.getProprietario();
    }

    public CasaInteligente getCasa(){
        return this.casa.clone();
    }

    public String getProviderName(){
        return this.provider_name;
    }

    /**
     * Imprime a fatura.
     */
    public String printFatura(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("----------------------------<<Fatura>>---------------------------\n");
        sb.append(String.format("%s\n\n",this.provider_name));
        sb.append(String.format("Fatura: F%d\n",this.id));
        sb.append(String.format("Cliente: %s\nNIF: %d\n",this.casa.getOwnerName(),this.casa.getOwnerNif()));
        sb.append(String.format("Morada: %s\n",this.casa.getMorada()));
        sb.append("Período de faturação: ");
        sb.append(this.start.toString()); sb.append(" - "); sb.append(this.end.toString()); sb.append("\n");
        sb.append(String.format("Montante: %f€\n", this.casa.getTotalCost()));
        sb.append("-----------------------Registo de Consumos-----------------------\n");
        sb.append("Tipo de Dispositivo | ID | Consumo\n");
        for(SmartDevice dev: this.casa.getDevices()){
            sb.append(String.format("%s | %s | %f\n",dev.getClass().getName(),dev.getID(),dev.getTotalConsumption()));
        }
        sb.append("-----------------------------------------------------------------\n");

        return sb.toString();
    }

    @Override
    /**
     * Copia uma fatura.
     */
    public Fatura clone(){
        return new Fatura(this);
    }

    @Override
    /**
     * Devolve uma string com informação relevante sobre esta fatura.
     */
    public String toString(){
        return String.format("{Fatura: F%d, Provider: %s, Cliente: %s, NIF: %s, Morada: %s, Montante: %f€}",
                             this.id,this.provider_name,this.casa.getOwnerName(),this.casa.getOwnerNif(),this.casa.getMorada(),this.montante);
    }

}
