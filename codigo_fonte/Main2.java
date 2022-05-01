import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main2 {

    private static Simulator loadState(String path) throws FileNotFoundException, 
                                                    IOException,
                                                    ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Simulator sim = (Simulator) ois.readObject();
        ois.close();
        return sim;                     
    }

    private static LocalDateTime getDateFromInput(String message,Scanner s){
        LocalDateTime date = null;
        boolean flag = true;
        while(flag){
            try {
                System.out.print(message);
                String aux = s.nextLine();
                if(aux.length() == 16) aux = aux.substring(0,10) + "T" + aux.substring(11,16);
                date = LocalDateTime.parse(s.nextLine());
                flag = false;
            } catch (DateTimeParseException e) {
                System.out.println("A data tem de estar no formato AAAA-MM-DD HH:mm!");
            }
        }
        return date;
    }

    private static int getMainOption(Scanner s){
        int op = 0; boolean flag = true;
        System.out.println("--------------------------------------------------------");
        System.out.println("| 1 | Carregar informação de ficheiro                  |");
        System.out.println("--------------------------------------------------------");
        System.out.println("| 2 | Carregar informação manualmente                  |");
        System.out.println("--------------------------------------------------------");
        System.out.println("| 3 | Guardar estado atual em ficheiro                 |");
        System.out.println("--------------------------------------------------------");
        System.out.println("| 4 | Iniciar simulação                                |");
        System.out.println("--------------------------------------------------------");
        System.out.println("| 5 | Sair                                             |");
        System.out.println("--------------------------------------------------------");
        while (flag){
            try {
                System.out.print("Insira a opção: ");
                op = Integer.parseInt(s.nextLine());
                flag = false;
            } catch (NumberFormatException e) {
                System.out.println("A opção deve ser um número inteiro!");
            } catch (Exception e){
                System.out.println("Ocorreu um erro desconhecido!");
            }
        }
        return op;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        boolean flag = true;
        int option;
        Simulator sim = null; // guarda o estado do programa!

        while (flag) {
            option = getMainOption(s);
            switch (option){
                case 1:{
                    System.out.print("Insira o caminho para o ficheiro: ");
                    String path = s.nextLine();
                    try {
                        sim = loadState(path);
                        System.out.println("Estado carregado com sucesso!\nPressione ENTER para continuar");
                        s.nextLine();
                    } catch (FileNotFoundException e){
                        System.out.printf("O ficheiro %s não existe\nPressione ENTER para continuar\n",path);
                        s.nextLine();
                    } catch (ClassNotFoundException e){
                        System.out.println("A classe não existe\nPressione ENTER para continuar");
                        s.nextLine();
                    } catch (IOException e){
                        System.out.println("Erro de input/output\nPressione ENTER para continuar");
                        s.nextLine();
                    }
                    break;
                }
                case 2:{
                    
                    break;
                }
                case 3:{
                    if(sim == null){
                        System.out.println("Não existem dados para ser guardados!\nPressione ENTER para continuar");
                        s.nextLine();
                    } else{
                        System.out.print("Insira o caminho para o ficheiro: ");
                        String path = s.nextLine();
                        try{
                            sim.saveState(path);
                            System.out.println("Informação guardada com sucesso!\nPressione ENTER para continuar");
                            s.nextLine();
                        } catch (FileNotFoundException e){
                            System.out.printf("O ficheiro %s não existe\nPressione ENTER para continuar\n");
                            s.nextLine();
                        } catch (IOException e){
                            System.out.println("Erro do input/output\nPressione ENTER para continuar");
                            s.nextLine();
                        }
                        
                    }
                    break;
                }
                case 4:{
                    
                    break;
                }
                case 5:{
                    flag = false;
                    break;
                }
                default:{
                    System.out.println("Opção inválida (deve ser um inteiro entre 1 e 5)");
                    break;
                }
            }
        }
        s.close();
    }
}
