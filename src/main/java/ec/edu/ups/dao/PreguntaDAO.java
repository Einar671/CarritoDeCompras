package ec.edu.ups.dao;

import ec.edu.ups.modelo.Pregunta;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia de datos (DAO - Data Access Object)
 * para la entidad {@link Pregunta}.
 * <p>
 * Esta interfaz abstrae los detalles de implementación del almacenamiento de datos,
 * permitiendo que las preguntas de seguridad se puedan obtener desde diferentes
 * fuentes (memoria, archivos, bases de datos) de manera intercambiable.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public interface PreguntaDAO {

    /**
     * Recupera una lista de todas las preguntas de seguridad disponibles en la fuente de datos.
     *
     * @return Una {@link List} de todos los objetos {@link Pregunta}.
     */
    List<Pregunta> obtenerTodasLasPreguntas();

    /**
     * Busca y recupera una pregunta de seguridad específica por su identificador único (ID).
     *
     * @param id El ID de la pregunta a buscar.
     * @return El objeto {@link Pregunta} encontrado, o {@code null} si no existe ninguna con ese ID.
     */
    Pregunta buscarPorId(int id);
}