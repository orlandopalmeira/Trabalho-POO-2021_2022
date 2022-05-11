import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Consumer;

public class Command{
    /**
     * Instante em que este comando é executado.
     */
    private LocalDateTime executionDateTime;

    /**
     * Tratamento a ser aplicado na simulação.
     */
    private Consumer<Simulator> command;

    /**
     * Indica se o comando pode ser executado durante a simulação (true) ou apenas no fim da simulação (false).
     */
    private boolean flag;
    
    public Command(LocalDateTime executionDateTime, Consumer<Simulator> command, boolean flag){
        this.executionDateTime = executionDateTime;
        this.command = command;
        this.flag = flag;
    }

    /**
     * Executa este comando na simulação fornecida.
     */
    public void execute(Simulator simulator){
        this.command.accept(simulator);
    }

    /**
     * Retorna o instante em que este comando é executado.
     */
    public LocalDateTime getExecutionDateTime() {
        return this.executionDateTime;
    }

    /**
     * Retorna o valor da flag.
     */
    public boolean getFlag(){
        return this.flag;
    }

    /**
     * Faz o parse de uma string e devolve no formato de um comando.
     */
    public static Command fromString(String s) throws DateTimeParseException, NumberFormatException{
        // DATETIME,id_elemento,nome_operacao,novo_valor ou identificação de algo (dispositivo, fornecedor, etc...)
        String[] aux = s.split(",");
        LocalDateTime executionDateTime = LocalDateTime.parse(aux[0],DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String id = aux[1], action = aux[2];
        Command result = null;
        switch(action){

            case "alteraPreco":{
                double new_price = Double.parseDouble(aux[3]);
                result = new Command(executionDateTime, sim -> sim.changeProviderPrice(id,new_price), false);
                break;
            }

            case "alteraImposto":{
                double new_tax = Double.parseDouble(aux[3]);
                result = new Command(executionDateTime, sim -> sim.changeProviderTax(id,new_tax), false);
                break;
            }

            case "alteraFornecedor":{
                String new_provider = aux[3];
                int house_id = Integer.parseInt(id);
                result = new Command(executionDateTime, sim -> sim.changeHouseProvider(new_provider, house_id), false);
                break;
            }

            case "ligaTodosNaCasa":{
                int house_id = Integer.parseInt(id);
                result = new Command(executionDateTime, sim -> sim.setStateAllDevicesHouse(house_id, true, executionDateTime), true);
                break;
            }

            case "desligaTodosNaCasa":{
                int house_id = Integer.parseInt(id);
                result = new Command(executionDateTime, sim -> sim.setStateAllDevicesHouse(house_id, false, executionDateTime), true);
                break;
            }

            case "ligaTodosNaReparticao":{
                int house_id = Integer.parseInt(id);
                result = new Command(executionDateTime, sim -> sim.setStateAllDevicesInRoom(house_id, aux[3], true, executionDateTime), true);
                break;
            }

            case "desligaTodosNaReparticao":{
                int house_id = Integer.parseInt(id);
                result = new Command(executionDateTime, sim -> sim.setStateAllDevicesInRoom(house_id, aux[3], false, executionDateTime), true);
                break;
            }

            case "ligaTudoEmTodasAsCasas":{
                result = new Command(executionDateTime,sim -> sim.setStateAllDevicesInAllHouses(true, executionDateTime), true);
                break;
            }

            case "desligaTudoEmTodasAsCasas":{
                result = new Command(executionDateTime,sim -> sim.setStateAllDevicesInAllHouses(false, executionDateTime), true);
                break;
            }

            case "ligaDispositivoNaCasa":{
                int house_id = Integer.parseInt(id);
                result = new Command(executionDateTime,sim -> sim.setStateInDeviceInHouse(aux[3], house_id, true, executionDateTime), true);
                break;
            }

            case "desligaDispositivoNaCasa":{
                int house_id = Integer.parseInt(id);
                result = new Command(executionDateTime,sim -> sim.setStateInDeviceInHouse(aux[3], house_id, false, executionDateTime), true);
                break;
            }

            default:{
                System.out.println("Comando inválido");
                break;
            }
        }
        return result;
    }
    
}
