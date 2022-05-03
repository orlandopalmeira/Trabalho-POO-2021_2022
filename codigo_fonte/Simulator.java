import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;

public class Simulator implements Serializable{
    /**
     * Casas presentes nesta simulação indexadas pelo nif do seu proprietário.
     */
    private Map<Integer, CasaInteligente> houses;

    /**
     * Fornecedores de energia presentes nesta simulação indexados pelo seu nome,
     */
    private Map<String,EnergyProvider> energyProviders;
    
    /**
     * Faturas emitidas por cada fornecedor indexadas segundo o nome do fornecedor.
     */
    private Map<String, List<Fatura>> billsPerProvider; 
    
    /**
     * Volumes de faturação por fornecedor.
     */
    private Map<String, Double> profitPerProvider; 
    
    /**
     * Lista de casas ordenada (de forma decrescente) segundo o consumo total das casas na execução da simulação.
     * Esta lista está vazia no momento da criação do simulador.
     */
    private List<CasaInteligente> consumptionOrder;


    public Simulator(Collection<CasaInteligente> houses, Collection<EnergyProvider> providers) {
        this.houses = new HashMap<>();
        houses.forEach(house -> this.houses.put(house.getOwnerNif(), house.clone()));

        this.energyProviders = new HashMap<>();
        providers.forEach(provider -> this.energyProviders.put(provider.getName().toLowerCase(),provider.clone()));

        this.billsPerProvider = new HashMap<>();
        this.profitPerProvider = new HashMap<>();
        for(CasaInteligente house : houses){
            this.billsPerProvider.putIfAbsent(house.getFornecedor().toLowerCase(), new ArrayList<Fatura>());
            this.profitPerProvider.putIfAbsent(house.getFornecedor().toLowerCase(),0.0);
        }

        this.consumptionOrder = null; // só lhe é atribuído o resultado após terminar a simulação
    }

    // CORRER A SIMULAÇÃO

    /**
     * Executa um comando nesta simulação.
     * @return <ul>
     *         <li> 0 se o comando for executado  </li>
     *         <li> 1 se o comando apenas pode ser executado no fim da simulação  </li>
     *         <li> 2 se houver um erro </li>
     *         </ul>
     */
    private int exec_command(String[] command){
        String[] date_time = command[0].split(" ");
        LocalDateTime date = LocalDateTime.parse(date_time[0] + "T" + date_time[1]);
        if(command[1].equals("ligaTudoEmTodasAsCasas")){
            this.houses.values().forEach(house -> house.setAllOn(true, date));
            return 0;
        }
        String id = command[1];
        switch (command[2]){
            case "alteraPreco": return 1;
            case "alteraImposto": return 1;
            case "alteraFornecedor": return 1;
            case "ligaTodos":{
                this.houses.get(Integer.parseInt(id)).setAllOn(true, date);
                return 0;
            }
            case "desligaTodos":{
                this.houses.get(Integer.parseInt(id)).setAllOn(false, date);
                return 0;
            }
            case "setOn":{
                this.houses.get(Integer.parseInt(id)).setDeviceOn(command[3], true, date);
                return 0;
            }
            case "setOff":{
                this.houses.get(Integer.parseInt(id)).setDeviceOn(command[3], false, date);
                return 0;
            }
            case "ligaTudoEm":{
                this.houses.get(Integer.parseInt(id)).setAllinDivisionOn(command[3], true, date);
                return 0;
            }
            case "desligaTudoEm":{
                this.houses.get(Integer.parseInt(id)).setAllinDivisionOn(command[3], false, date);
                return 0;
            }
        
            default: return 2;
        }
    }

    /**
     * Executa os comandos que apenas podem ser executados no fim da simulação.
     * @return <ul>
     *          <li> 0 se todos os comandos foram executados </li>
     *          <li> 1 se algum comando não foi executado devido à ocorrência de erros </li>
     *         </ul>
     */
    private int exec_end_commands(List<String[]> commands){
        int result = 0;
        for(String[] command : commands){
            String id = command[1];
            switch(command[2]){
                case "alteraPreco":{ 
                    this.energyProviders.get(id.toLowerCase()).setPrice_kwh(Double.parseDouble(command[3]));
                    break;
                }
                case "alteraImposto":{ 
                    this.energyProviders.get(id.toLowerCase()).setTax(Double.parseDouble(command[3]));
                    break;
                }
                case "alteraFornecedor": {
                    this.houses.get(Integer.parseInt(id)).setFornecedor(command[3]);
                    break;
                }
                default: {result = 1; break;}
            }
        }
        return result;
    }


    public void startSimulation(LocalDateTime start, LocalDateTime end, String[] commands){
        this.resetAll();
        // transformação do formato dos comandos num formato conveniente
        String[][] commands_ = new String[commands.length][];
        List<String[]> end_commands = new ArrayList<>();
        for(int i = 0; i < commands.length; i++){
            commands_[i] = commands[i].split(",");
        }
        // execução da simulacao
        this.houses.values().forEach(house -> house.setLastChangeDateAllDevices(start));
        for(String[] cmd: commands_){
            switch (exec_command(cmd)) {
                case 0: break;

                case 1: {
                    end_commands.add(cmd);
                    break;
                }
            
                default: break;
            }
        }
        this.houses.values().forEach(house -> house.updateConsumptionAllDevices(end));
        // gerar resultados
        this.consumptionOrder = this.houses.values().stream().sorted((h1,h2) -> {
            double dif = h2.getTotalConsumption() - h1.getTotalConsumption();
            return dif < 0 ? -1 : dif == 0 ? 0 : 1;
        }).collect(Collectors.toList());

        for(CasaInteligente house: this.houses.values()) {
            String provider_name = house.getFornecedor().toLowerCase();
            this.billsPerProvider.get(provider_name).add(this.energyProviders.get(provider_name).emitirFatura(house,start,end));
            this.profitPerProvider.merge(provider_name, house.getTotalCost(this.energyProviders.get(provider_name),end), Double::sum);
        }
        exec_end_commands(end_commands);
    }

