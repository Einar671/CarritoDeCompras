package ec.edu.ups.modelo;

import java.util.Objects;

/**
 * Representa una pregunta de seguridad utilizada para la recuperación de cuentas.
 * <p>
 * Cada pregunta tiene un identificador numérico único (ID) y el texto
 * correspondiente a la pregunta. El ID se utiliza para la persistencia y la
 * referencia, mientras que el texto se muestra al usuario.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class Pregunta {

    /**
     * El identificador numérico único de la pregunta.
     */
    private int id;
    /**
     * El texto completo de la pregunta de seguridad.
     */
    private String texto;

    /**
     * Constructor para crear una nueva Pregunta.
     *
     * @param id    El ID único para la pregunta.
     * @param texto El texto de la pregunta.
     */
    public Pregunta(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    /**
     * Obtiene el ID de la pregunta.
     *
     * @return El ID de la pregunta.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID de la pregunta.
     *
     * @param id El nuevo ID para la pregunta.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el texto de la pregunta.
     *
     * @return El texto de la pregunta.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Establece el texto de la pregunta.
     *
     * @param texto El nuevo texto para la pregunta.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Devuelve una representación en cadena del objeto Pregunta.
     *
     * @return Una cadena que muestra el ID y el texto de la pregunta.
     */
    @Override
    public String toString() {
        return "Pregunta{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                '}';
    }

    /**
     * Compara este objeto Pregunta con otro para ver si son iguales.
     * <p>
     * Dos preguntas se consideran iguales si sus IDs son idénticos.
     *
     * @param o El objeto a comparar.
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pregunta pregunta = (Pregunta) o;
        return id == pregunta.id;
    }

    /**
     * Genera un código hash para el objeto Pregunta.
     * <p>
     * El código hash se basa únicamente en el ID de la pregunta.
     *
     * @return El código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}