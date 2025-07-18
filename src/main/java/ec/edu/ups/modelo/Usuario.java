package ec.edu.ups.modelo;

import ec.edu.ups.util.CedulaValidatorException;
import ec.edu.ups.util.ContraseñaValidatorException;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Usuario {

    private String username;
    private Rol rol;
    private String password;
    private String nombreCompleto;
    private int edad;
    private Genero genero;
    private String telefono;
    private String email ;
    private List<Respuesta> respuestasSeguridad;


    public Usuario(String username, Rol rol, String password, String nombreCompleto, int edad, Genero genero, String telefono, String email)
            throws CedulaValidatorException, ContraseñaValidatorException {
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.genero = genero;
        this.telefono = telefono;
        this.email = email;
        this.rol = rol;
        this.respuestasSeguridad = new ArrayList<>();

        // Corrección: Usar los setters para forzar la validación desde la creación.
        setUsername(username);
        setPassword(password);
    }
    public Usuario(Rol rol, String nombreCompleto, int edad, Genero genero, String telefono, String email)
            throws CedulaValidatorException, ContraseñaValidatorException {
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.genero = genero;
        this.telefono = telefono;
        this.email = email;
        this.rol = rol;
        this.respuestasSeguridad = new ArrayList<>();
    }



    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String    getUsername() {
        return username;
    }

    public void setUsername(String username) throws CedulaValidatorException {
        if(!validarCedula(username)) {
            throw new CedulaValidatorException("");
        }
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

    // Corrección: Se añade 'throws' para manejar la validación correctamente
    public void setPassword(String password) throws ContraseñaValidatorException {
        if(!validarContraseña(password)) {
            throw new ContraseñaValidatorException("");
        }
        this.password = password;
    }


    public List<Respuesta> getRespuestasSeguridad() {
        return respuestasSeguridad;
    }

    public void setRespuestasSeguridad(List<Respuesta> respuestasSeguridad) {
        this.respuestasSeguridad = respuestasSeguridad;
    }


    public void addRespuesta(Pregunta pregunta, String respuestaTexto) {
        if (this.respuestasSeguridad == null) {
            this.respuestasSeguridad = new ArrayList<>();
        }
        this.respuestasSeguridad.add(new Respuesta(pregunta, respuestaTexto));
    }

    public  boolean validarCedula(String cedula) {
        if (cedula == null || !cedula.matches("\\d{10}")) {
            return false;
        }

        int provincia = Integer.parseInt(cedula.substring(0, 2));
        int tercerDigito = Character.getNumericValue(cedula.charAt(2));

        if (provincia < 1 || provincia > 24 || tercerDigito >= 6) {
            return false;
        }

        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i));
            if (i % 2 == 0) {
                int producto = digito * 2;
                suma += (producto > 9) ? (producto - 9) : producto;
            } else {
                suma += digito;
            }
        }

        int digitoVerificadorCalculado = (10 - (suma % 10)) % 10;
        int digitoVerificadorReal = Character.getNumericValue(cedula.charAt(9));

        return digitoVerificadorCalculado == digitoVerificadorReal;
    }

    private boolean validarContraseña(String contrasena) {
        if (contrasena == null || contrasena.length() < 6) {
            return false;
        }

        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneSimbolo = false;

        for (char c : contrasena.toCharArray()) {
            if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            } else if (Character.isLowerCase(c)) {
                tieneMinuscula = true;
            } else if (c == '@' || c == '_' || c == '-') {
                tieneSimbolo = true;
            }
        }

        return tieneMayuscula && tieneMinuscula && tieneSimbolo;
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