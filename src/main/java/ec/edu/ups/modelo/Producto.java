package ec.edu.ups.modelo;

/**
 * Representa un producto que puede ser vendido en la tienda.
 * <p>
 * Esta clase contiene la información básica de un producto, como su código único,
 * nombre y precio. Es una entidad fundamental en el sistema de carrito de compras.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class Producto {
    /**
     * El código único que identifica al producto.
     */
    private int codigo;
    /**
     * El nombre descriptivo del producto.
     */
    private String nombre;
    /**
     * El precio de venta unitario del producto.
     */
    private double precio;

    /**
     * Constructor por defecto.
     * Crea una instancia de Producto sin inicializar sus campos.
     */
    public Producto() {
    }

    /**
     * Constructor para crear un Producto con todos sus atributos.
     *
     * @param codigo El código único del producto.
     * @param nombre El nombre del producto.
     * @param precio El precio del producto.
     */
    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Establece el código del producto.
     *
     * @param codigo El nuevo código para el producto.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre El nuevo nombre para el producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio El nuevo precio para el producto.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el código del producto.
     *
     * @return El código del producto.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return El precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Devuelve una representación en cadena del objeto Producto.
     * <p>
     * El formato es "nombre - $precio".
     *
     * @return Una cadena formateada con el nombre y el precio del producto.
     */
    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }

}