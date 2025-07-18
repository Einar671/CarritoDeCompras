package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Implementación de la interfaz PreguntaDAO que utiliza un archivo binario
 * para la persistencia de los IDs de las preguntas de seguridad.
 * <p>
 * Esta clase almacena únicamente los identificadores numéricos (IDs) de las preguntas
 * en un archivo de acceso aleatorio. El texto de cada pregunta se obtiene dinámicamente
 * a través del {@link MensajeInternacionalizacionHandler}, permitiendo que las preguntas
 * se muestren en el idioma correspondiente.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class PreguntaDAOArchivoBinario implements PreguntaDAO {

    /**
     * La ruta completa del archivo binario donde se almacenan los IDs de las preguntas.
     */
    private final String ruta;
    /**
     * El manejador de internacionalización para obtener el texto de las preguntas.
     */
    private final MensajeInternacionalizacionHandler mensajes;
    /**
     * El tamaño en bytes de cada registro en el archivo (un entero para el ID).
     */
    private static final int RECORD_SIZE = 4; // Tamaño de un int en bytes

    /**
     * Constructor para PreguntaDAOArchivoBinario.
     *
     * @param ruta La ruta del archivo binario a utilizar.
     * @param mensajes El manejador de internacionalización para resolver los textos de las preguntas.
     */
    public PreguntaDAOArchivoBinario(String ruta, MensajeInternacionalizacionHandler mensajes) {
        this.ruta = ruta;
        this.mensajes = mensajes;
        inicializarArchivo();
    }

    /**
     * Inicializa el archivo de preguntas. Si el archivo no existe o está vacío,
     * lo crea y escribe los IDs de las preguntas por defecto (del 1 al 10).
     */
    private void inicializarArchivo() {
        try {
            File archivo = new File(ruta);
            if (!archivo.exists() || archivo.length() == 0) {
                try (RandomAccessFile file = new RandomAccessFile(archivo, "rw")) {
                    // Escribe los IDs del 1 al 10 en el archivo binario
                    IntStream.rangeClosed(1, 10).forEach(id -> {
                        try {
                            file.writeInt(id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (IOException e) {
            System.err.println("Error al inicializar el archivo de preguntas: " + e.getMessage());
        }
    }

    /**
     * Lee todos los IDs del archivo binario y los convierte en una lista de objetos {@link Pregunta}.
     * El texto de cada pregunta se obtiene del manejador de mensajes usando la clave "pregunta.seguridad.ID".
     *
     * @return Una lista de todas las preguntas de seguridad disponibles.
     */
    @Override
    public List<Pregunta> obtenerTodasLasPreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();
        try (RandomAccessFile file = new RandomAccessFile(ruta, "r")) {
            long numRecords = file.length() / RECORD_SIZE;
            for (int i = 0; i < numRecords; i++) {
                file.seek(i * RECORD_SIZE);
                int id = file.readInt();
                String texto = mensajes.get("pregunta.seguridad." + id);
                preguntas.add(new Pregunta(id, texto));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return preguntas;
    }

    /**
     * Busca una pregunta específica por su ID.
     * <p>
     * Este método primero obtiene todas las preguntas y luego filtra la lista para encontrar
     * la que coincide con el ID proporcionado.
     *
     * @param id El ID de la pregunta a buscar.
     * @return El objeto {@link Pregunta} encontrado, o {@code null} si no existe.
     */
    @Override
    public Pregunta buscarPorId(int id) {
        return obtenerTodasLasPreguntas().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}