    // MANIPULAR AS ENTIDADES DA SIMULAÇÃO

    /**
     * Repoe o estado das casas e dos dispositivos no estado inicial, excetuando alterações de preços e de fornecedores.
     */
    public void resetAll(){
        this.houses.values().forEach(house -> house.resetConsumption());
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
     * @return true se a operação foi bem sucedida e false se o fornecedor dado não existe.
     */

    public boolean changeProviderPrice(EnergyProvider provider, double new_price){
        return this.changeProviderPrice(provider.getName(),new_price);
    }

    /**
     * Altera o preco aplicado por um fornecedor.
     * @return true se a operação foi bem sucedida e false se o fornecedor dado não existe.
     */

    public boolean changeProviderPrice(String providerName, double new_price){
        if(this.energyProviders.containsKey(providerName.toLowerCase())){
            this.energyProviders.get(providerName.toLowerCase()).setPrice_kwh(new_price);
            return true;
        }else return false;
    }

    /**
     * Altera o imposto aplicado por um fornecedor.
     */
    public boolean changeProviderTax(EnergyProvider provider, double new_tax){
        return this.changeProviderTax(provider.getName(),new_tax);
    }

    /**
     * Altera o imposto aplicado por um fornecedor dado o seu nome.
     */
    public boolean changeProviderTax(String providerName, double new_tax){
        if (this.energyProviders.containsKey(providerName.toLowerCase())) {
            this.energyProviders.get(providerName.toLowerCase()).setTax(new_tax);
            return true;
        } else return false;
    }

    /**
     * Adiciona um fornecedor a esta simulação
     */
    public void addProvider(EnergyProvider provider){
        this.energyProviders.putIfAbsent(provider.getName().toLowerCase(),provider.clone());
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
     * Devolve o mapa que contém as casas
     */
    public Map<Integer, CasaInteligente> getHousesMap(){
        Map<Integer, CasaInteligente> result = new HashMap<>();
        this.houses.keySet().forEach(nif -> result.put(nif,this.houses.get(nif).clone()));
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
     * Devolve o mapa que contém os fornecedores
     */
    public Map<String,EnergyProvider> getProvidersMap(){
        Map<String,EnergyProvider> result = new HashMap<>();
        this.energyProviders.keySet().forEach(name -> result.put(name,this.energyProviders.get(name).clone()));
        return result;
    }

    /**
     * Altera o estado(ligado/desligado) de todos os dispositivos de todas as casas.
     */
    public void setStateAllDevicesInAllHouses(boolean state, LocalDateTime change_date){
        this.houses.values().forEach(house -> house.setAllOn(state,change_date));
    }

    /** 
     * Altera o estado(ligado/desligado) de todos os dispositivos de uma certa casa.
     * @return true se a operação foi bem sucedida e false se a casa não existe.
    */
    public boolean setStateAllDevicesHouse(int houseID, boolean state, LocalDateTime change_date){
        if(this.houses.containsKey(houseID)){
            this.houses.get(houseID).setAllOn(state,change_date);
            return true;
        }else return false;
    }

    /** 
     * Altera o estado(ligado/desligado) de um dispositivo de uma certa casa.
     * @return 0 se a operação foi bem sucedida; 1 se a casa não existe; 2 se o dispositivo não existe na casa
    */
    public int setStateInDeviceInHouse(String devID, int houseID, boolean state, LocalDateTime change_date){
        if(this.houses.containsKey(houseID)){
            CasaInteligente house = this.houses.get(houseID);
            if(house.existsDevice(devID)){
                house.setDeviceOn(devID, state, change_date);
                return 0;
            } else return 2;
        } else return 1;
    }

    /**
     * Altera o estado(ligado/desligado) de todos os dispositivos de uma reparticao de uma certa casa.
     * @return 0 se a operação foi bem sucedida; 1 se a casa não existe; 2 se a reparticao não existe na casa.
     */
    public int setStateAllDevicesInRoom(int houseID, String room, boolean state, LocalDateTime change_date){
        if(this.houses.containsKey(houseID)){
            CasaInteligente house = this.houses.get(houseID);
            if(house.hasRoom(room)){
                house.setAllinDivisionOn(room, state, change_date);
                return 0;
            }else return 2;
        }else return 1;
    }

    /**
     * Altera o fornecedor de uma casa.
     */
    
    

    // ESTATÍSTICAS DA SIMULAÇÃO

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
                                                 double aux = this.profitPerProvider.get(p1.toLowerCase()) - this.profitPerProvider.get(p2.toLowerCase());
                                                 return aux < 0 ? -1 : aux == 0 ? 0 : 1;
                                             }).orElse(null);
        return providerName != null ? this.energyProviders.get(providerName.toLowerCase()).clone() : null;
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
        this.billsPerProvider.get(providerName.toLowerCase()).forEach(fatura -> result.add(fatura.clone()));
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

    // GUARDAR O ESTADO DESTA SIMULAÇÃO
    public void saveState(String path) throws FileNotFoundException,IOException{
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

}
