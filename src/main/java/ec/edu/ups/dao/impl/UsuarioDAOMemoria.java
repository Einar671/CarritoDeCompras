package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Genero;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.CedulaValidatorException;
import ec.edu.ups.util.ContraseñaValidatorException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de la interfaz UsuarioDAO que utiliza una lista en memoria
 * para la persistencia de datos de los usuarios.
 * <p>
 * Los datos se pierden cuando la aplicación se cierra. Esta implementación es útil
 * para pruebas o demostraciones rápidas.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class UsuarioDAOMemoria implements UsuarioDAO {

    /**
     * Lista en memoria que almacena todos los objetos Usuario.
     */
    private final List<Usuario> usuarios;

    /**
     * Constructor para UsuarioDAOMemoria.
     * <p>
     * Inicializa la lista de usuarios y la puebla con dos usuarios por defecto:
     * un administrador y un usuario estándar, para asegurar que la aplicación
     * tenga datos con los que operar desde el inicio.
     */
    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<>();
        try {
            // Se crean usuarios por defecto con datos que cumplen las validaciones.
            crear(new Usuario("0302581863", Rol.ADMINISTRADOR, "Admin@123", "Administrador Principal", 30, Genero.OTRO, "0999999999", "admin@example.com"));
            crear(new Usuario("0150204212", Rol.USUARIO, "User_456", "Usuario de Prueba", 25, Genero.MASCULINO, "0888888888", "user@example.com"));
        } catch (CedulaValidatorException | ContraseñaValidatorException e) {
            System.err.println("Error crítico al crear usuarios por defecto: " + e.getMessage());
        }
    }

    /**
     * Autentica a un usuario verificando su nombre de usuario y contraseña contra
     * la lista en memoria.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña a verificar.
     * @return El objeto {@link Usuario} si la autenticación es exitosa, de lo contrario {@code null}.
     */
    @Override
    public Usuario autenticar(String username, String password) {
        for (Usuario usuario : usuarios) {
            if (username.equals(usuario.getUsername()) && password.equals(usuario.getPassword())) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Agrega un nuevo usuario a la lista en memoria.
     *
     * @param usuario El objeto {@link Usuario} a ser creado.
     */
    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    /**
     * Busca un usuario específico en la lista por su nombre de usuario (username).
     *
     * @param username El nombre de usuario a buscar.
     * @return El objeto {@link Usuario} encontrado, o {@code null} si no existe.
     */
    @Override
    public Usuario buscarPorUsuario(String username) {
        for (Usuario usuario : usuarios) {
            // Comprobación segura para evitar el error original
            if (usuario.getUsername() != null && usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Elimina un usuario de la lista basándose en su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario a eliminar.
     */
    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername() != null && usuario.getUsername().equals(username)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Actualiza un usuario existente en la lista.
     * Busca el usuario por su username y lo reemplaza con la nueva instancia.
     *
     * @param usuarioActualizado El objeto {@link Usuario} con los datos actualizados.
     */
    @Override
    public void actualizar(Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuarioExistente = usuarios.get(i);
            if (usuarioExistente.getUsername() != null && usuarioExistente.getUsername().equals(usuarioActualizado.getUsername())) {
                usuarios.set(i, usuarioActualizado);
                break;
            }
        }
    }

    /**
     * Devuelve una lista de todos los usuarios con el rol de ADMINISTRADOR.
     *
     * @return Una lista de administradores.
     */
    @Override
    public List<Usuario> listarAdministradores() {
        List<Usuario> admins = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                admins.add(usuario);
            }
        }
        return admins;
    }

    /**
     * Devuelve una lista de todos los usuarios con el rol de USUARIO.
     *
     * @return Una lista de usuarios estándar.
     */
    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> users = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() == Rol.USUARIO) {
                users.add(usuario);
            }
        }
        return users;
    }

    /**
     * Filtra la lista de todos los usuarios para devolver solo aquellos que tienen un rol específico.
     *
     * @param rol El {@link Rol} por el cual filtrar.
     * @return Una lista de usuarios que coinciden con el rol especificado.
     */
    @Override
    public List<Usuario> listarRol(Rol rol) {
        List<Usuario> usuariosRol = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() == rol) {
                usuariosRol.add(usuario);
            }
        }
        return usuariosRol;
    }

    /**
     * Devuelve una copia de la lista de todos los usuarios almacenados en memoria.
     *
     * @return Una nueva lista conteniendo todos los usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }
}