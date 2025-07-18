package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia de datos (DAO - Data Access Object)
 * para la entidad {@link Carrito}.
 * <p>
 * Esta interfaz abstrae los detalles de implementación del almacenamiento de datos,
 * permitiendo que los carritos de compra se puedan guardar y recuperar
 * de diferentes fuentes (memoria, archivos, bases de datos) de manera intercambiable.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public interface CarritoDAO {
    /**
     * Persiste un nuevo carrito de compras en la fuente de datos.
     *
     * @param carrito El objeto {@link Carrito} a ser creado.
     */
    void crear(Carrito carrito);

    /**
     * Actualiza un carrito de compras existente en la fuente de datos.
     * La búsqueda del carrito a actualizar se realiza generalmente por su código.
     *
     * @param carrito El objeto {@link Carrito} con los datos actualizados.
     */
    void actualizar(Carrito carrito);

    /**
     * Elimina un carrito de compras de la fuente de datos utilizando su código.
     *
     * @param codigo El código único del carrito a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Busca y recupera un carrito de compras por su código único.
     *
     * @param codigo El código del carrito a buscar.
     * @return El objeto {@link Carrito} encontrado, o {@code null} si no existe ninguno con ese código.
     */
    Carrito buscarPorCodigo(int codigo);

    /**
     * Recupera una lista de todos los carritos de compras almacenados en la fuente de datos.
     *
     * @return Una {@link List} de todos los objetos {@link Carrito}.
     */
    List<Carrito> listarTodos();

    /**
     * Busca y recupera todos los carritos de compras que pertenecen a un usuario específico.
     *
     * @param usuario El objeto {@link Usuario} cuyos carritos se desean obtener.
     * @return Una lista de todos los carritos asociados al usuario proporcionado.
     */
    List<Carrito> buscarPorUsuario(Usuario usuario);

    /**
     * Busca un carrito por su código, pero solo lo devuelve si también pertenece al usuario especificado.
     * Es una operación de búsqueda combinada para verificar la propiedad del carrito.
     *
     * @param codigo  El código del carrito a buscar.
     * @param usuario El usuario que debe ser el propietario del carrito.
     * @return El objeto {@link Carrito} si se encuentra y pertenece al usuario, de lo contrario {@code null}.
     */
    Carrito buscarPorCodigoYUsuario(int codigo, Usuario usuario);
}