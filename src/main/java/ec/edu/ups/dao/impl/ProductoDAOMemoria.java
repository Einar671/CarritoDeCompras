package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de la interfaz ProductoDAO que utiliza una lista en memoria
 * para la persistencia de los datos de los productos.
 * <p>
 * Los datos se pierden cuando la aplicación se cierra. Esta implementación es útil
 * para pruebas o para tener datos iniciales sin necesidad de una base de datos o archivos.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class ProductoDAOMemoria implements ProductoDAO {

    /**
     * Lista en memoria que almacena todos los objetos Producto.
     */
    private List<Producto> productos;

    /**
     * Constructor para ProductoDAOMemoria.
     * <p>
     * Inicializa la lista de productos y la puebla con dos productos por defecto
     * ("Arroz" y "Platano") para que la aplicación tenga datos disponibles desde el inicio.
     */
    public ProductoDAOMemoria() {
        productos = new ArrayList<Producto>();
        crear(new Producto(1,"Arroz",15));
        crear(new Producto(2,"Platano",12));
    }

    /**
     * Agrega un nuevo producto a la lista en memoria.
     *
     * @param producto El objeto {@link Producto} a ser creado y almacenado.
     */
    @Override
    public void crear(Producto producto) {
        productos.add(producto);
    }

    /**
     * Busca un producto específico en la lista por su código.
     *
     * @param codigo El código del producto a buscar.
     * @return El objeto {@link Producto} encontrado, o {@code null} si no existe un producto con ese código.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    /**
     * Busca productos en la lista cuyo nombre coincida (ignorando mayúsculas y minúsculas)
     * con el nombre proporcionado.
     *
     * @param nombre El nombre del producto a buscar.
     * @return Una lista de objetos {@link Producto} que coinciden con el criterio de búsqueda.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    /**
     * Actualiza un producto existente en la lista.
     * Busca el producto por su código y lo reemplaza con la nueva instancia proporcionada.
     *
     * @param producto El objeto {@link Producto} con los datos actualizados.
     */
    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
            }
        }
    }

    /**
     * Elimina un producto de la lista basándose en su código.
     *
     * @param codigo El código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    /**
     * Devuelve la lista de todos los productos almacenados en memoria.
     * <p>
     * <b>Nota:</b> Este método devuelve la referencia directa a la lista interna,
     * por lo que las modificaciones a la lista devuelta afectarán al DAO.
     *
     * @return La lista de todos los productos.
     */
    @Override
    public List<Producto> listarTodos() {
        return productos;
    }
}