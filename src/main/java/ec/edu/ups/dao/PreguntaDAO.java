package ec.edu.ups.dao;

import ec.edu.ups.modelo.Pregunta;
import java.util.List;

public interface PreguntaDAO {
    List<Pregunta> obtenerTodasLasPreguntas();

    Pregunta buscarPorId(int id);
}
