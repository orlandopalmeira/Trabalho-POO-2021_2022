import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;

public class Simulator implements Serializable{
    /**
     * Armazena o último id atribuído a uma casa inserida na simulação.
     */
    private int last_house_id;

    /**
     * Casas presentes nesta simulação indexadas por um inteiro atribuído autmaticamente.
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

    // CONSTRUTORES

    public Simulator(Collection<CasaInteligente> houses, Collection<EnergyProvider> providers) {
        this.last_house_id = 0;
        this.houses = new HashMap<>();
        houses.forEach(house -> this.addHouse(house));

        this.energyProviders = new HashMap<>();
        providers.forEach(provider -> this.addProvider(provider));

        this.billsPerProvider = new HashMap<>();
        this.profitPerProvider = new HashMap<>();
        for(EnergyProvider ep : providers){
            this.billsPerProvider.putIfAbsent(ep.getName().toLowerCase(), new ArrayList<Fatura>());
            this.profitPerProvider.putIfAbsent(ep.getName().toLowerCase(),0.0);
        }

        this.consumptionOrder = null; // só lhe é atribuído o resultado após terminar a simulação
    }

    public Simulator(){
        this.houses = new HashMap<>();
        this.energyProviders = new HashMap<>();
        this.billsPerProvider = new HashMap<>();
        this.profitPerProvider = new HashMap<>();
        this.consumptionOrder = null; // só lhe é atribuído o resultado após terminar a simulação
    }

    /**
     * Método para inserir todos os elementos da simulação.
     */
    public void setSimulator(Collection<CasaInteligente> houses, Collection<EnergyProvider> providers){
        houses.forEach(house -> this.addHouse(house));
        providers.forEach(provider -> this.addProvider(provider));
        for(EnergyProvider ep : providers){
            this.billsPerProvider.putIfAbsent(ep.getName().toLowerCase(), new ArrayList<Fatura>());
            this.profitPerProvider.putIfAbsent(ep.getName().toLowerCase(),0.0);
        }
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
        LocalDateTime date;
        try{
            date = LocalDateTime.parse(date_time[0] + "T" + date_time[1]);
        } catch(DateTimeParseException e){return 2;}
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
                try{
                    this.houses.get(Integer.parseInt(id)).setAllOn(true, date);
                }
                catch(NumberFormatException e){return 2;}
                catch(NullPointerException e){return 2;}
                return 0;
            }
            case "desligaTodos":{
                try{
                    this.houses.get(Integer.parseInt(id)).setAllOn(false, date);
                }
                catch(NumberFormatException e){return 2;}
                catch(NullPointerException e){return 2;}
                return 0;
            }
            case "setOn":{
                try{
                    this.houses.get(Integer.parseInt(id)).setDeviceOn(command[3], true, date);
                }
                catch(NumberFormatException e){return 2;}
                catch(NullPointerException e){return 2;}
                return 0;
            }
            case "setOff":{
                try{
                    this.houses.get(Integer.parseInt(id)).setDeviceOn(command[3], false, date);
                }
                catch(NumberFormatException e){return 2;}
                catch(NullPointerException e){return 2;}
                return 0;
            }
            case "ligaTudoNaDivisao":{
                try{
                    this.houses.get(Integer.parseInt(id)).setAllinDivisionOn(command[3], true, date);
                }
                catch(NumberFormatException e){return 2;}
                catch(NullPointerException e){return 2;}
                return 0;
            }
            case "desligaTudoNaDivisao":{
                try{
                    this.houses.get(Integer.parseInt(id)).setAllinDivisionOn(command[3], false, date);
                }
                catch(NumberFormatException e){return 2;}
                catch(NullPointerException e){return 2;}
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
                    try{
                        this.energyProviders.get(id.toLowerCase()).setPrice_kwh(Double.parseDouble(command[3]));
                    } 
                    catch(NumberFormatException e){result = 1;}
                    catch(NullPointerException e){result = 1;}
                    break;
                }
                case "alteraImposto":{ 
                    try{
                        this.energyProviders.get(id.toLowerCase()).setTax(Double.parseDouble(command[3]));
                    }
                    catch(NumberFormatException e){result = 1;}
                    catch(NullPointerException e){result = 1;}
                    break;
                }
                case "alteraFornecedor": {
                    try{
                        this.houses.get(Integer.parseInt(id)).setFornecedor(command[3]);
                    }
                    catch(NumberFormatException e){result = 1;}
                    catch(NullPointerException e){result = 1;}
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
                case 1: {
                    end_commands.add(cmd); // os comandos que entram aqui são aqueles que só se executam quando a simulação fechar
                    break;
                }

                case 2:{
                    System.out.println("Erro num comando");
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
     * Apaga todos os dados desta simulação.
     */
    public void clearSimulation(){
        this.houses = new HashMap<>();
        this.energyProviders = new HashMap<>();
        this.billsPerProvider = new HashMap<>();
        this.profitPerProvider = new HashMap<>();
        this.consumptionOrder = null;
    }

    /**
     * Adiciona uma casa a esta simulacao.
     */
    public void addHouse(CasaInteligente house){
        this.houses.put(++this.last_house_id,house.clone());
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
    public void removeDeviceFromHouse(int houseID,String devID){
        if(this.houses.containsKey(houseID)){
            this.houses.get(houseID).removeDevice(devID);
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
        return this.houses.values().stream().map(CasaInteligente::clone).collect(Collectors.toList());
    }

    /**
     * Devolve o mapa que contém as casas
     */
    public Map<Integer, CasaInteligente> getHousesMap(){
        Map<Integer, CasaInteligente> result = new HashMap<>();
        this.houses.keySet().forEach(id -> result.put(id,this.houses.get(id).clone()));
        return result;
    }

    /**
     * Retorna uma casa da simulação.
     */
    public CasaInteligente getHouse(Integer houseID){
        return this.houses.get(houseID).clone();
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
    public void changeHouseProvider(String providername, Integer houseID){
        CasaInteligente house = this.houses.get(houseID);
        if(this.energyProviders.containsKey(providername.toLowerCase())){
            house.setFornecedor(providername);
        }
    } 
    
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
        return this.billsPerProvider.get(providerName.toLowerCase())
                                    .stream()
                                    .map(Fatura::clone)
                                    .collect(Collectors.toList());
    }

    /**
     * Devolve uma ordenação dos consumidores pelo consumo total.
     */
    public List<CasaInteligente> getConsumptionOrder(){
        return this.consumptionOrder.stream()
                                    .map(CasaInteligente::clone)
                                    .collect(Collectors.toList());
    }

    // MÉTODOS AUXILIARES

    /**
     * Verifica a existência de um dispositivo em todas as casas da simulação.
     */
    public boolean existsDevice(String devID){
        boolean result = false;
        for(CasaInteligente house: this.houses.values()){
            if(house.existsDevice(devID)){
                result = true; break;
            }
        }
        return result;
    }

    /**
     * Verifica a existência de um fornecedor na simulação dado o seu nome.
     */
    public boolean existsProvider(String providerName){
        return this.energyProviders.containsKey(providerName.toLowerCase());
    }

    /**
     * Verifica a existência de um fornecedor na simulação.
     */
    public boolean existsProvider(EnergyProvider provider){
        return this.existsProvider(provider.getName());
    }

    /**
     * Adiciona um dispositivo a uma casa da simulação
     */
    public void addDevice(SmartDevice device, int id_house, String room){
        this.houses.get(id_house).addDevice(device, room);
    }

    /**
     * Remove um dispositivo de uma casa da simulação
     */
    public void removeDevice(String devID, int id_house){
        this.houses.get(id_house).removeDevice(devID);
    }

    /**
     * Verifica as condições para se saber se a simulação pode arrancar.
     */
    public boolean isReady(){
        return !(this.houses.isEmpty()) && !(this.energyProviders.isEmpty());
    }

    // GUARDAR O ESTADO DESTA SIMULAÇÃO
    /**
     * Guarda esta simulação num ficheiro binário.
     */
    public void saveState(String path) throws FileNotFoundException,IOException{
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

}
