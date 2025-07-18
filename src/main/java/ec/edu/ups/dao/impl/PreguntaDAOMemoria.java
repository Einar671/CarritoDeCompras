package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Implementación de la interfaz PreguntaDAO que utiliza una lista en memoria
 * para la persistencia de las preguntas de seguridad.
 * <p>
 * Las preguntas se cargan inicialmente desde el manejador de internacionalización
 * y se mantienen en una lista. El texto de las preguntas se actualiza dinámicamente
 * para reflejar el idioma actual de la aplicación.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class PreguntaDAOMemoria implements PreguntaDAO {

    /**
     * La lista en memoria que almacena todos los objetos Pregunta.
     */
    private final List<Pregunta> preguntas;
    /**
     * El manejador de internacionalización para obtener el texto de las preguntas.
     */
    private final MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor para PreguntaDAOMemoria.
     *
     * @param mensajes El manejador de internacionalización que se utilizará para
     *                 cargar los textos de las preguntas.
     */
    public PreguntaDAOMemoria(MensajeInternacionalizacionHandler mensajes) {
        this.mensajes = mensajes;
        this.preguntas = new ArrayList<>();
        cargarPreguntas();
    }

    /**
     * Carga las preguntas de seguridad por defecto (IDs del 1 al 10) en la lista
     * en memoria. El texto de cada pregunta se obtiene del manejador de mensajes.
     */
    private void cargarPreguntas() {
        // Carga las 10 preguntas desde el archivo de propiedades
        IntStream.rangeClosed(1, 10).forEach(id -> {
            String texto = mensajes.get("pregunta.seguridad." + id);
            preguntas.add(new Pregunta(id, texto));
        });
    }

    /**
     * Devuelve una lista de todas las preguntas de seguridad disponibles.
     * <p>
     * Antes de devolver la lista, actualiza el texto de cada pregunta para asegurar
     * que esté en el idioma actualmente configurado en el manejador de mensajes.
     *
     * @return Una nueva lista con todas las preguntas y sus textos actualizados.
     */
    @Override
    public List<Pregunta> obtenerTodasLasPreguntas() {
        // Recarga las preguntas para asegurar que el texto esté en el idioma actual
        preguntas.forEach(p -> p.setTexto(mensajes.get("pregunta.seguridad." + p.getId())));
        return new ArrayList<>(preguntas);
    }

    /**
     * Busca una pregunta específica por su ID.
     * <p>
     * Este método primero obtiene la lista completa de preguntas con sus textos
     * actualizados y luego la filtra para encontrar la que coincide con el ID.
     *
     * @param id El ID de la pregunta a buscar.
     * @return El objeto {@link Pregunta} encontrado, o {@code null} si no existe.
     */
    @Override
    public Pregunta buscarPorId(int id) {
        return obtenerTodasLasPreguntas().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }
}