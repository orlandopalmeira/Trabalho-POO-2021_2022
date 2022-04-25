import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private SmartBulb bulbFromInput(Set<String> ids, Scanner sc){
        // Variáveis auxiliares
        String isOn,idBulb,tone; boolean state, flag = true;
        double dimension = 1.0;
        SmartBulb sb = null;

        // Criação da lâmpada
        System.out.print("Insira o id desta lâmpada: ");
        idBulb = sc.nextLine();
        while(ids.contains(idBulb)){
            System.out.println("O id que pretende atribuir já existe!");
            System.out.print("Insira o id desta lâmpada: ");
            idBulb = sc.nextLine();
        }
        ids.add(idBulb);

        System.out.print("O dispositivo inicia-se ligado?(s/n): ");
        isOn = sc.nextLine();
        while (isOn.length() != 1 && !("sSnN".contains(isOn))) {
            System.out.println("Opção inválida, escreva 's' ou 'S' para sim ou 'n' ou 'N' para não (sem aspas)!");
            System.out.print("O dispositivo inicia-se ligado?(s/n): ");
            isOn = sc.nextLine();
        }
        if(isOn.toLowerCase().equals("s")) state = true;
        else state = false;

        System.out.print("Insira a tonalidade da lâmpada (WARM/COLD/NEUTRAL): ");
        tone = sc.nextLine().toLowerCase();
        while(!(tone.equals("warm") || tone.equals("cold") || tone.equals("neutral"))){
            System.out.println("Opção inválida, escreva 'warm','cold' ou 'neutral'");
            System.out.print("Insira a tonalidade da lâmpada (WARM/COLD/NEUTRAL): ");
            tone = sc.nextLine().toLowerCase();
        }

        System.out.print("Insira a dimensão desta lâmpada: ");
        while(flag){
            try{
                dimension = Double.parseDouble(sc.nextLine());
                flag = false;
            } catch (NumberFormatException e){
                System.out.println("A dimensão deve ser um número real!");
                System.out.print("Insira a dimensão desta lâmpada: ");
            }
        }
        switch (tone) {
            case "warm":sb = new SmartBulb(idBulb,state,2,dimension); break;
            case "cold":sb = new SmartBulb(idBulb,state,0,dimension);break;
            case "neutral":sb = new SmartBulb(idBulb,state,1,dimension);break;
        }
        return sb;
    }

    private SmartSpeaker speakerFromInput(Set<String> ids, Scanner sc){
        // Variáveis auxiliares
        String isOn,idSpeaker,channel,marca; boolean state, flag = true;
        int volume = 0;
        SmartSpeaker ss = null;

        // Criação do speaker
        System.out.print("Insira o id deste speaker : ");
        idSpeaker = sc.nextLine();
        while(ids.contains(idSpeaker)){
            System.out.println("O id que pretende atribuir já existe!");
            System.out.print("Insira o id deste speaker: ");
            idSpeaker = sc.nextLine();
        }
        ids.add(idSpeaker);

        System.out.print("O dispositivo inicia-se ligado?(s/n): ");
        isOn = sc.nextLine();
        while (isOn.length() != 1 && !("sSnN".contains(isOn))) {
            System.out.println("Opção inválida, escreva 's' ou 'S' para sim ou 'n' ou 'N' para não (sem aspas)!");
            System.out.print("O dispositivo inicia-se ligado?(s/n): ");
            isOn = sc.nextLine();
        }
        if(isOn.toLowerCase().equals("s")) state = true;
        else state = false;

        System.out.print("Insira o volume com que o speaker inicia: ");
        while (flag) {
            try {
                volume = Integer.parseInt(sc.nextLine());
                flag = false;
            } catch (NumberFormatException e){
                System.out.println("O volume deve ser um número inteiro!");
                System.out.print("Insira o volume com que o speaker inicia: ");
            }
        }

        System.out.print("Insira o canal com que este speaker inicia: ");
        channel = sc.nextLine();

        System.out.print("Insira a marca deste speaker: ");
        marca = sc.nextLine();

        ss = new SmartSpeaker(idSpeaker,state,volume,channel,marca);

        return ss;
    }

    private SmartCamera cameraFromInput(Set<String> ids, Scanner sc){
        // Variáveis auxiliares
        String isOn,idCam; boolean state, flag = true;
        int resX = 0, resY = 0; double sizeOfFile = 0;
        SmartCamera sCam = null;

        // Criação da câmara
        System.out.print("Insira o id desta câmara: ");
        idCam = sc.nextLine();
        while(ids.contains(idCam)){
            System.out.println("O id que pretende atribuir já existe!");
            System.out.print("Insira o id deste speaker: ");
            idCam = sc.nextLine();
        }
        ids.add(idCam);

        System.out.print("O dispositivo inicia-se ligado?(s/n): ");
        isOn = sc.nextLine();
        while (isOn.length() != 1 && !("sSnN".contains(isOn))) {
            System.out.println("Opção inválida, escreva 's' ou 'S' para sim ou 'n' ou 'N' para não (sem aspas)!");
            System.out.print("O dispositivo inicia-se ligado?(s/n): ");
            isOn = sc.nextLine();
        }
        if(isOn.toLowerCase().equals("s")) state = true;
        else state = false;

        System.out.print("Insira a resolução horizontal(X): ");
        while (flag) {
            try{
                resX = Integer.parseInt(sc.nextLine());
                flag = false;
            } catch (NumberFormatException e){
                System.out.println("A resolução horizontal deve ser um número inteiro!");
                System.out.print("Insira a resolução horizontal(X): ");
            }
        }
        flag = true;

        System.out.print("Insira a resolução vertical(Y): ");
        while (flag) {
            try{
                resY = Integer.parseInt(sc.nextLine());
                flag = false;
            } catch (NumberFormatException e){
                System.out.println("A resolução vertical deve ser um número inteiro!");
                System.out.print("Insira a resolução vertical(Y): ");
            }
        }
        flag = true;

        System.out.print("Insira o tamanho do ficheiro gerado por esta câmara: ");
        while (flag) {
            try{
                sizeOfFile = Double.parseDouble(sc.nextLine());
                flag = false;
            } catch (NumberFormatException e){
                System.out.println("O tamanho do ficheiro deve ser um número real!");
                System.out.print("Insira o tamanho do ficheiro gerado por esta câmara: ");
            }
        }
        flag = true;

        sCam = new SmartCamera(idCam,state,resX,resY,sizeOfFile);
        return sCam;
    }

    private static Pessoa personFromInput(Set<Integer> nifs, Scanner sc){
        String name; 
        int nif = 0;
        boolean flag = true;

        System.out.print("Insira o nome desta pessoa: ");
        name = sc.nextLine();

        System.out.print("Insira o nif desta pessoa: ");
        while(flag){
            try {
                nif = Integer.parseInt(sc.nextLine());
                if(nifs.contains(nif)){
                    System.out.println("O nif que pretende atribuir já existe!\n");
                    System.out.print("Insira o nif desta pessoa: ");
                }else{
                    flag = false;
                    nifs.add(nif);
                }
            } catch (Exception e) {
                System.out.println("O nif deve ser um número inteiro!");
            }
        }
        return new Pessoa(name, nif);
    }

    private static EnergyProvider providerFromInput(Set<String> providersNames, Scanner sc){
        String name;
        double price_kwh = 0, tax = 0;
        boolean flag = true;

        System.out.print("Insira o nome deste fornecedor: ");
        name = sc.nextLine();
        while(providersNames.contains(name.toLowerCase())){
            System.out.printf("O nome: '%s' já existe!\n", name);
            System.out.print("Insira o nome deste fornecedor: ");
            name = sc.nextLine();
        }
        providersNames.add(name.toLowerCase());

        System.out.println("Insira o preço por kWh deste fornecedor: ");
        while (flag) {
            try {
                price_kwh = Double.parseDouble(sc.nextLine());
                flag = false;
            } catch (NumberFormatException e) {
                System.out.println("O preço por kWh deve ser um número real!");
            }
        }
        flag = true;

        System.out.println("Insira o imposto aplicado por este fornecedor: ");
        while (flag) {
            try {
                tax = Double.parseDouble(sc.nextLine());
                flag = false;
            } catch (NumberFormatException e) {
                System.out.println("O imposto deve ser um número real!");
            }
        }

        return new EnergyProvider(name,price_kwh,tax);
    } 

    private static Map<String,EnergyProvider> providersFromFile(String path) throws FileNotFoundException{
        try {
            File providers = new File(path);
            return Generator.fileToProviders(providers);
        } catch (FileNotFoundException e) {
            System.out.printf("ERRO: O ficheiro '%s' de fornecedores de energia não existe.",path);
            return null;
        }
    }

    private static List<CasaInteligente> housesFromFile(String devices, String people, String houses) throws FileNotFoundException{
        try {
            File devices_f = new File(devices), people_f = new File(people), houses_f = new File(houses);
            return Generator.fileToHouses(devices_f,people_f,houses_f);
        } catch (FileNotFoundException e) {
            System.out.println("ERRO: Algum dos ficheiros de casas, dispositivos ou pessoas não existe.");
            return null;
        }
    }

    private static int getMainOption(Scanner s){
        int op = 0; boolean flag = true;
        System.out.println("--------------------------------------------------------");
        System.out.println("| 1 | Carregar informação de ficheiros                 |");
        System.out.println("--------------------------------------------------------");
        System.out.println("| 2 | Carregar informação manualmente                  |");
        System.out.println("--------------------------------------------------------");
        System.out.println("| 3 | Guardar estado atual em ficheiros                |");
        System.out.println("--------------------------------------------------------");
        System.out.println("| 4 | Iniciar simulação                                |");
        System.out.println("--------------------------------------------------------");
        System.out.println("| 5 | Instruções de utilização do programa             |");
        System.out.println("--------------------------------------------------------");
        System.out.println("| 6 | Sair                                             |");
        System.out.println("--------------------------------------------------------");
        while (flag){
            try {
                op = Integer.parseInt(s.nextLine());
                flag = false;
            } catch (NumberFormatException e) {
                System.out.println("A opção deve ser um número inteiro!");
            }
        }
        return op;
    }

    public static int getSimulationTypeOption(Scanner s){
        int op = 0; boolean flag = true;
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("| Selecione o tipo de simulação                                                        |");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("| 1 | Simulação básica (o estado das entidades não é alterado no decorrer do tempo)    |");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("| 2 | Simulação normal (o estado das entidades pode ser alterado no decorrer do tempo) |");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("| 3 | Simulação automática (é uma simulação normal (2) em que as alterações ao estado  |");
        System.out.println("|     das entidades provêm de um ficheiro (*.txt) de comandos)                         |");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("| 4 | Voltar ao menu inicial                                                           |");
        System.out.println("----------------------------------------------------------------------------------------");
        while (flag) {
            try {
                op = Integer.parseInt(s.nextLine());
                flag = false;
            } catch (NumberFormatException e) {
                System.out.println("A seleção deve ser um número inteiro!");
            }
        }
        return op;
    }

    private static LocalDate getDateFromInput(String message,Scanner s){
        System.out.print(message);
        LocalDate date = null;
        boolean flag = true;
        while(flag){
            try {
                date = LocalDate.parse(s.nextLine());
                flag = false;
            } catch (DateTimeParseException e) {
                System.out.println("A data tem de estar no formato AAAA-MM-DD!");
            }
        }
        return date;
    }

    private static int getSimulationOption(Scanner s){
        boolean flag = true;
        int op = 0;
        System.out.println("-------------------------------------------------------------");
        System.out.println("| Selecione uma das seguintes opções                        |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 1  | Ver as casas                                         |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 2  | Ver os fornecedores                                  |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 3  | Ver a casa que mais consumiu                         |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 4  | Ver o fornecedor com maior volume de faturação       |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 5  | Ver as faturas emitidas por um certo fornecedor      |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 6  | Ranking de consumidores de energia                   |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 7  | Alterar o preço/kWh de um fornecedor                 |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 8  | Alterar o imposto de um fornecedor                   |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 9  | Ligar todos os dispositivos de uma casa              |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 10 | Desligar todos os dispositivos de uma casa           |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 11 | Ligar um dispositivo numa casa                       |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 12 | Desligar um dispositivo numa casa                    |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 13 | Ligar os dispositivos numa reparticao de uma casa    |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 14 | Desligar os dispositivos numa reparticao de uma casa |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 15 | Ligar todos os dispositivos de todas as casas        |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 16 | Mudar o fornecedor de uma casa                       |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("| 17 | Voltar ao menu anterior                              |");
        System.out.println("-------------------------------------------------------------");
        while (flag) {
            try {
                op = Integer.parseInt(s.nextLine());
                flag = false;
            } catch (Exception e) {
                System.out.println("A opção deve ser um número inteiro!");
            }
        }
        return op;
    }
/*
    private static void watchBasicSimulation(Simulator sim, Scanner s){
        boolean flag = true;
        int op = getSimulationOption(s);
        while(flag){
            switch (op) {
                case 1:{
                    Map<Integer,CasaInteligente> housesMap = sim.getHousesMap();
                    for(Integer nif: housesMap.keySet()){
                        CasaInteligente house = housesMap.get(nif);
                        System.out.println("-------------------------------------------------------");
                        System.out.printf("    ID: %d\n",nif);
                        System.out.printf("    Morada: %s\n", house.getMorada());
                        System.out.printf("    Proprietário [Nome]: %s\n",house.getOwnerName());
                        System.out.printf("    Proprietário [NIF]: %d\n",house.getOwnerNif());
                        System.out.printf("    Fornecedor: %s\n", house.getFornecedor());
                        System.out.printf("    Consumo total: %f\n", house.getTotalConsumption());
                        System.out.println("    Dispositivos:");
                        for(SmartDevice dev: house.getDevices()){
                            System.out.printf("        Tipo: %s, Estado: %s, Consumo total: %f, Consumo diário: %f\n",
                                              dev.getClass().getName(),dev.getOn() ? "Ligado" : "Desligado",dev.getTotalConsumption(),dev.dailyConsumption());
                        }
                        System.out.println("-------------------------------------------------------");
                    }
                    System.out.println("Pressione ENTER para continuar...");
                    s.nextLine();
                    op = getSimulationOption(s);
                    break;
                }
                case 2: {
                    Map<String,EnergyProvider> providersMap = sim.getProvidersMap();
                    for(String providerName: providersMap.keySet()){
                        EnergyProvider provider = providersMap.get(providerName);
                        System.out.println("-------------------------------------------------------");
                        System.out.printf("    ID: %s\n",providerName.toLowerCase());
                        System.out.printf("    Nome: %s\n",provider.getName());
                        System.out.printf("    Preço/kWh: %f\n",provider.getPrice_kwh());
                        System.out.printf("    Imposto: %f\n",provider.getTax());
                        System.out.println("-------------------------------------------------------");
                    }
                    System.out.println("Pressione ENTER para continuar...");
                    s.nextLine();
                    op = getSimulationOption(s);
                    break;
                }
                case 3: {
                    CasaInteligente house = sim.getBiggestConsumer();
                    System.out.println("-------------------------------------------------------");
                    System.out.printf("    ID: %d\n",house.getOwnerNif());
                    System.out.printf("    Morada: %s\n", house.getMorada());
                    System.out.printf("    Proprietário [Nome]: %s\n",house.getOwnerName());
                    System.out.printf("    Proprietário [NIF]: %d\n",house.getOwnerNif());
                    System.out.printf("    Fornecedor: %s\n", house.getFornecedor());
                    System.out.printf("    Consumo total: %f\n", house.getTotalConsumption());
                    System.out.println("    Dispositivos:");
                    for(SmartDevice dev: house.getDevices()){
                        System.out.printf("        Tipo: %s, Estado: %s, Consumo total: %f, Consumo diário: %f\n",
                                            dev.getClass().getName(),dev.getOn() ? "Ligado" : "Desligado",dev.getTotalConsumption(),dev.dailyConsumption());
                    }
                    System.out.println("-------------------------------------------------------");
                    System.out.println("Pressione ENTER para continuar...");
                    s.nextLine();
                    op = getSimulationOption(s);
                    break;
                }
                case 4:{
                    EnergyProvider provider = sim.getBiggestProvider();
                    System.out.println("-------------------------------------------------------");
                    System.out.printf("    ID: %s\n",provider.getName().toLowerCase());
                    System.out.printf("    Nome: %s\n",provider.getName());
                    System.out.printf("    Preço/kWh: %f\n",provider.getPrice_kwh());
                    System.out.printf("    Imposto: %f\n",provider.getTax());
                    System.out.println("-------------------------------------------------------");
                    System.out.println("Pressione ENTER para continuar...");
                    s.nextLine();
                    op = getSimulationOption(s);
                    break;
                }
                case 5:{
                    System.out.print("Insira o id(nome) do fornecedor: ");
                    String idProv = s.nextLine();
                    if(sim.getProvidersMap().containsKey(idProv.toLowerCase())){
                        List<Fatura> faturas = sim.getBillsFromProvider(idProv);
                        for(Fatura fatura : faturas){
                            System.out.printf("%s\n",fatura.printFatura());
                        }
                    }else{
                        System.out.printf("O fornecedor '%s' não existe\n",idProv);
                    }
                    System.out.println("Pressione ENTER para continuar...");
                    s.nextLine();
                    op = getSimulationOption(s);
                    break;
                }
                case 6:{
                    List<CasaInteligente> consumptionRanking = sim.getConsumptionOrder();
                    for(CasaInteligente house : consumptionRanking){
                        System.out.println("-------------------------------------------------------");
                        System.out.printf("    ID: %d\n",house.getOwnerNif());
                        System.out.printf("    Morada: %s\n", house.getMorada());
                        System.out.printf("    Proprietário [Nome]: %s\n",house.getOwnerName());
                        System.out.printf("    Proprietário [NIF]: %d\n",house.getOwnerNif());
                        System.out.printf("    Fornecedor: %s\n", house.getFornecedor());
                        System.out.printf("    Consumo total: %f\n", house.getTotalConsumption());
                        System.out.println("-------------------------------------------------------");
                    }
                    System.out.println("Pressione ENTER para continuar...");
                    s.nextLine();
                    op = getSimulationOption(s);
                    break;
                }

                case 7:{
                    String provName; double new_price = 0.0;
                    System.out.print("Insira o id(nome) do fornecedor: ");
                    provName = s.nextLine();
                    while(flag){
                        System.out.print("Insira o novo preço/kWh: ");
                        try {
                            new_price = Double.parseDouble(s.nextLine());
                            flag = false;
                        } catch (NumberFormatException e) {
                            System.out.println("O preço tem de ser um número real!");
                        }
                    }
                    flag = true;
                    if(!sim.changeProviderPrice(provName, new_price)){
                        System.out.println("Atenção: O fornecedor mencionado não existe. Alteração não efectuada!");
                        System.out.println("Pressione ENTER para continuar...");
                        s.nextLine();
                    }
                    op = getSimulationOption(s);
                    break;
                }

                case 8:{
                    String provName; double new_price = 0.0;
                    System.out.print("Insira o id(nome) do fornecedor: ");
                    provName = s.nextLine();
                    while(flag){
                        System.out.print("Insira o novo imposto: ");
                        try {
                            new_price = Double.parseDouble(s.nextLine());
                            flag = false;
                        } catch (NumberFormatException e) {
                            System.out.println("O imposto tem de ser um número real!");
                        }
                    }
                    flag = true;
                    if(!sim.changeProviderTax(provName, new_price)){
                        System.out.println("Atenção: O fornecedor mencionado não existe. Alteração não efectuada!");
                        System.out.println("Pressione ENTER para continuar...");
                        s.nextLine();
                    }
                    op = getSimulationOption(s);
                    break;
                }

                case 9:{
                    int houseID = 0;
                    while(flag){
                        System.out.print("Insira o id(nif proprietario) da casa: ");
                        try {
                            houseID = Integer.parseInt(s.nextLine());
                            flag = false;
                        } catch (NumberFormatException e) {
                            System.out.println("O id da casa tem de ser um número inteiro!");
                        }
                    }
                    flag = true;
                    if(!sim.setStateAllDevicesHouse(houseID, true)){
                        System.out.println("Atenção: O id fornecido não existe. Alteração não efectuada!");
                        System.out.println("Pressione ENTER para continuar...");
                        s.nextLine();
                    }
                    op = getSimulationOption(s);
                    break;
                }

                case 10:{
                    int houseID = 0;
                    while(flag){
                        System.out.print("Insira o id(nif proprietario) da casa: ");
                        try {
                            houseID = Integer.parseInt(s.nextLine());
                            flag = false;
                        } catch (NumberFormatException e) {
                            System.out.println("O id da casa tem de ser um número inteiro!");
                        }
                    }
                    flag = true;
                    if(!sim.setStateAllDevicesHouse(houseID, false)){
                        System.out.println("Atenção: O id fornecido não existe. Alteração não efectuada!");
                        System.out.println("Pressione ENTER para continuar...");
                        s.nextLine();
                    }
                    op = getSimulationOption(s);
                    break;
                }

                case 11:{ 
                    int houseID = 0; String devID = "null";
                    while(flag){
                        System.out.print("Insira o id(nif proprietario) da casa: ");
                        try {
                            houseID = Integer.parseInt(s.nextLine());
                            flag = false;
                        } catch (NumberFormatException e) {
                            System.out.println("O id da casa tem de ser um número inteiro!");
                        }
                    }
                    flag = true;
                    System.out.print("Insira o id do dispositivo: ");
                    devID = s.nextLine();
                    switch (sim.setStateInDeviceInHouse(devID, houseID, true)) {
                        case 1:{
                            System.out.println("Atenção: O id da casa fornecido não existe. Alteração não efectuada!");
                            System.out.println("Pressione ENTER para continuar...");
                            s.nextLine();
                            break;
                        }

                        case 2:{
                            System.out.println("Atenção: O id do dispositivo fornecido não existe. Alteração não efectuada!");
                            System.out.println("Pressione ENTER para continuar...");
                            s.nextLine();
                            break;
                        }
                    
                        default: break;
                    }
                    op = getSimulationOption(s);
                    break;
                }

                case 12:{
                    int houseID = 0; String devID = "null";
                    while(flag){
                        System.out.print("Insira o id(nif proprietario) da casa: ");
                        try {
                            houseID = Integer.parseInt(s.nextLine());
                            flag = false;
                        } catch (NumberFormatException e) {
                            System.out.println("O id da casa tem de ser um número inteiro!");
                        }
                    }
                    flag = true;
                    System.out.print("Insira o id do dispositivo: ");
                    devID = s.nextLine();
                    switch (sim.setStateInDeviceInHouse(devID, houseID, false)) {
                        case 1:{
                            System.out.println("Atenção: O id da casa fornecido não existe. Alteração não efectuada!");
                            System.out.println("Pressione ENTER para continuar...");
                            s.nextLine();
                            break;
                        }

                        case 2:{
                            System.out.println("Atenção: O id do dispositivo fornecido não existe. Alteração não efectuada!");
                            System.out.println("Pressione ENTER para continuar...");
                            s.nextLine();
                            break;
                        }
                    
                        default: break;
                    }
                    op = getSimulationOption(s);
                    break;
                }

                case 13:{
                    int houseID = 0; String room = "null";
                    while(flag){
                        System.out.print("Insira o id(nif proprietario) da casa: ");
                        try {
                            houseID = Integer.parseInt(s.nextLine());
                            flag = false;
                        } catch (NumberFormatException e) {
                            System.out.println("O id da casa tem de ser um número inteiro!");
                        }
                    }
                    flag = true;
                    System.out.print("Insira o nome da repartição: ");
                    room = s.nextLine();
                    switch (sim.setStateAllDevicesInRoom(houseID, room, true)) {
                        case 1:{
                            System.out.println("Atenção: O id da casa fornecido não existe. Alteração não efectuada!");
                            System.out.println("Pressione ENTER para continuar...");
                            s.nextLine();
                            break;
                        }

                        case 2:{
                            System.out.println("Atenção: A repartição fornecida não existe. Alteração não efectuada!");
                            System.out.println("Pressione ENTER para continuar...");
                            s.nextLine();
                            break;
                        }
                    
                        default: break;
                    }
                    op = getSimulationOption(s);
                    break;
                }

                case 14:{
                    int houseID = 0; String room = "null";
                    while(flag){
                        System.out.print("Insira o id(nif proprietario) da casa: ");
                        try {
                            houseID = Integer.parseInt(s.nextLine());
                            flag = false;
                        } catch (NumberFormatException e) {
                            System.out.println("O id da casa tem de ser um número inteiro!");
                        }
                    }
                    flag = true;
                    System.out.print("Insira o nome da repartição: ");
                    room = s.nextLine();
                    switch (sim.setStateAllDevicesInRoom(houseID, room, false)) {
                        case 1:{
                            System.out.println("Atenção: O id da casa fornecido não existe. Alteração não efectuada!");
                            System.out.println("Pressione ENTER para continuar...");
                            s.nextLine();
                            break;
                        }

                        case 2:{
                            System.out.println("Atenção: A repartição fornecida não existe. Alteração não efectuada!");
                            System.out.println("Pressione ENTER para continuar...");
                            s.nextLine();
                            break;
                        }
                    
                        default: break;
                    }
                    op = getSimulationOption(s);
                    break;
                }

                case 15:{
                    sim.setStateAllDevicesInAllHouses(true);
                    op = getSimulationOption(s);
                    break;
                }

                case 16:{//Mudar o fornecedor de uma casa
                    
                    break;
                }

                case 17:{
                    flag = false;
                    break;
                }
                default:{
                    System.out.println("Opção inválida!");
                    op = getSimulationOption(s);
                    break;
                }
            }
        }
    }
*/
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in); // este scanner é usado no programa todo

        List<CasaInteligente> houses = null;
        Map<String,EnergyProvider> providers = null;
        Set<String> devIDs = new HashSet<String>(); // apenas auxilia a evitar a criação de ids repetidos
        Set<Integer> nifs = new HashSet<Integer>(); // apenas auxilia a evitar a criação de nifs repetidos
        Set<String> providersNames = new HashSet<String>(); // apenas auxilia a evitar a criação de fornecedores repetidos
        boolean flag = true; int option = 0;

        option = getMainOption(s);
        while (flag){
            switch (option) {
                case 1:{
                    // TODO: ⚠️ CARREGAR INFORMAÇÃO DE FICHEIROS ⚠️
                    File providers_f = null, houses_f = null, people_f = null, devices_f = null;
                    //Simulator sim;
                    while(flag){
                        System.out.print("Insira o caminho para o ficheiro dos dispositivos: ");
                        devices_f = new File(s.nextLine());
                        if(!devices_f.exists()){
                            System.out.println("Esse ficheiro não existe!");
                        }else flag = false;
                    }
                    flag = true;
                    while(flag){
                        System.out.print("Insira o caminho para o ficheiro dos proprietários: ");
                        people_f = new File(s.nextLine());
                        if(!people_f.exists()){
                            System.out.println("Esse ficheiro não existe!");
                        }else flag = false;
                    }
                    flag = true;
                    while(flag){
                        System.out.print("Insira o caminho para o ficheiro das casas: ");
                        houses_f = new File(s.nextLine());
                        if(!houses_f.exists()){
                            System.out.println("Esse ficheiro não existe!");
                        }else flag = false;
                    }
                    flag = true;
                    while(flag){
                        System.out.print("Insira o caminho para o ficheiro dos fornecedores: ");
                        providers_f = new File(s.nextLine());
                        if(!providers_f.exists()){
                            System.out.println("Esse ficheiro não existe!");
                        }else flag = false;
                    }
                    try {
                        providers = Generator.fileToProviders(providers_f);
                    } catch (FileNotFoundException e) {}
                    try {
                        houses = Generator.fileToHouses(devices_f,people_f,houses_f);
                    } catch (FileNotFoundException e) {}
                    option = getMainOption(s);
                    flag = true;
                    break;
                }
    
                case 2:{
                    System.out.println("Insira as informações pedidas conforme o formato especificado.");
                    // TODO: ⚠️ CARREGAR INFORMAÇÃO MANUALMENTE ⚠️
                    
                    option = getMainOption(s);
                    flag = true;
                    break;
                }
    
                case 3:{
                    if(houses != null && providers != null && houses.size() > 0 && providers.size() > 0){
                        // TODO: ⚠️ CONCLUIR ESTE IF ⚠️
                    }else{
                        System.out.println("Não há informação para ser carregada!");
                    }
                    option = getMainOption(s);
                    flag = true;
                    break;
                }

                case 4:{
                    if(houses != null && providers != null && houses.size() > 0 && providers.size() > 0){
                        Simulator sim = new Simulator(houses,providers.values());
                        int op = getSimulationTypeOption(s);
                        while (flag) {
                            switch (op) {
                                case 1:{
                                    LocalDate start = null, end = null;
                                    start = getDateFromInput("Insira a data de início: ",s);
                                    end = getDateFromInput("Insira a data de fim: ",s);
                                    sim.startSimulation(start, end);
                                    //watchBasicSimulation(sim,s);
                                    op = getSimulationTypeOption(s);
                                    flag = true;
                                    break;
                                }
                                case 2:{
                                    op = getSimulationTypeOption(s);
                                    flag = true;
                                    break;
                                }
                                case 3:{
                                    op = getSimulationTypeOption(s);
                                    flag = true;
                                    break;
                                }
                                case 4:{
                                    flag = false;
                                    break;
                                }
                            
                                default:{
                                    System.out.println("OPÇÃO INVÁLIDA!");
                                    op = getSimulationTypeOption(s);
                                    flag = true;
                                    break;
                                }
                            }   
                        }
                    }else{
                        System.out.println("Não existe conteúdo para simular!");
                    }
                    option = getMainOption(s);
                    flag = true;
                    break;
                }
    
                case 5: {
                    // TODO: ⚠️ MOSTRAR AS INSTRUÇÕES DE UTILIZAÇÃO DO PROGRAMA ⚠️
                    option = getMainOption(s);
                    flag = true;
                    break;
                }
    
                case 6:{
                    flag = false;
                    break;
                }
            
                default:{
                    System.out.println("OPÇÃO INVÁLIDA");
                    option = getMainOption(s);
                    flag = true;
                    break;
                }
            }
        }
        s.close();
        System.out.println("A sair...");
    }
}
