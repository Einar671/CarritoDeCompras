package ec.edu.ups.modelo;

/**
 * Representa la respuesta de un {@link Usuario} a una {@link Pregunta} de seguridad específica.
 * <p>
 * Esta clase vincula una pregunta con la respuesta textual proporcionada por el usuario.
 * Se utiliza para verificar la identidad del usuario durante procesos como la
 * recuperación de contraseña.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class Respuesta {

    /**
     * La pregunta de seguridad a la que esta respuesta corresponde.
     */
    private Pregunta pregunta;
    /**
     * El texto de la respuesta proporcionado por el usuario.
     */
    private String respuesta;

    /**
     * Constructor para crear una nueva Respuesta.
     *
     * @param pregunta  La {@link Pregunta} a la que se está respondiendo.
     * @param respuesta El texto de la respuesta.
     */
    public Respuesta(Pregunta pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    /**
     * Obtiene la pregunta asociada a esta respuesta.
     *
     * @return El objeto {@link Pregunta}.
     */
    public Pregunta getPregunta() {
        return pregunta;
    }

    /**
     * Establece o cambia la pregunta asociada a esta respuesta.
     *
     * @param pregunta La nueva {@link Pregunta}.
     */
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    /**
     * Obtiene el texto de la respuesta.
     *
     * @return La respuesta del usuario como una cadena de texto.
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * Establece o cambia el texto de la respuesta.
     *
     * @param respuesta El nuevo texto para la respuesta.
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * Compara la respuesta almacenada con una respuesta proporcionada para su validación.
     * <p>
     * La comparación no distingue entre mayúsculas y minúsculas y elimina los espacios
     * en blanco al principio y al final de ambas cadenas antes de compararlas.
     *
     * @param respuestaAValidar La respuesta que se va a verificar.
     * @return {@code true} si las respuestas coinciden, {@code false} en caso contrario.
     *         También devuelve {@code false} si alguna de las respuestas es nula.
     */
    public boolean esRespuestaCorrecta(String respuestaAValidar) {
        if (this.respuesta == null || respuestaAValidar == null) {
            return false;
        }
        return this.respuesta.trim().equalsIgnoreCase(respuestaAValidar.trim());
    }

    /**
     * Devuelve una representación en cadena del objeto Respuesta.
     * El nombre de la clase en el String es "RespuestaSeguridad".
     *
     * @return Una cadena que muestra la pregunta y la respuesta.
     */
    @Override
    public String toString() {
        return "RespuestaSeguridad{" +
                "pregunta=" + pregunta +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }
}