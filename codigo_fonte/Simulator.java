import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Simulator {
    /**
     * Casas presentes nesta simulação indexadas pelo nif do seu proprietario.
     */
    private Map<Integer, CasaInteligente> houses;

    /**
     * Fornecedores de energia presentes nesta simulação indexados pelo seu nome,
     */
    private Map<String,EnergyProvider> energyProviders;
    
    /**
     * Faturas emitidas por cada fornecedor indexadas segundo o nome do fornecedor.
     */
    private Map<String, List<Fatura>> billsPerProvider; // faturas de cada fornecedores
    
    /**
     * Volumes de faturação por fornecedor.
     */
    private Map<String, Double> profitPerProvider; // volumes de faturação por fornecedor ordenados por ordem decrescente
    
    /**
     * Lista de casas ordenada (de forma decrescente) segundo o consumo total das casas na execução da simulação.
     * Esta lista está vazia no momento da criação do simulador.
     */
    private List<CasaInteligente> consumptionOrder; // lista de casas ordenada pelo seu consumo (ordem decrescente)


    public Simulator(Collection<CasaInteligente> houses, Collection<EnergyProvider> providers) {
        this.houses = new HashMap<>();
        houses.forEach(house -> this.houses.put(house.getOwnerNif(), house.clone()));

        this.energyProviders = new HashMap<>();
        providers.forEach(provider -> this.energyProviders.put(provider.getName(),provider.clone()));

        this.billsPerProvider = new HashMap<>();
        this.profitPerProvider = new HashMap<>();
        for(CasaInteligente house : houses){
            this.billsPerProvider.putIfAbsent(house.getFornecedor(), new ArrayList<Fatura>());
            this.profitPerProvider.putIfAbsent(house.getFornecedor(),0.0);
        }

        this.consumptionOrder = null; // só lhe é atribuído o resultado após terminar a simulação
    }

    /**
     * Executa uma simulação básica.
     * Uma simulação básica consiste em efectuar a passagem do tempo sem alterar o estado de todas as entidades.            
     */
    public void startBasicSimulation(LocalDate start,LocalDate end){
        this.resetAll();
        for(LocalDate aux = start; aux.compareTo(end) < 0; aux = aux.plusDays(1)){
            this.houses.values().forEach(house -> house.passTime(this.energyProviders.get(house.getFornecedor())));
        }

        this.consumptionOrder = this.houses.values().stream().sorted((h1,h2) -> {
            double aux = h2.getTotalConsumption() - h1.getTotalConsumption();
            return aux < 0 ? -1 : aux == 0 ? 0 : 1;
        }).collect(Collectors.toList());

        for(CasaInteligente house : this.houses.values()){
            EnergyProvider ep = this.energyProviders.get(house.getFornecedor()); // fornecedor da casa.
            if(ep != null){
                this.profitPerProvider.merge(ep.getName(),house.getTotalCost(),Double::sum); // atualiza o volume de faturaçao do fornecedor desta casa
                this.billsPerProvider.get(ep.getName()).add(ep.emitirFatura(house,start,end)); // adiciona uma fatura no fornecedor 
            } 
        }
    }

    /**
     * Repoe o estado das casas e dos dispositivos no estado inicial, excetuando alterações de preços e de fornecedores.
     */
    public void resetAll(){
        this.houses.values().forEach(house -> house.resetConsumptionAndCost());
        this.billsPerProvider.values().forEach(list -> list.clear());
        this.profitPerProvider.keySet().forEach(key -> this.profitPerProvider.put(key,0.0));
        this.consumptionOrder = null;
    }

    /**
     * Adiciona uma casa a esta simulacao.
     */
    public void addHouse(CasaInteligente house){
        this.houses.put(house.getOwnerNif(),house.clone());
    }

    /**
     * Altera o preco aplicado por um fornecedor.
     */
    public void changeProviderPrice(EnergyProvider provider, double new_price){
        this.changeProviderPrice(provider.getName(),new_price);
    }

    /**
     * Altera o preco aplicado por um fornecedor dado o seu nome.
     */
    public void changeProviderPrice(String providerName, double new_price){
        this.energyProviders.get(providerName).setPrice_kwh(new_price);
    }

    /**
     * Altera o imposto aplicado por um fornecedor.
     */
    public void changeProviderTax(EnergyProvider provider, double new_tax){
        this.changeProviderTax(provider.getName(),new_tax);
    }

    /**
     * Adiciona um fornecedor a esta simulação
     */
    public void addProvider(EnergyProvider provider){
        this.energyProviders.putIfAbsent(provider.getName(),provider.clone());
    }

    /**
     * Altera o imposto aplicado por um fornecedor dado o seu nome.
     */
    public void changeProviderTax(String providerName, double new_tax){
        this.energyProviders.get(providerName).setTax(new_tax);
    }

    /**
     * Remove um dispositivo de uma casa da simulação.
     */
    public void removeDeviceFromHouse(int ownerNIF,String devID){
        if(this.houses.containsKey(ownerNIF)){
            this.houses.get(ownerNIF).removeDevice(devID);
        }
    }

    /**
     * Remove uma casa desta simulacao e retorna-a;
     */
    public CasaInteligente removeHouse(int houseID){
        return this.houses.remove(houseID);
    }

    /**
     * Devolve uma lista com as casas desta simulação
     */
    public List<CasaInteligente> getHouses(){
        List<CasaInteligente> result = new ArrayList<>();
        this.houses.values().forEach(house -> result.add(house.clone()));
        return result;
    }

    /**
     * Devolve uma lista com os fornecedores desta simulação
     */
    public List<EnergyProvider> getProviders(){
        List<EnergyProvider> result = new ArrayList<>();
        this.energyProviders.values().forEach(ep -> result.add(ep.clone()));
        return result;
    }

    /**
     * Retorna a casa que mais energia consumiu na simulacao.
     */
    public CasaInteligente getBiggestConsumer(){
        return this.consumptionOrder.get(0).clone();
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
        return this.getBillsFromProvider(provider.getName());
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
    public List<CasaInteligente> getConsumptionOrder(){
        List<CasaInteligente> result = new ArrayList<>();
        this.consumptionOrder.forEach(house -> result.add(house.clone()));
        return result;
    }

}
