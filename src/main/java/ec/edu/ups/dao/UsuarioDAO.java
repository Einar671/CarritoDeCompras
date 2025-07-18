package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia de datos (DAO - Data Access Object)
 * para la entidad {@link Usuario}.
 * <p>
 * Esta interfaz abstrae los detalles de implementación del almacenamiento de datos,
 * permitiendo que los usuarios se puedan guardar y recuperar
 * de diferentes fuentes (memoria, archivos, bases de datos) de manera intercambiable.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public interface UsuarioDAO {
    /**
     * Autentica a un usuario verificando su nombre de usuario y contraseña.
     *
     * @param username El nombre de usuario a verificar.
     * @param password La contraseña a verificar.
     * @return El objeto {@link Usuario} si la autenticación es exitosa, de lo contrario {@code null}.
     */
    Usuario autenticar(String username, String password);

    /**
     * Persiste un nuevo usuario en la fuente de datos.
     *
     * @param usuario El objeto {@link Usuario} a ser creado.
     */
    void crear(Usuario usuario);

    /**
     * Busca y recupera un usuario por su nombre de usuario (username).
     *
     * @param username El nombre de usuario a buscar.
     * @return El objeto {@link Usuario} encontrado, o {@code null} si no existe ninguno con ese username.
     */
    Usuario buscarPorUsuario(String username);

    /**
     * Elimina un usuario de la fuente de datos utilizando su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario a eliminar.
     */
    void eliminar(String username);

    /**
     * Actualiza un usuario existente en la fuente de datos.
     * La búsqueda del usuario a actualizar se realiza por su nombre de usuario.
     *
     * @param usuario El objeto {@link Usuario} con los datos actualizados.
     */
    void actualizar(Usuario usuario);

    /**
     * Recupera una lista de todos los usuarios que tienen el rol de Administrador.
     *
     * @return Una {@link List} de objetos {@link Usuario} con rol de {@link Rol#ADMINISTRADOR}.
     */
    List<Usuario> listarAdministradores();

    /**
     * Recupera una lista de todos los usuarios que tienen el rol de Usuario estándar.
     *
     * @return Una {@link List} de objetos {@link Usuario} con rol de {@link Rol#USUARIO}.
     */
    List<Usuario> listarUsuarios();

    /**
     * Recupera una lista de todos los usuarios que pertenecen a un rol específico.
     *
     * @param rol El rol por el cual filtrar la lista de usuarios.
     * @return Una {@link List} de objetos {@link Usuario} que coinciden con el rol especificado.
     */
    List<Usuario> listarRol(Rol rol);

    /**
     * Recupera una lista de todos los usuarios almacenados en la fuente de datos.
     *
     * @return Una {@link List} de todos los objetos {@link Usuario}.
     */
    List<Usuario> listarTodos();
}