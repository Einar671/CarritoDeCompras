package ec.edu.ups.util;

/**
 * Excepción personalizada que se lanza cuando una contraseña no cumple con las
 * reglas de complejidad y seguridad definidas en el sistema.
 * <p>
 * Esta excepción extiende {@link RuntimeException}, lo que la convierte en una
 * excepción no comprobada (unchecked exception), indicando un error de programación
 * o de datos de entrada inválidos que no se espera que se recuperen en tiempo de ejecución.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class ContraseñaValidatorException extends RuntimeException {

    /**
     * Construye una nueva ContraseñaValidatorException con un mensaje de detalle específico.
     *
     * @param message El mensaje de detalle. El mensaje de detalle se guarda para
     *                su posterior recuperación por el método {@link #getMessage()}.
     */
    public ContraseñaValidatorException(String message) {
        super(message);
    }
}