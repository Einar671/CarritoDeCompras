package ec.edu.ups.modelo;

import ec.edu.ups.util.CedulaValidatorException;
import ec.edu.ups.util.ContraseñaValidatorException;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Representa a un usuario del sistema.
 * <p>
 * Esta clase contiene toda la información personal y de credenciales de un usuario,
 * incluyendo su nombre de usuario (cédula), rol, contraseña, y datos personales.
 * También gestiona las respuestas a las preguntas de seguridad para la recuperación
 * de la cuenta.
 * <p>
 * La clase impone reglas de validación para la cédula (username) y la contraseña
 * a través de excepciones personalizadas.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class Usuario {

    /**
     * El nombre de usuario, que debe ser una cédula ecuatoriana válida de 10 dígitos.
     */
    private String username;
    /**
     * El rol del usuario en el sistema (ADMINISTRADOR o USUARIO).
     */
    private Rol rol;
    /**
     * La contraseña del usuario. Debe cumplir con ciertos criterios de seguridad.
     */
    private String password;
    /**
     * El nombre y apellido completos del usuario.
     */
    private String nombreCompleto;
    /**
     * La edad del usuario.
     */
    private int edad;
    /**
     * El género con el que se identifica el usuario.
     */
    private Genero genero;
    /**
     * El número de teléfono de contacto del usuario.
     */
    private String telefono;
    /**
     * La dirección de correo electrónico del usuario.
     */
    private String email ;
    /**
     * Una lista de las respuestas a las preguntas de seguridad proporcionadas por el usuario.
     */
    private List<Respuesta> respuestasSeguridad;


    /**
     * Constructor completo para crear una nueva instancia de Usuario.
     * Valida la cédula y la contraseña durante la creación.
     *
     * @param username El nombre de usuario (cédula).
     * @param rol El rol del usuario.
     * @param password La contraseña del usuario.
     * @param nombreCompleto El nombre completo del usuario.
     * @param edad La edad del usuario.
     * @param genero El género del usuario.
     * @param telefono El teléfono del usuario.
     * @param email El correo electrónico del usuario.
     * @throws CedulaValidatorException Si el username no es una cédula válida.
     * @throws ContraseñaValidatorException Si la contraseña no cumple con los requisitos de seguridad.
     */
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

    /**
     * Constructor para crear un usuario sin un nombre de usuario o contraseña inicial.
     * Estos campos deberán ser establecidos posteriormente usando los setters correspondientes.
     *
     * @param rol El rol del usuario.
     * @param nombreCompleto El nombre completo del usuario.
     * @param edad La edad del usuario.
     * @param genero El género del usuario.
     * @param telefono El teléfono del usuario.
     * @param email El correo electrónico del usuario.
     * @throws CedulaValidatorException (Potencialmente desde setters futuros)
     * @throws ContraseñaValidatorException (Potencialmente desde setters futuros)
     */
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


    /**
     * Obtiene el nombre completo del usuario.
     * @return El nombre completo.
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Establece el nombre completo del usuario.
     * @param nombreCompleto El nuevo nombre completo.
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Obtiene la edad del usuario.
     * @return La edad.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establece la edad del usuario.
     * @param edad La nueva edad.
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Obtiene el género del usuario.
     * @return El género.
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * Establece el género del usuario.
     * @param genero El nuevo género.
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * Obtiene el teléfono del usuario.
     * @return El número de teléfono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del usuario.
     * @param telefono El nuevo número de teléfono.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el email del usuario.
     * @return La dirección de email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     * @param email La nueva dirección de email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el nombre de usuario (cédula).
     * @return El nombre de usuario.
     */
    public String    getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario y valida que sea una cédula correcta.
     * @param username El nuevo nombre de usuario (cédula).
     * @throws CedulaValidatorException Si la cédula no es válida.
     */
    public void setUsername(String username) throws CedulaValidatorException {
        if(!validarCedula(username)) {
            throw new CedulaValidatorException("");
        }
        this.username = username;
    }

    /**
     * Obtiene el rol del usuario.
     * @return El rol.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * @param rol El nuevo rol.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return La contraseña.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña y valida que cumpla con los requisitos de seguridad.
     * @param password La nueva contraseña.
     * @throws ContraseñaValidatorException Si la contraseña no es segura.
     */
    public void setPassword(String password) throws ContraseñaValidatorException {
        if(!validarContraseña(password)) {
            throw new ContraseñaValidatorException("");
        }
        this.password = password;
    }


    /**
     * Obtiene la lista de respuestas de seguridad del usuario.
     * @return Una lista de objetos {@link Respuesta}.
     */
    public List<Respuesta> getRespuestasSeguridad() {
        return respuestasSeguridad;
    }

    /**
     * Establece la lista de respuestas de seguridad del usuario.
     * @param respuestasSeguridad La nueva lista de respuestas.
     */
    public void setRespuestasSeguridad(List<Respuesta> respuestasSeguridad) {
        this.respuestasSeguridad = respuestasSeguridad;
    }


    /**
     * Agrega una nueva respuesta de seguridad a la lista del usuario.
     * @param pregunta La {@link Pregunta} a la que se responde.
     * @param respuestaTexto El texto de la respuesta.
     */
    public void addRespuesta(Pregunta pregunta, String respuestaTexto) {
        if (this.respuestasSeguridad == null) {
            this.respuestasSeguridad = new ArrayList<>();
        }
        this.respuestasSeguridad.add(new Respuesta(pregunta, respuestaTexto));
    }

    /**
     * Valida si una cadena de texto corresponde a una cédula ecuatoriana válida.
     * Implementa el algoritmo de validación del Módulo 10.
     * @param cedula La cédula de 10 dígitos a validar.
     * @return {@code true} si la cédula es válida, {@code false} en caso contrario.
     */
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

    /**
     * Valida si una contraseña cumple con los requisitos de seguridad del sistema.
     * Requisitos: mínimo 6 caracteres, una mayúscula, una minúscula y un símbolo ('@', '_', '-').
     * @param contrasena La contraseña a validar.
     * @return {@code true} si la contraseña es válida, {@code false} en caso contrario.
     */
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


    /**
     * Devuelve el número de preguntas de seguridad que el usuario ha respondido.
     * @return El tamaño de la lista de respuestas de seguridad.
     */
    public int preguntasAsignadas(){
        return this.respuestasSeguridad.size();
    }


    /**
     * Devuelve una representación en cadena del objeto Usuario.
     * Incluye username, rol, contraseña y las respuestas de seguridad.
     * @return Una cadena formateada con los detalles del usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", rol=" + rol +
                ", password='" + password + '\'' +
                ", respuestasSeguridad=" + respuestasSeguridad +
                '}';
    }

    /**
     * Compara este objeto Usuario con otro para ver si son iguales.
     * Dos usuarios se consideran iguales si sus nombres de usuario (username) son idénticos.
     * @param o El objeto a comparar.
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(username, usuario.username);
    }

    /**
     * Genera un código hash para el objeto Usuario.
     * El código hash se basa únicamente en el nombre de usuario (username).
     * @return El código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}