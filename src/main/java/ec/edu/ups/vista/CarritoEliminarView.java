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
 * Vista (JInternalFrame) para buscar y eliminar un carrito de compras.
 * <p>
 * Esta clase proporciona la interfaz gráfica para que un usuario (generalmente un administrador)
 * pueda buscar un carrito por su código, ver sus detalles (usuario, fecha, ítems) y
 * proceder a su eliminación del sistema.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class CarritoEliminarView extends JInternalFrame {
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Campo de texto para ingresar el código del carrito a buscar.
     */
    private JTextField txtCodigo;
    /**
     * Botón para iniciar la búsqueda del carrito por su código.
     */
    private JButton btnBuscar;
    /**
     * Tabla que muestra los detalles (ítems) del carrito encontrado.
     */
    private JTable tblItems;
    /**
     * Campo de texto para mostrar el nombre de usuario del propietario del carrito.
     */
    private JTextField txtUsuario;
    /**
     * Campo de texto para mostrar la fecha de creación del carrito.
     */
    private JTextField txtFecha;
    /**
     * Botón para confirmar la eliminación del carrito mostrado.
     */
    private JButton btnEliminar;
    /**
     * Etiqueta para el campo de código.
     */
    private JLabel lblCodigo;
    /**
     * Etiqueta para el campo de usuario.
     */
    private JLabel lblUsuario;
    /**
     * Etiqueta para el campo de fecha.
     */
    private JLabel lblFecha;
    /**
     * Etiqueta para la tabla de ítems.
     */
    private JLabel lblItems;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;
    /**
     * Modelo de datos para la tabla {@code tblItems}.
     */
    private DefaultTableModel modeloDetalles;
    /**
     * El objeto Carrito que se ha encontrado y se está visualizando actualmente.
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
     * Constructor de la vista CarritoEliminarView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga los iconos
     * para los botones, inicializa el modelo de la tabla y llama al método
     * para actualizar los textos según el idioma.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public CarritoEliminarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        setContentPane(panelPrincipal);
        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));
        URL urlBuscar = getClass().getResource("/search.png");
        URL urlEliminar = getClass().getResource("/trash.png");
        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        btnEliminar.setIcon(new ImageIcon(urlEliminar));
        setSize(600, 400);


        modeloDetalles = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblItems.setModel(modeloDetalles);

        actualizarTextos();
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, los tooltips y los encabezados
     * de la tabla según el {@code Locale} configurado en el manejador de mensajes.
     * Al final, vuelve a mostrar los ítems del carrito para que los formatos de moneda se actualicen.
     */
    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        setTitle(mensajes.get("carrito.eliminar.titulo.app"));

        lblTitulo.setText(mensajes.get("carrito.eliminar.titulo.app"));
        lblCodigo.setText(mensajes.get("global.codigo"));
        lblUsuario.setText(mensajes.get("global.usuario"));
        lblFecha.setText(mensajes.get("global.fecha"));
        lblItems.setText(mensajes.get("global.item"));
        txtCodigo.setToolTipText(mensajes.get("carrito.top.codigo"));

        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnEliminar.setText(mensajes.get("global.boton.eliminar"));

        Object[] columnasDetalles = {
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.subtotal")
        };
        modeloDetalles.setColumnIdentifiers(columnasDetalles);
        mostrarItemsCarrito(carritoActual);
    }

    /**
     * Muestra los ítems de un objeto {@link Carrito} en la tabla de la interfaz.
     * <p>
     * Limpia la tabla y la vuelve a poblar con los ítems del carrito proporcionado.
     * Los valores numéricos como precios y subtotales se formatean según el {@code Locale} actual.
     *
     * @param carrito El carrito cuyos ítems se van a mostrar. Si es nulo, la tabla se limpia.
     */
    public void mostrarItemsCarrito(Carrito carrito) {
        this.carritoActual = carrito;
        modeloDetalles.setRowCount(0);

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
     * Muestra un cuadro de diálogo con un mensaje informativo.
     *
     * @param mensaje El texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Obtiene el campo de texto del código del carrito.
     * @return El componente JTextField para el código.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Obtiene el botón de buscar.
     * @return El componente JButton para buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Obtiene la tabla de ítems del carrito.
     * @return El componente JTable que muestra los ítems.
     */
    public JTable getTblItems() {
        return tblItems;
    }

    /**
     * Obtiene el campo de texto del usuario.
     * @return El componente JTextField para el usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Obtiene el campo de texto de la fecha.
     * @return El componente JTextField para la fecha.
     */
    public JTextField getTxtFecha() {
        return txtFecha;
    }

    /**
     * Obtiene el botón de eliminar.
     * @return El componente JButton para eliminar.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Obtiene el carrito que se está mostrando actualmente en la vista.
     * @return El objeto {@link Carrito} actual.
     */
    public Carrito getCarritoActual() {
        return carritoActual;
    }

}