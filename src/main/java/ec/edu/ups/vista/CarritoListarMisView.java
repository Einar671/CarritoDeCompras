package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Vista (JInternalFrame) para que un usuario liste sus propios carritos de compras.
 * <p>
 * Esta clase proporciona una interfaz gráfica que muestra una lista de todos los carritos
 * asociados al usuario actualmente autenticado. Al seleccionar un carrito de la lista,
 * se muestran sus detalles (los ítems que contiene) en una segunda tabla.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class CarritoListarMisView extends JInternalFrame {
    /**
     * Tabla que muestra la lista de carritos del usuario.
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
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;
    /**
     * Etiqueta para la tabla de la lista de carritos.
     */
    private JLabel lblListaCarrito;
    /**
     * Etiqueta para la tabla de detalles del carrito.
     */
    private JLabel lblDetalles;
    /**
     * El objeto Carrito que está actualmente seleccionado en la tabla de carritos.
     */
    private Carrito carritoActual;
    /**
     * La lista de carritos que se muestra actualmente en la tabla.
     */
    private List<Carrito> listaActual;

    /**
     * Modelo de datos para la tabla {@code tblCarritos}.
     */
    private DefaultTableModel modeloCarritos;
    /**
     * Modelo de datos para la tabla {@code tblDetalles}.
     */
    private DefaultTableModel modeloDetalles;

    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private MensajeInternacionalizacionHandler mensajes;
    /**
     * El locale actual, utilizado para formatear fechas y monedas.
     */
    private Locale locale;

    /**
     * Constructor de la vista CarritoListarMisView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga los iconos
     * para los botones, inicializa los modelos de las tablas y llama al método
     * para actualizar los textos según el idioma.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public CarritoListarMisView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        setContentPane(panelPrincipal);

        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        setSize(600, 450);

        URL urlListar = getClass().getResource("/list.png");

        modeloCarritos = new DefaultTableModel();
        tblCarritos.setModel(modeloCarritos);

        btnListar.setIcon(new ImageIcon(urlListar));

        modeloDetalles = new DefaultTableModel();
        tblDetalles.setModel(modeloDetalles);

        actualizarTextos();
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, el texto del botón y los encabezados
     * de ambas tablas según el {@code Locale} configurado en el manejador de mensajes.
     * Al final, vuelve a mostrar los datos en las tablas para que los formatos se actualicen.
     */
    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        setTitle(mensajes.get("menu.carrito.listarMis"));
        lblDetalles.setText(mensajes.get("global.detalles"));
        lblTitulo.setText(mensajes.get("menu.carrito.listarMis"));
        lblListaCarrito.setText(mensajes.get("menu.carrito.listar"));


        btnListar.setText(mensajes.get("menu.carrito.listarMis"));

        Object[] columnasCarritos = {
                mensajes.get("global.codigo"),
                mensajes.get("global.fecha"),
                mensajes.get("global.item"),
                mensajes.get("global.total")
        };
        modeloCarritos.setColumnIdentifiers(columnasCarritos);

        Object[] columnasDetalles = {
                mensajes.get("global.nombre"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.precio"),
                mensajes.get("global.subtotal")
        };
        modeloDetalles.setColumnIdentifiers(columnasDetalles);

        if (listaActual != null) {
            mostrarCarritos(listaActual);
        }
        if (carritoActual != null) {
            mostrarDetalles(carritoActual);
        }
    }

    /**
     * Muestra una lista de objetos {@link Carrito} en la tabla principal.
     * <p>
     * Limpia la tabla de carritos y la de detalles, y luego puebla la tabla de carritos
     * con la lista proporcionada. Los valores de fecha y moneda se formatean
     * según el {@code Locale} actual.
     *
     * @param carritos La lista de carritos a mostrar. Si es nula, las tablas se limpian.
     */
    public void mostrarCarritos(List<Carrito> carritos) {
        this.listaActual = carritos;
        modeloCarritos.setRowCount(0);
        modeloDetalles.setRowCount(0);
        if (carritos == null) {
            return;
        }
        for (Carrito carrito : carritos) {
            modeloCarritos.addRow(new Object[]{
                    carrito.getCodigo(),
                    FormateadorUtils.formatearFecha(carrito.getFecha().getTime(), locale),
                    carrito.obtenerItems().size(),
                    FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale)
            });
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
                        FormateadorUtils.formatearMoneda(item.getSubtotal(), locale)
                });
            }
        }
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
     * Obtiene la tabla de carritos.
     * @return El componente JTable que muestra la lista de carritos.
     */
    public JTable getTblCarritos() {
        return tblCarritos;
    }

    /**
     * Obtiene el botón de listar.
     * @return El componente JButton para listar/actualizar.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Obtiene la tabla de detalles del carrito.
     * @return El componente JTable que muestra los ítems del carrito seleccionado.
     */
    public JTable getTblDetalles() {
        return tblDetalles;
    }

    /**
     * Obtiene el panel principal de la vista.
     * @return El componente JPanel principal.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

}