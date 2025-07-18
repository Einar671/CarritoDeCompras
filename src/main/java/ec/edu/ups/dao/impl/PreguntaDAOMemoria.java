package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PreguntaDAOMemoria implements PreguntaDAO {

    private final List<Pregunta> preguntas;
    private final MensajeInternacionalizacionHandler mensajes;

    public PreguntaDAOMemoria(MensajeInternacionalizacionHandler mensajes) {
        this.mensajes = mensajes;
        this.preguntas = new ArrayList<>();
        cargarPreguntas();
    }

    private void cargarPreguntas() {
        // Carga las 10 preguntas desde el archivo de propiedades
        IntStream.rangeClosed(1, 10).forEach(id -> {
            String texto = mensajes.get("pregunta.seguridad." + id);
            preguntas.add(new Pregunta(id, texto));
        });
    }

    @Override
    public List<Pregunta> obtenerTodasLasPreguntas() {
        // Recarga las preguntas para asegurar que el texto esté en el idioma actual
        preguntas.forEach(p -> p.setTexto(mensajes.get("pregunta.seguridad." + p.getId())));
        return new ArrayList<>(preguntas);
    }

    @Override
    public Pregunta buscarPorId(int id) {
        return obtenerTodasLasPreguntas().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }
}