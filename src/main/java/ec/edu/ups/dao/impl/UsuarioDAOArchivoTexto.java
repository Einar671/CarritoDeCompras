package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Genero;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.Respuesta;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.CedulaValidatorException;
import ec.edu.ups.util.ContraseñaValidatorException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de la interfaz UsuarioDAO que utiliza un archivo de texto plano
 * para la persistencia de datos de los usuarios.
 * <p>
 * Cada usuario se almacena como una línea en el archivo, con sus atributos
 * separados por un delimitador específico. Las respuestas de seguridad también se
 * serializan en la misma línea.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class UsuarioDAOArchivoTexto implements UsuarioDAO {
    /**
     * La ruta completa del archivo de texto donde se almacenan los datos de los usuarios.
     */
    private final String ruta;
    /**
     * Carácter utilizado para separar los campos principales de un usuario en una línea.
     */
    private static final String SEPARADOR_CAMPOS = "|";
    /**
     * Carácter utilizado para separar múltiples pares de pregunta-respuesta de seguridad.
     */
    private static final String SEPARADOR_PREGUNTAS = ";";
    /**
     * Carácter utilizado para separar el ID de una pregunta de su respuesta textual.
     */
    private static final String SEPARADOR_RESPUESTAS = ",";

    /**
     * Constructor para UsuarioDAOArchivoTexto.
     * <p>
     * Inicializa el DAO con la ruta al archivo de persistencia. Si el archivo no existe
     * o está vacío al momento de la creación, se crea y se puebla con usuarios
     * por defecto (un administrador y un usuario estándar).
     *
     * @param ruta La ruta del archivo donde se guardarán los datos.
     */
    public UsuarioDAOArchivoTexto(String ruta) {
        this.ruta = ruta;
        try {
            File archivo = new File(ruta);
            // Si el archivo no existe o está vacío, se crean los usuarios por defecto.
            if (!archivo.exists() || archivo.length() == 0) {
                archivo.createNewFile();
                // Se usan datos que cumplen las reglas de validación de Cédula y Contraseña.
                crear(new Usuario("0302581863", Rol.ADMINISTRADOR, "Admin@123", "Administrador Principal", 30, Genero.OTRO, "0999999999", "admin@example.com"));
                crear(new Usuario("0150363232", Rol.USUARIO, "User_456", "Usuario de Prueba", 25, Genero.MASCULINO, "0888888888", "user@example.com"));
            }
        } catch (IOException | CedulaValidatorException | ContraseñaValidatorException e) {
            System.err.println("Error al inicializar o crear usuarios por defecto en el archivo: " + e.getMessage());
        }
    }

    /**
     * Agrega un nuevo usuario al final del archivo de texto.
     *
     * @param usuario El objeto {@link Usuario} a ser creado y almacenado.
     */
    @Override
    public void crear(Usuario usuario) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, true))) {
            bw.write(usuarioToString(usuario));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al crear el usuario en el archivo: " + e.getMessage());
        }
    }

    /**
     * Busca un usuario específico en el archivo por su nombre de usuario (username).
     * Lee el archivo línea por línea hasta encontrar una que comience con el username buscado.
     *
     * @param username El nombre de usuario a buscar.
     * @return El objeto {@link Usuario} encontrado, o {@code null} si no existe.
     */
    @Override
    public Usuario buscarPorUsuario(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith(username + SEPARADOR_CAMPOS)) {
                    return stringToUsuario(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Autentica a un usuario verificando su nombre de usuario y contraseña contra
     * los datos almacenados en el archivo.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña a verificar.
     * @return El objeto {@link Usuario} si la autenticación es exitosa, de lo contrario {@code null}.
     */
    @Override
    public Usuario autenticar(String username, String password) {
        Usuario usuario = buscarPorUsuario(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }

    /**
     * Actualiza un usuario existente en el archivo.
     * Este método lee todos los usuarios, modifica el deseado en memoria y luego
     * sobrescribe completamente el archivo con la lista actualizada.
     *
     * @param usuarioActualizado El objeto {@link Usuario} con los datos actualizados.
     */
    @Override
    public void actualizar(Usuario usuarioActualizado) {
        List<Usuario> usuarios = listarTodos();
        boolean modificado = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuarioActualizado.getUsername())) {
                usuarios.set(i, usuarioActualizado);
                modificado = true;
                break;
            }
        }
        if (modificado) {
            sobrescribirArchivo(usuarios);
        }
    }

    /**
     * Elimina un usuario del archivo basándose en su nombre de usuario.
     * Lee todos los usuarios, elimina el deseado de la lista en memoria y
     * luego sobrescribe el archivo.
     *
     * @param username El nombre de usuario del usuario a eliminar.
     */
    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = listarTodos();
        // Reemplazo de removeIf con un iterador para ser más explícito.
        Iterator<Usuario> iterador = usuarios.iterator();
        boolean eliminado = false;
        while (iterador.hasNext()) {
            Usuario usuario = iterador.next();
            if (usuario.getUsername().equals(username)) {
                iterador.remove();
                eliminado = true;
                break; // Se asume que los usernames son únicos.
            }
        }

        if (eliminado) {
            sobrescribirArchivo(usuarios);
        }
    }

    /**
     * Lee todas las líneas del archivo y las convierte en una lista de objetos {@link Usuario}.
     *
     * @return Una lista de todos los usuarios almacenados en el archivo.
     */
    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Usuario usuario = stringToUsuario(linea);
                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            // No se imprime error si el archivo no se encuentra, es un caso normal.
            if (!(e instanceof FileNotFoundException)) {
                e.printStackTrace();
            }
        }
        return usuarios;
    }

    /**
     * Filtra la lista de todos los usuarios para devolver solo aquellos que tienen un rol específico.
     *
     * @param rol El {@link Rol} por el cual filtrar.
     * @return Una lista de usuarios que coinciden con el rol especificado.
     */
    @Override
    public List<Usuario> listarRol(Rol rol) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : listarTodos()) {
            if (usuario.getRol() == rol) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    /**
     * Devuelve una lista de todos los usuarios con el rol de ADMINISTRADOR.
     *
     * @return Una lista de administradores.
     */
    @Override
    public List<Usuario> listarAdministradores() {
        return listarRol(Rol.ADMINISTRADOR);
    }

    /**
     * Devuelve una lista de todos los usuarios con el rol de USUARIO.
     *
     * @return Una lista de usuarios estándar.
     */
    @Override
    public List<Usuario> listarUsuarios() {
        return listarRol(Rol.USUARIO);
    }


    /**
     * Escribe una lista completa de usuarios en el archivo, sobrescribiendo su contenido actual.
     *
     * @param usuarios La lista de usuarios a escribir en el archivo.
     */
    private void sobrescribirArchivo(List<Usuario> usuarios) {
        // Se usa 'false' para sobrescribir completamente el archivo.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, false))) {
            for (Usuario u : usuarios) {
                bw.write(usuarioToString(u));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Convierte un objeto {@link Usuario} en una cadena de texto formateada para ser guardada en el archivo.
     *
     * @param usuario El objeto usuario a convertir.
     * @return Una cadena de texto con los datos del usuario separados por delimitadores.
     */
    private String usuarioToString(Usuario usuario) {
        StringBuilder sb = new StringBuilder();
        sb.append(usuario.getUsername()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getRol()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getPassword()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getNombreCompleto()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getEdad()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getGenero()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getTelefono()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getEmail());

        if (usuario.getRespuestasSeguridad() != null && !usuario.getRespuestasSeguridad().isEmpty()) {
            sb.append(SEPARADOR_CAMPOS);

            List<String> respuestasFormateadas = new ArrayList<>();
            for (Respuesta r : usuario.getRespuestasSeguridad()) {
                // Se asume que la clase Respuesta tiene el método getTextoRespuesta()
                // para mantener consistencia con el resto del proyecto.
                respuestasFormateadas.add(r.getPregunta().getId() + SEPARADOR_RESPUESTAS + r.getRespuesta());
            }
            sb.append(String.join(SEPARADOR_PREGUNTAS, respuestasFormateadas));
        }
        return sb.toString();
    }


    /**
     * Convierte una cadena de texto (una línea del archivo) en un objeto {@link Usuario}.
     *
     * @param linea La línea de texto a parsear.
     * @return Un objeto {@link Usuario} reconstruido a partir de la línea, o {@code null} si la línea es inválida.
     */
    private Usuario stringToUsuario(String linea) {
        String[] parts = linea.split("\\" + SEPARADOR_CAMPOS, -1);
        if (parts.length < 8) return null;

        try {
            String username = parts[0];
            Rol rol = Rol.valueOf(parts[1]);
            String password = parts[2];
            String nombreCompleto = parts[3];
            int edad = Integer.parseInt(parts[4]);
            Genero genero = Genero.valueOf(parts[5]);
            String telefono = parts[6];
            String email = parts[7];

            Usuario usuario = new Usuario(username, rol, password, nombreCompleto, edad, genero, telefono, email);

            if (parts.length > 8 && !parts[8].isEmpty()) {
                String[] preguntasRespuestas = parts[8].split(SEPARADOR_PREGUNTAS);
                for (String pr : preguntasRespuestas) {
                    String[] prParts = pr.split(SEPARADOR_RESPUESTAS);
                    if (prParts.length == 2) {
                        int preguntaId = Integer.parseInt(prParts[0]);
                        String textoRespuesta = prParts[1];
                        // El texto de la pregunta no se guarda, se recupera desde los archivos de mensajes.
                        Pregunta pregunta = new Pregunta(preguntaId, "");
                        usuario.addRespuesta(pregunta, textoRespuesta);
                    }
                }
            }
            return usuario;
        } catch (IllegalArgumentException | CedulaValidatorException | ContraseñaValidatorException e) {
            System.err.println("Error al parsear la línea de usuario o datos inválidos: " + linea);
            e.printStackTrace();
            return null;
        }
    }
}