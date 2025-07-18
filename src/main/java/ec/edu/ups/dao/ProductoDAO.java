package ec.edu.ups.dao;

import ec.edu.ups.modelo.Producto;

import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia de datos (DAO - Data Access Object)
 * para la entidad {@link Producto}.
 * <p>
 * Esta interfaz abstrae los detalles de implementación del almacenamiento de datos,
 * permitiendo que los productos se puedan guardar y recuperar
 * de diferentes fuentes (memoria, archivos, bases de datos) de manera intercambiable.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public interface ProductoDAO {

    /**
     * Persiste un nuevo producto en la fuente de datos.
     *
     * @param producto El objeto {@link Producto} a ser creado.
     */
    void crear(Producto producto);

    /**
     * Busca y recupera un producto por su código único.
     *
     * @param codigo El código del producto a buscar.
     * @return El objeto {@link Producto} encontrado, o {@code null} si no existe ninguno con ese código.
     */
    Producto buscarPorCodigo(int codigo);

    /**
     * Busca y recupera todos los productos cuyo nombre coincida (generalmente ignorando mayúsculas/minúsculas)
     * con el nombre proporcionado.
     *
     * @param nombre El nombre a buscar.
     * @return Una {@link List} de objetos {@link Producto} que coinciden con el nombre.
     */
    List<Producto> buscarPorNombre(String nombre);

    /**
     * Actualiza un producto existente en la fuente de datos.
     * La búsqueda del producto a actualizar se realiza generalmente por su código.
     *
     * @param producto El objeto {@link Producto} con los datos actualizados.
     */
    void actualizar(Producto producto);

    /**
     * Elimina un producto de la fuente de datos utilizando su código.
     *
     * @param codigo El código único del producto a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Recupera una lista de todos los productos almacenados en la fuente de datos.
     *
     * @return Una {@link List} de todos los objetos {@link Producto}.
     */
    List<Producto> listarTodos();

}