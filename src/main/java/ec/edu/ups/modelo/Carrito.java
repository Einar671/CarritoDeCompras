package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Representa un carrito de compras.
 * <p>
 * Esta clase gestiona una colección de productos ({@link ItemCarrito}) que un
 * {@link Usuario} ha seleccionado. Permite agregar, eliminar y actualizar productos,
 * así como calcular los costos totales de la compra.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class Carrito {

    /**
     * El código único que identifica al carrito de compras.
     */
    private int codigo;
    /**
     * Un contador estático para generar códigos únicos y autoincrementales para nuevos carritos.
     */
    private static int contador = 1;
    /**
     * La fecha y hora en que se creó el carrito.
     */
    private GregorianCalendar fecha;
    /**
     * La lista de ítems (productos y sus cantidades) que contiene el carrito.
     */
    private final List<ItemCarrito> items;
    /**
     * El usuario propietario de este carrito de compras.
     */
    private Usuario usuario;

    /**
     * Devuelve una representación en cadena del objeto Carrito.
     * <p>
     * Incluye información clave como el código, la fecha, el número de items,
     * subtotal, total, IVA y el nombre de usuario del propietario.
     *
     * @return Una cadena formateada con los detalles del carrito.
     */
    @Override
    public String toString() {
        return "Carrito{" +
                "codigo=" + codigo +
                ", fecha=" + (fecha != null ? fecha.getTime() : "N/A") +
                ", items=" + items.size() + " items" +
                ", subtotal=" + String.format("%.2f", calcularSubtotal()) +
                ", total=" + String.format("%.2f", calcularTotal()) +
                ", IVA=" + String.format("%.2f", calcularIVA()) +
                ", usuario=" + (usuario != null ? usuario.getUsername() : "N/A") +
                '}';
    }

    /**
     * Constructor por defecto.
     * <p>
     * Inicializa un nuevo carrito vacío, le asigna un código único y autoincremental,
     * y establece la fecha de creación a la fecha y hora actuales.
     */
    public Carrito() {
        this.items = new ArrayList<>();
        this.codigo = contador++;
        this.fecha = new GregorianCalendar();
    }


    /**
     * Constructor para crear un Carrito con valores específicos.
     *
     * @param codigo El código para el nuevo carrito.
     * @param fecha La fecha de creación del carrito.
     * @param usuario El usuario propietario del carrito.
     */
    public Carrito(int codigo, GregorianCalendar fecha, Usuario usuario) {
        this.items = new ArrayList<>();
        this.codigo = codigo;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    /**
     * Obtiene el código del carrito.
     *
     * @return El código único del carrito.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del carrito.
     *
     * @param codigo El nuevo código para el carrito.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la fecha de creación del carrito.
     *
     * @return Un objeto {@link GregorianCalendar} con la fecha de creación.
     */
    public GregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del carrito.
     *
     * @param fecha La nueva fecha para el carrito.
     */
    public void setFecha(GregorianCalendar fecha) {

        this.fecha = fecha;
    }

    /**
     * Agrega un producto al carrito.
     * <p>
     * Si el producto ya existe en el carrito, simplemente actualiza su cantidad.
     * Si es un producto nuevo, crea un nuevo {@link ItemCarrito} y lo añade a la lista.
     *
     * @param producto El {@link Producto} a agregar.
     * @param cantidad La cantidad del producto que se va a agregar.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return; // Producto ya agregado, cantidad actualizada
            }
        }
        ItemCarrito item = new ItemCarrito(producto, cantidad);
        items.add(item);
    }

    /**
     * Elimina un producto del carrito por su código.
     *
     * @param codigoProducto El código del producto a eliminar.
     */
    public void eliminarProducto(int codigoProducto) {
        items.removeIf(item -> item.getProducto().getCodigo() == codigoProducto);
    }

    /**
     * Actualiza la cantidad de un producto específico en el carrito.
     * <p>
     * Si la nueva cantidad es menor o igual a cero, el producto se elimina del carrito.
     *
     * @param codigoProducto El código del producto cuya cantidad se va a actualizar.
     * @param nuevaCantidad La nueva cantidad para el producto.
     */
    public void actualizarCantidadProducto(int codigoProducto, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            eliminarProducto(codigoProducto);
            return;
        }
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                item.setCantidad(nuevaCantidad);
                return;
            }
        }
    }

    /**
     * Calcula el subtotal de la compra (la suma de los precios de todos los ítems sin impuestos).
     *
     * @return El valor del subtotal como un {@code double}.
     */
    public double calcularSubtotal() {
        double currentSubtotal = 0;
        for (ItemCarrito item : items) {
            currentSubtotal += item.getSubtotal();
        }
        return currentSubtotal;
    }

    /**
     * Calcula el monto del IVA (Impuesto al Valor Agregado) sobre el subtotal.
     *
     * @return El valor del IVA como un {@code double}.
     */
    public double calcularIVA() {
        return calcularSubtotal() * 0.12;
    }

    /**
     * Calcula el costo total de la compra (subtotal + IVA).
     *
     * @return El valor total a pagar como un {@code double}.
     */
    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    /**
     * Obtiene una copia de la lista de ítems del carrito.
     * <p>
     * Se devuelve una copia para proteger la lista interna de modificaciones externas no deseadas.
     *
     * @return Una nueva lista con todos los {@link ItemCarrito} del carrito.
     */
    public List<ItemCarrito> obtenerItems() {
        return new ArrayList<>(items);
    }

    /**
     * Obtiene el usuario propietario del carrito.
     *
     * @return El {@link Usuario} asociado a este carrito.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asigna o cambia el usuario propietario del carrito.
     *
     * @param usuario El nuevo {@link Usuario} propietario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}