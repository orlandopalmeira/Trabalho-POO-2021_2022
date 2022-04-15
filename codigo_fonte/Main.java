import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private SmartBulb bulbFromInput(Set<String> ids){
        // Variáveis auxiliares
        Scanner sc = new Scanner(System.in);
        String s,idBulb,tone; boolean state, flag = true;
        double dimension = 1.0;
        SmartBulb sb;

        // Criação da lâmpada
        System.out.print("Insira o id desta lâmpada: ");
        s = sc.nextLine();
        while(ids.contains(s)){
            System.out.println("O id que pretende atribuir já existe!");
            System.out.print("Insira o id desta lâmpada: ");
            s = sc.nextLine();
        }
        idBulb = s;
        ids.add(s);

        System.out.print("O dispositivo inicia-se ligado?(s/n): ");
        s = sc.nextLine();
        while (s.length() != 1 && !("sSnN".contains(s))) {
            System.out.println("Opção inválida, escreva 's' ou 'S' para sim e 'n' ou 'N' para não (sem aspas)!");
            System.out.print("O dispositivo inicia-se ligado?(s/n): ");
            s = sc.nextLine();
        }
        if(s.toLowerCase().equals("s")) state = true;
        else state = false;

        System.out.print("Insira a tonalidade da lâmpada (WARM/COLD/NEUTRAL): ");
        s = sc.nextLine().toLowerCase();
        while(!(s.equals("warm") || s.equals("cold") || s.equals("neutral"))){
            System.out.println("Opção inválida, escreva 'warm','cold' ou 'neutral'");
            System.out.print("Insira a tonalidade da lâmpada (WARM/COLD/NEUTRAL): ");
            s = sc.nextLine().toLowerCase();
        }
        tone = s;
        while(flag){
            try{
                s = sc.nextLine().toLowerCase();
                dimension = Double.parseDouble(s);
                flag = false;
            } catch (NumberFormatException e){
                System.out.println("ERRO: Dimensão inválida");
            } catch (Exception e){
                System.out.println("Ocorreu um erro desconhecido");
            }
        }
        switch (tone) {
            case "warm":sb = new SmartBulb(idBulb,state,2,dimension); break;
            case "cold":sb = new SmartBulb(idBulb,state,0,dimension);break;
            case "neutral":sb = new SmartBulb(idBulb,state,1,dimension);break;
        }
        sc.close();
        return null;
    }

    private static Map<String,EnergyProvider> providersFromFile(String path) throws FileNotFoundException{
        try {
            File providers = new File(path);
            return Generator.fileToProviders(providers);
        } catch (FileNotFoundException e) {
            System.out.println("ERRO: O ficheiro de fornecedores de energia não existe.");
            return null;
        } catch (Exception e) {
            System.out.println("Ocorreu um erro desconhecido");
            return null;
        }
    }

    private static List<CasaInteligente> housesFromFile(String[] paths) throws FileNotFoundException{
        if(paths.length == 3){
            try {
                File devices = new File(paths[0]), people = new File(paths[1]), houses = new File(paths[2]);
                return Generator.fileToHouses(devices,people,houses);
            } catch (FileNotFoundException e) {
                System.out.println("ERRO: Algum dos ficheiros de casas, dispositivos ou pessoas não existe.");
                return null;
            } catch (Exception e) {
                System.out.println("Ocorreu um erro desconhecido");
                return null;
            }
        }else{
            System.out.println("O array com os caminhos dos ficheiros para as casas, pessoas e dispositivos tem tamanho inválido.");
            return null;
        }
    }

    public static void main(String[] args){
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("| 1 | Carregar informação de ficheiros                                    |");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("| 2 | Carregar informação manualmente                                     |");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("| 3 | Guardar estado atual em ficheiros                                   |");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("| 4 | Sair                                                                |");
        System.out.println("---------------------------------------------------------------------------");
        List<CasaInteligente> houses = null;
        Map<String,EnergyProvider> providers = null;
        Set<String> devIDs = new HashSet<String>(); // apenas auxilia a evitar a criação de ids repetidos
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextInt()) {
            case 1:{
                break;
            }

            case 2:{
                break;
            }

            case 3:{
                if(houses != null && providers != null){
                    // TODO: ⚠️ ESTE IF PRECISA DE SER CONCLUÍDO ⚠️
                }else{
                    System.out.println("Não há informação para ser carregada!");
                }
                break;
            }

            case 4: break;
        
            default:{
                System.out.println("OPÇÃO INVÁLIDA");
                break;
            }
        }
        scanner.close();
    }
}
