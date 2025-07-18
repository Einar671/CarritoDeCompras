package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.net.URL;
import java.util.Locale;

/**
 * Vista (JInternalFrame) para crear y gestionar un carrito de compras.
 * <p>
 * Esta clase proporciona la interfaz gráfica para que el usuario pueda buscar productos,
 * añadirlos a un carrito, ver los ítems actuales en una tabla y observar los
 * totales calculados (subtotal, IVA, total).
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class CarritoAñadirView extends JInternalFrame {
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Panel secundario, posiblemente para agrupar campos de texto.
     */
    private JPanel txt;
    /**
     * Campo de texto para ingresar o mostrar el código de un producto.
     */
    private JTextField txtCodigo;
    /**
     * Campo de texto para mostrar el nombre del producto encontrado.
     */
    private JTextField txtNombre;
    /**
     * Campo de texto para mostrar el precio del producto encontrado.
     */
    private JTextField txtPrecio;
    /**
     * Campo de texto para mostrar el subtotal calculado del carrito.
     */
    private JTextField txtSubtotal;
    /**
     * Campo de texto para mostrar el IVA calculado del carrito.
     */
    private JTextField txtIVA;
    /**
     * Campo de texto para mostrar el total calculado del carrito.
     */
    private JTextField txtTotal;
    /**
     * Tabla que muestra los ítems (productos, cantidades, subtotales) del carrito.
     */
    private JTable tblItems;
    /**
     * Botón para guardar el estado actual del carrito.
     */
    private JButton btnGuardar;
    /**
     * Botón para buscar un producto por su código.
     */
    private JButton btnBuscar;
    /**
     * Botón para añadir el producto buscado al carrito.
     */
    private JButton btnAñadir;
    /**
     * Botón para limpiar los campos de búsqueda y selección.
     */
    private JButton btnLimpiar;
    /**
     * ComboBox para seleccionar la cantidad de un producto a añadir.
     */
    private JComboBox<String> cbxCantidad;
    /**
     * Etiqueta para el campo de código.
     */
    private JLabel lblCodigo;
    /**
     * Etiqueta para el campo de nombre.
     */
    private JLabel lblNombre;
    /**
     * Etiqueta para el campo de precio.
     */
    private JLabel lblPrecio;
    /**
     * Etiqueta para el ComboBox de cantidad.
     */
    private JLabel lblCantidad;
    /**
     * Etiqueta para el campo de subtotal.
     */
    private JLabel lblSubtotal;
    /**
     * Etiqueta para el campo de IVA.
     */
    private JLabel IVA;
    /**
     * Etiqueta para el campo de total.
     */
    private JLabel lblTotal;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;
    /**
     * Modelo de datos para la tabla {@code tblItems}.
     */
    private DefaultTableModel modelo;
    /**
     * El objeto Carrito que se está visualizando y modificando en esta vista.
     */
    private Carrito carritoActual;

    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private MensajeInternacionalizacionHandler mensajes;
    /**
     * El locale actual, utilizado para formatear números y monedas.
     */
    private Locale locale;

    /**
     * Constructor de la vista CarritoAñadirView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga los iconos
     * para los botones, inicializa el modelo de la tabla y llama a los métodos
     * para actualizar los textos según el idioma y cargar datos iniciales.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public CarritoAñadirView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));
        setContentPane(panelPrincipal);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 550);
        setClosable(true);
        setIconifiable(true);
        URL urlBuscar = getClass().getResource("/search.png");
        URL urlAñadir = getClass().getResource("/plus.png");
        URL urlGuardar = getClass().getResource("/check.png");
        URL urlLimpiar = getClass().getResource("/clean.png");
        modelo = new DefaultTableModel();
        tblItems.setModel(modelo);
        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        btnAñadir.setIcon(new ImageIcon(urlAñadir));
        btnGuardar.setIcon(new ImageIcon(urlGuardar));
        btnLimpiar.setIcon(new ImageIcon(urlLimpiar));
        actualizarTextos();
        cargarDatos();
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, los tooltips y los encabezados
     * de la tabla según el {@code Locale} configurado en el manejador de mensajes.
     * Al final, vuelve a mostrar el carrito para que los formatos de moneda se actualicen.
     */
    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        setTitle(mensajes.get("carrito.crear.titulo.app"));
        lblTitulo.setText(mensajes.get("carrito.crear.titulo"));
        lblCodigo.setText(mensajes.get("global.codigo"));
        lblNombre.setText(mensajes.get("global.nombre"));
        lblPrecio.setText(mensajes.get("global.precio"));
        lblCantidad.setText(mensajes.get("global.cantidad"));
        lblSubtotal.setText(mensajes.get("global.subtotal"));
        IVA.setText(mensajes.get("global.IVA"));
        lblTotal.setText(mensajes.get("global.total"));
        txtCodigo.setToolTipText(mensajes.get("producto.top.codigo"));
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnAñadir.setText(mensajes.get("global.añadir"));
        btnGuardar.setText(mensajes.get("global.boton.guardar"));
        btnLimpiar.setText(mensajes.get("global.boton.limpiar"));
        cbxCantidad.setToolTipText(mensajes.get("carrito.top.cantidad"));

        Object[] columnas = {
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.subtotal")
        };
        modelo.setColumnIdentifiers(columnas);
        mostrarCarrito(carritoActual);
    }

    /**
     * Carga los datos iniciales en los componentes, como el ComboBox de cantidad.
     */
    private void cargarDatos() {
        cbxCantidad.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(String.valueOf(i));
        }
    }


    /**
     * Muestra los datos de un objeto {@link Carrito} en la interfaz gráfica.
     * <p>
     * Limpia la tabla y la vuelve a poblar con los ítems del carrito proporcionado.
     * Actualiza los campos de subtotal, IVA y total con los valores calculados
     * y formateados según el {@code Locale} actual.
     *
     * @param carrito El carrito cuyos datos se van a mostrar. Si es nulo, se limpian los campos.
     */
    public void mostrarCarrito(Carrito carrito) {
        this.carritoActual = carrito;
        modelo.setRowCount(0);
        if (carrito != null) {
            for (ItemCarrito item : carrito.obtenerItems()) {
                modelo.addRow(new Object[]{
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                        item.getCantidad(),
                        FormateadorUtils.formatearMoneda(item.getSubtotal(), locale)
                });
            }
            txtSubtotal.setText(FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale));
            txtIVA.setText(FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale));
            txtTotal.setText(FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale));
        } else {
            txtSubtotal.setText("");
            txtIVA.setText("");
            txtTotal.setText("");
        }
    }

    /**
     * Obtiene el campo de texto del código del producto.
     * @return El componente JTextField para el código.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Obtiene el campo de texto del nombre del producto.
     * @return El componente JTextField para el nombre.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Obtiene el campo de texto del precio del producto.
     * @return El componente JTextField para el precio.
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Obtiene el campo de texto del subtotal del carrito.
     * @return El componente JTextField para el subtotal.
     */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /**
     * Obtiene el campo de texto del IVA del carrito.
     * @return El componente JTextField para el IVA.
     */
    public JTextField getTxtIVA() {
        return txtIVA;
    }

    /**
     * Obtiene el campo de texto del total del carrito.
     * @return El componente JTextField para el total.
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Obtiene el botón de guardar.
     * @return El componente JButton para guardar.
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /**
     * Obtiene el botón de buscar.
     * @return El componente JButton para buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Obtiene el botón de añadir.
     * @return El componente JButton para añadir.
     */
    public JButton getBtnAñadir() {
        return btnAñadir;
    }

    /**
     * Obtiene el botón de limpiar.
     * @return El componente JButton para limpiar.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Obtiene el ComboBox para seleccionar la cantidad.
     * @return El componente JComboBox para la cantidad.
     */
    public JComboBox<String> getCbxCantidad() {
        return cbxCantidad;
    }

    /**
     * Obtiene la tabla de ítems del carrito.
     * @return El componente JTable que muestra los ítems.
     */
    public JTable getTblItems() {
        return tblItems;
    }

    /**
     * Obtiene el modelo de la tabla.
     * @return El DefaultTableModel de la tabla de ítems.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje informativo.
     * @param mensaje El texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }



}