import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Simulator {
    private Map<Integer, CasaInteligente> houses;
    private Map<String,EnergyProvider> energyProviders;
    
    private Map<String, List<Fatura>> billsPerProvider; // faturas de cada fornecedores
    private Map<String, Double> profitPerProvider; // volumes de faturação por fornecedor ordenados por ordem decrescente
    private TreeSet<CasaInteligente> consumptionOrder; // árvore de casas ordenada segundo o seu consumo total no fim da simulação

    private int lastHouseID; // último id atribuído a uma casa.

    public Simulator(Collection<CasaInteligente> houses, Collection<EnergyProvider> providers) {
        this.houses = new HashMap<>();
        Iterator<CasaInteligente> ith = houses.iterator();
        for (int i = 1; ith.hasNext(); i++) {
            this.houses.put(i,ith.next().clone());
            this.lastHouseID = i;
        }

        this.energyProviders = new HashMap<>();
        providers.forEach(provider -> this.energyProviders.put(provider.getName(),provider.clone()));

        this.billsPerProvider = new HashMap<>();
        this.profitPerProvider = new HashMap<>();
        for(CasaInteligente house : houses){
            this.billsPerProvider.putIfAbsent(house.getFornecedor().getName(), new ArrayList<Fatura>());
            this.profitPerProvider.put(house.getFornecedor().getName(),0.0);
        }

        this.consumptionOrder = new TreeSet<>((c1,c2) -> {
            double aux = c2.getTotalConsumption() - c1.getTotalConsumption();
            return aux < 0 ? -1 : aux == 0 ? 0 : 1;
        });
    }

    /**
     * Inicia a simulacao.
     */
    public void startSimulation(LocalDate start,LocalDate end){
        this.resetAll();
        for(LocalDate aux = start; aux.compareTo(end) < 0; aux = aux.plusDays(1)){
            this.houses.values().forEach(house -> house.passTime());
        }
        for(CasaInteligente house : this.houses.values()){
            this.consumptionOrder.add(house); // árvore ordenada pelo consumo
            EnergyProvider ep = house.getFornecedor(); // fornecedor da casa.
            this.profitPerProvider.replace(ep.getName(), this.profitPerProvider.get(ep.getName()) + house.getTotalConsumption()); // atualiza o volume de faturaçao do fornecedor desta casa
            this.billsPerProvider.get(ep.getName()).add(ep.emitirFatura(house,start,end)); // adiciona uma fatura no fornecedor 
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
     * Adiciona uma casa e esta simulacao.
     */
    public void addHouse(CasaInteligente house){
        this.houses.put(++this.lastHouseID,house.clone());
    }

    /**
     * Retorna a casa que mais energia consumiu na simulacao.
     */
    public CasaInteligente getBiggestConsumer(){
        CasaInteligente aux = this.consumptionOrder.first(); // a árvore está por ordem decrescente
        return aux.clone(); 
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

    /**
     * Devolve as faturas emitidas por um certo fornecedor.
     */
    public List<Fatura> getBillsFromProvider(EnergyProvider provider){
        List<Fatura> result = new ArrayList<>();
        this.billsPerProvider.get(provider.getName()).forEach(fatura -> result.add(fatura.clone()));
        return result;
    }

    /**
     * Devolve as faturas emitidas por um certo fornecedor dado o seu nome.
     */
    public List<Fatura> getBillsFromProvider(String providerName){
        List<Fatura> result = new ArrayList<>();
        this.billsPerProvider.get(providerName).forEach(fatura -> result.add(fatura.clone()));
        return result;
    }

    /**
     * Devolve uma ordenação dos consumidores pelo consumo total.
     */
    public Set<CasaInteligente> getConsumptionOrder(){
        TreeSet<CasaInteligente> result = new TreeSet<>(this.consumptionOrder.comparator());
        this.consumptionOrder.forEach(house -> result.add(house.clone()));
        return result;
    }

}
