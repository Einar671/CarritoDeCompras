package ec.edu.ups.modelo;

/**
 * Representa un único ítem dentro de un {@link Carrito} de compras.
 * <p>
 * Esta clase asocia un {@link Producto} específico con una cantidad determinada,
 * permitiendo calcular el subtotal para esa línea de producto.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class ItemCarrito {
    /**
     * El producto que se está comprando.
     */
    private Producto producto;
    /**
     * La cantidad de unidades del producto.
     */
    private int cantidad;

    /**
     * Constructor por defecto.
     * Crea una instancia de ItemCarrito sin inicializar sus campos.
     */
    public ItemCarrito() {
    }

    /**
     * Constructor para crear un ItemCarrito con un producto y una cantidad inicial.
     *
     * @param producto El {@link Producto} para este ítem.
     * @param cantidad La cantidad de unidades de dicho producto.
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    /**
     * Establece o cambia el producto de este ítem.
     *
     * @param producto El nuevo {@link Producto} para el ítem.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Establece o cambia la cantidad de este ítem.
     *
     * @param cantidad La nueva cantidad de unidades.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el producto asociado a este ítem.
     *
     * @return El {@link Producto} de este ítem.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Obtiene la cantidad de unidades de este ítem.
     *
     * @return La cantidad como un entero.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Calcula el subtotal para este ítem.
     * El cálculo es el resultado de multiplicar el precio del producto por la cantidad.
     *
     * @return El subtotal como un valor de tipo {@code double}.
     */
    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    /**
     * Devuelve una representación en cadena del objeto ItemCarrito.
     * Muestra el producto, la cantidad y el subtotal calculado.
     *
     * @return Una cadena formateada con los detalles del ítem.
     */
    @Override
    public String toString() {
        return producto.toString() + " x " + cantidad + " = $" + getSubtotal();
    }

}