import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class Simulator {
    private Map<Integer, CasaInteligente> houses;
    private Map<String,EnergyProvider> energyProviders;
    private Map<String, List<Fatura>> billsPerProvider; // faturas de cada fornecedores
    private Map<String, Double> profitPerProvider; // volumes de faturação por fornecedor
    private TreeSet<CasaInteligente> consumptionOrder; // árvore de casas ordenada segundo o seu consumo total no fim da simulação
    private LocalDate start; // data de início da simulação
    private LocalDate end; // data de fim da simulação

    public Simulator(Collection<CasaInteligente> houses, Collection<EnergyProvider> providers, LocalDate start, LocalDate end) {
        this.houses = new HashMap<>();
        Iterator<CasaInteligente> ith = houses.iterator();
        for (int i = 0; ith.hasNext(); i++) {
            this.houses.put(i,ith.next().clone());
        }

        this.energyProviders = new HashMap<>();
        providers.forEach(provider -> this.energyProviders.put(provider.getName(),provider.clone()));

        this.billsPerProvider = new HashMap<>();
        this.profitPerProvider = new HashMap<>();
        for(CasaInteligente house : houses){
            this.billsPerProvider.put(house.getFornecedor().getName(), new ArrayList<Fatura>());
            this.profitPerProvider.put(house.getFornecedor().getName(),0.0);
        }

        this.consumptionOrder = new TreeSet<>((c1,c2) -> {
            double aux = c2.getTotalConsumption() - c1.getTotalConsumption();
            return aux < 0 ? -1 : aux == 0 ? 0 : 1;
        });

        this.start = start; // LocalDate é imutável/sem necessidade de clone
        this.end = end;
    }

    /**
     * Inicia a simulacao.
     */
    public void startSimulation(){
        LocalDate aux = start;
        while(aux.compareTo(end) < 0){
            this.houses.values().forEach(house -> house.passTime());
            aux = aux.plusDays(1);
        }
    }

    /**
     * Repoe a simulacao no estado inicial.
     */
    public void resetAll(){
        this.houses.values().forEach(house -> house.resetConsumptionAndCost());
        this.billsPerProvider.values().forEach(list -> list.clear());
        this.profitPerProvider.keySet().forEach(key -> this.profitPerProvider.put(key,0.0));
        this.consumptionOrder.clear();
    }

    /**
     * Altera a data de inicio da simulacao.
     */
    public void setStartDate(LocalDate start){
        this.start = start;
    }

    /**
     * Altera a data de fim da simulacao.
     */
    public void setEndDate(LocalDate end){
        this.end = end;
    }

    /**
     * Devolve a data de inicio da simulacao.
     */
    public LocalDate getStartDate(){
        return this.start;
    }

    /**
     * Devolve a data de fim da simulacao.
     */
    public LocalDate getEndDate(){
        return this.end;
    }

    /**
     * Devolve as faturas emitidas por um certo fornecedor.
     */
    public List<Fatura> getBillsFromProvider(EnergyProvider provider){
        List<Fatura> result = new ArrayList<>();
        this.billsPerProvider.get(provider.getName()).forEach(fatura -> result.add(fatura.clone()));
        return result;
    }

    /**
     * Devolve as faturas emitidas por um certo fornecedor.
     */
    public List<Fatura> getBillsFromProvider(String providerName){
        List<Fatura> result = new ArrayList<>();
        this.billsPerProvider.get(providerName).forEach(fatura -> result.add(fatura.clone()));
        return result;
    }

    /**
     * Retorna a casa que mais energia consumiu na simulacao.
     */
    public CasaInteligente getBiggestConsumer(){
        CasaInteligente aux = this.consumptionOrder.first(); // a árvore está por ordem decrescente
        return aux != null ? aux.clone() : null;
    }

    /**
     * Retorna o fornecedor com maior volume de faturacao.
     */
    public EnergyProvider getBiggestProvider(){
        String providerName;
        providerName = this.profitPerProvider.keySet().stream()
                                             .max((p1,p2) -> {
                                                 double aux = this.profitPerProvider.get(p1) - this.profitPerProvider.get(p2);
                                                 return aux < 0 ? -1 : aux == 0 ? 0 : 1;
                                             }).orElse(null);
        return providerName != null ? this.energyProviders.get(providerName).clone() : null;
    }

}
