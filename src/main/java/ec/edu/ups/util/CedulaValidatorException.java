package ec.edu.ups.util;

/**
 * Excepción personalizada que se lanza cuando una cadena de texto no cumple con las
 * reglas de validación para una cédula ecuatoriana.
 * <p>
 * Esta excepción extiende {@link RuntimeException}, lo que la convierte en una
 * excepción no comprobada (unchecked exception), indicando un error de programación
 * o de datos de entrada inválidos que no se espera que se recuperen en tiempo de ejecución.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class CedulaValidatorException extends RuntimeException {

    /**
     * Construye una nueva CedulaValidatorException con un mensaje de detalle específico.
     *
     * @param message El mensaje de detalle. El mensaje de detalle se guarda para
     *                su posterior recuperación por el método {@link #getMessage()}.
     */
    public CedulaValidatorException(String message) {
        super(message);
    }
}