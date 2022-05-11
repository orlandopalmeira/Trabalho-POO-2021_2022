import java.time.LocalDateTime;
import java.util.function.Consumer;

public class Command{
    /**
     * Instante em que este comando é executado.
     */
    private LocalDateTime executionDateTime;

    /**
     * Simulação em que o comando é executado (!AGREGAÇÃO!)
     */
    private Simulator simulator;

    /**
     * Tratamento a ser aplicado na simulação.
     */
    private Consumer<Simulator> command;

    /**
     * Indica se o comando pode ser executado durante a simulação (true) ou apenas no fim da simulação (false).
     */
    private boolean flag;
    
    /**
     * Executa este comando.
     */
    public void execute(){
        this.command.accept(this.simulator);
    }

    public Command(LocalDateTime executionDateTime, Simulator simulator, Consumer<Simulator> command,boolean flag){
        this.executionDateTime = executionDateTime;
        this.simulator = simulator;
        this.command = command;
        this.flag = flag;
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
}
