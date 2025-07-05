package ec.edu.ups.modelo;


public class Respuesta {

    private Pregunta pregunta;
    private String respuesta;

    public Respuesta(Pregunta pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public boolean esRespuestaCorrecta(String respuestaAValidar) {
        if (this.respuesta == null || respuestaAValidar == null) {
            return false;
        }
        return this.respuesta.trim().equalsIgnoreCase(respuestaAValidar.trim());
    }

    @Override
    public String toString() {
        return "RespuestaSeguridad{" +
                "pregunta=" + pregunta +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }
}