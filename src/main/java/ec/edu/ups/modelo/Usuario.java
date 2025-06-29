package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {

    private String username;
    private Rol rol;
    private String password;
    private List<RespuestaSeguridad> respuestasSeguridad;

    public Usuario(String username, Rol rol, String password) {
        this.username = username;
        this.rol = rol;
        this.password = password;
        this.respuestasSeguridad = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<RespuestaSeguridad> getRespuestasSeguridad() {
        return respuestasSeguridad;
    }

    public void setRespuestasSeguridad(List<RespuestaSeguridad> respuestasSeguridad) {
        this.respuestasSeguridad = respuestasSeguridad;
    }


    public void addRespuesta(Pregunta pregunta, String respuestaTexto) {
        if (this.respuestasSeguridad == null) {
            this.respuestasSeguridad = new ArrayList<>();
        }
        this.respuestasSeguridad.add(new RespuestaSeguridad(pregunta, respuestaTexto));
    }

    public int preguntasAsignadas(){
        return this.respuestasSeguridad.size();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", rol=" + rol +
                ", password='" + password + '\'' +
                ", respuestasSeguridad=" + respuestasSeguridad +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(username, usuario.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}