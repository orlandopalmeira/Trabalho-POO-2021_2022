/**
 * Esta classe serve para sinalizar a tentativa de criação de comandos inválidos.
 * É essencialmente usada para parsing de strings que representem comandos inválidos.
 * @author Miguel Silva, Orlando Palmeira e Pedro Martins
 */
public class InvalidCommandException extends Exception {
    public InvalidCommandException(String message) {
        super(message);
    }
}