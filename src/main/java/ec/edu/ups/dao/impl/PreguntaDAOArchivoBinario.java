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

public class PreguntaDAOArchivoBinario implements PreguntaDAO {

    private final String ruta;
    private final MensajeInternacionalizacionHandler mensajes;
    private static final int RECORD_SIZE = 4; // Tamaño de un int en bytes

    public PreguntaDAOArchivoBinario(String ruta, MensajeInternacionalizacionHandler mensajes) {
        this.ruta = ruta;
        this.mensajes = mensajes;
        inicializarArchivo();
    }

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

    @Override
    public Pregunta buscarPorId(int id) {
        return obtenerTodasLasPreguntas().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
