package ec.edu.ups.modelo;

/**
 * Representa los géneros con los que se puede identificar un {@link Usuario}.
 * <p>
 * Esta enumeración proporciona un conjunto de valores fijos para el género,
 * asegurando la consistencia de los datos en toda la aplicación.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public enum Genero {
    /**
     * Representa el género masculino.
     */
    MASCULINO,

    /**
     * Representa el género femenino.
     */
    FEMENINO,

    /**
     * Representa otras identidades de género o la opción de no especificar.
     */
    OTRO
}