package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Vista (JInternalFrame) para que un administrador liste todos los carritos de compras del sistema.
 * <p>
 * Esta clase proporciona una interfaz gráfica que muestra una lista de todos los carritos
 * de todos los usuarios. Al seleccionar un carrito de la lista,
 * se muestran sus detalles (los ítems que contiene) en una segunda tabla.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class CarritoListarView extends JInternalFrame {
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Tabla que muestra la lista de todos los carritos del sistema.
     */
    private JTable tblCarritos;
    /**
     * Botón para actualizar o volver a cargar la lista de carritos.
     */
    private JButton btnListar;
    /**
     * Tabla que muestra los detalles (ítems) del carrito seleccionado.
     */
    private JTable tblDetalles;
    /**
     * Modelo de datos para la tabla {@code tblCarritos}.
     */
    private DefaultTableModel modelo;
    /**
     * Modelo de datos para la tabla {@code tblDetalles}.
     */
    private DefaultTableModel modeloDetalles;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;
    /**
     * Etiqueta para la tabla de detalles del carrito.
     */
    private JLabel lblDetalles;
    /**
     * Etiqueta secundaria, también para la sección de detalles del carrito.
     */
    private JLabel lblDetallesCar;
    /**
     * La lista de carritos que se muestra actualmente en la tabla principal.
     */
    private List<Carrito> listaActual;
    /**
     * El objeto Carrito que está actualmente seleccionado en la tabla de carritos.
     */
    private Carrito carritoActual;
    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private MensajeInternacionalizacionHandler mensajes;
    /**
     * El locale actual, utilizado para formatear fechas y monedas.
     */
    private Locale locale;

    /**
     * Constructor de la vista CarritoListarView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga el icono
     * para el botón, inicializa los modelos de las tablas y llama al método
     * para actualizar los textos según el idioma.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public CarritoListarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        setContentPane(panelPrincipal);

        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        URL urlListar = getClass().getResource("/list.png");

        modelo = new DefaultTableModel();
        tblCarritos.setModel(modelo);

        btnListar.setIcon(new ImageIcon(urlListar));

        modeloDetalles = new DefaultTableModel();
        tblDetalles.setModel(modeloDetalles);

        actualizarTextos();
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, el texto del botón y
     * los encabezados de ambas tablas según el {@code Locale} configurado en el manejador de mensajes.
     * Al final, vuelve a mostrar los datos en las tablas para que los formatos se actualicen.
     */
    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));
        lblDetallesCar.setText(mensajes.get("global.detalles"));
        setTitle(mensajes.get("carrito.listar.titulo.app"));
        lblTitulo.setText(mensajes.get("carrito.listar.titulo.app"));
        lblDetalles.setText(mensajes.get("global.detalles"));


        btnListar.setText(mensajes.get("menu.carrito.listar"));

        Object[] columnas = {
                mensajes.get("global.codigo"),
                mensajes.get("global.usuario"),
                mensajes.get("global.fecha"),
                mensajes.get("global.item"),
                mensajes.get("global.subtotal"),
                mensajes.get("global.IVA"),
                mensajes.get("global.total")
        };
        modelo.setColumnIdentifiers(columnas);

        Object[] columnasDetalles = {
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.subtotal")
        };
        modeloDetalles.setColumnIdentifiers(columnasDetalles);
        mostrarDetallesCarrito(carritoActual);
        mostrarCarritos(listaActual);

    }

    /**
     * Muestra una lista de objetos {@link Carrito} en la tabla principal.
     * <p>
     * Limpia la tabla de carritos y la de detalles, y luego puebla la tabla de carritos
     * con la lista proporcionada. Los valores de fecha, usuario y moneda se formatean
     * según el {@code Locale} actual.
     *
     * @param carritos La lista de carritos a mostrar. Si es nula, las tablas se limpian.
     */
    public void mostrarCarritos(List<Carrito> carritos) {
        this.listaActual = carritos;
        modelo.setRowCount(0);
        if (carritos == null) {
            return;
        }
        limpiarTablaDetalles();
        for (Carrito carrito : carritos) {
            Object[] fila = {
                    carrito.getCodigo(),
                    carrito.getUsuario() != null ? carrito.getUsuario().getUsername() : "N/A",
                    carrito.getFecha() != null ? FormateadorUtils.formatearFecha(carrito.getFecha().getTime(), locale) : "N/A",
                    carrito.obtenerItems().size(),
                    FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale),
                    FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale),
                    FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale)
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Muestra los detalles (ítems) de un objeto {@link Carrito} en la tabla secundaria.
     * <p>
     * Limpia la tabla de detalles y la vuelve a poblar con los ítems del carrito proporcionado.
     * Los valores de moneda se formatean según el {@code Locale} actual.
     *
     * @param carrito El carrito cuyos detalles se van a mostrar. Si es nulo, la tabla se limpia.
     */
    public void mostrarDetallesCarrito(Carrito carrito) {
        this.carritoActual = carrito;
        limpiarTablaDetalles();
        if (carrito != null) {
            List<ItemCarrito> items = carrito.obtenerItems();
            for (ItemCarrito item : items) {
                Object[] fila = {
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                        item.getCantidad(),
                        FormateadorUtils.formatearMoneda(item.getSubtotal(), locale)
                };
                modeloDetalles.addRow(fila);
            }
        }
    }

    /**
     * Muestra los detalles (ítems) de un objeto {@link Carrito} en la tabla secundaria.
     * <p>
     * Limpia la tabla de detalles y la vuelve a poblar con los ítems del carrito proporcionado.
     * Los valores de moneda se formatean según el {@code Locale} actual.
     *
     * @param carrito El carrito cuyos detalles se van a mostrar. Si es nulo, la tabla se limpia.
     */
    public void mostrarDetalles(Carrito carrito) {
        this.carritoActual = carrito;
        modeloDetalles.setRowCount(0);

        if (carrito != null) {
            for (ItemCarrito item : carrito.obtenerItems()) {
                modeloDetalles.addRow(new Object[]{
                        item.getProducto().getNombre(),
                        item.getCantidad(),
                        FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                        item.getCantidad(),
                        FormateadorUtils.formatearMoneda(item.getSubtotal(), locale)
                });
            }
        }
    }

    /**
     * Limpia todas las filas de la tabla de detalles del carrito.
     */
    public void limpiarTablaDetalles() {
        modeloDetalles.setRowCount(0);
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje informativo.
     *
     * @param mensaje El texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Obtiene el botón de listar.
     *
     * @return El componente JButton para listar/actualizar.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Obtiene la tabla de carritos.
     *
     * @return El componente JTable que muestra la lista de carritos.
     */
    public JTable getTblCarritos() {
        return tblCarritos;
    }
}