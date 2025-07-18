package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Vista (JInternalFrame) para buscar y modificar un carrito de compras existente.
 * <p>
 * Esta clase proporciona la interfaz gráfica para que un usuario (generalmente un administrador)
 * pueda buscar un carrito por su código, ver sus detalles y modificar la cantidad de los
 * ítems directamente en la tabla.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class CarritoModificarView extends JInternalFrame {
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Campo de texto para ingresar el código del carrito a buscar.
     */
    private JTextField txtCodigo;
    /**
     * Botón para iniciar la búsqueda del carrito.
     */
    private JButton btnBuscar;
    /**
     * Campo de texto para mostrar el nombre de usuario del propietario del carrito.
     */
    private JTextField txtUsuario;
    /**
     * Campo de texto para mostrar la fecha de creación del carrito.
     */
    private JTextField txtFecha;
    /**
     * Tabla que muestra los ítems del carrito y permite la edición de la cantidad.
     */
    private JTable tblItems;
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
     * Botón para guardar las modificaciones realizadas en el carrito.
     */
    private JButton btnModificar;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;
    /**
     * Modelo de datos para la tabla {@code tblItems}.
     */
    private DefaultTableModel modeloDetalles;
    /**
     * El objeto Carrito que se ha encontrado y se está modificando actualmente.
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
     * Constructor de la vista CarritoModificarView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga los iconos
     * para los botones, inicializa el modelo de la tabla, configura los listeners
     * y actualiza los textos según el idioma.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public CarritoModificarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        setContentPane(panelPrincipal);
        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));


        setSize(600, 400);

        URL urlBuscar=getClass().getResource("/search.png");
        URL urlModificar=getClass().getResource("/edit.png");

        modeloDetalles = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Solo la columna de cantidad (índice 3) es editable.
            }
        };
        tblItems.setModel(modeloDetalles);

        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        btnModificar.setIcon(new ImageIcon(urlModificar));

        actualizarTextos();
        configurarListeners();

        btnModificar.setEnabled(false);
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, los tooltips y los encabezados
     * de la tabla según el {@code Locale} configurado en el manejador de mensajes.
     * Al final, vuelve a mostrar los ítems del carrito para que los formatos se actualicen.
     */
    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        setTitle(mensajes.get("carrito.modificar.titulo.app"));
        lblTitulo.setText(mensajes.get("carrito.modificar.titulo.app"));
        lblCodigo.setText(mensajes.get("global.codigo") + ":");
        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblFecha.setText(mensajes.get("global.fecha") + ":");
        lblItems.setText(mensajes.get("global.item") + ":");

        txtCodigo.setToolTipText(mensajes.get("carrito.top.codigo"));

        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnModificar.setText(mensajes.get("global.boton.modificar"));

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
     * Configura los listeners de los componentes de la vista.
     * <p>
     * Añade un {@code TableModelListener} al modelo de la tabla para detectar
     * cuando el usuario edita la cantidad de un ítem y actualizar el subtotal
     * en consecuencia.
     */
    private void configurarListeners() {
        modeloDetalles.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) {
                actualizarSubtotalFila(e.getFirstRow());
            }
        });
    }

    /**
     * Actualiza el subtotal de una fila específica en la tabla cuando se modifica la cantidad.
     * <p>
     * Este método se llama cuando el listener de la tabla detecta un cambio en la columna
     * de cantidad. Actualiza el modelo de datos del {@code carritoActual} y luego
     * refresca la celda del subtotal en la tabla con el nuevo valor formateado.
     *
     * @param fila El índice de la fila que fue actualizada.
     */
    private void actualizarSubtotalFila(int fila) {
        if (carritoActual != null) {
            try {
                int codigoProducto = (int) modeloDetalles.getValueAt(fila, 0);
                int nuevaCantidad = Integer.parseInt(modeloDetalles.getValueAt(fila, 3).toString());

                carritoActual.actualizarCantidadProducto(codigoProducto, nuevaCantidad);

                ItemCarrito itemActualizado = encontrarItem(codigoProducto);
                if (itemActualizado != null) {
                    modeloDetalles.setValueAt(
                            FormateadorUtils.formatearMoneda(itemActualizado.getSubtotal(), locale),
                            fila, 4);
                }
            } catch (NumberFormatException ex) {
                mostrarMensaje(mensajes.get("mensaje.carrito.cantidadInvalida"));
            }
        }
    }

    /**
     * Busca un {@link ItemCarrito} dentro del carrito actual por el código del producto.
     *
     * @param codigoProducto El código del producto del ítem a buscar.
     * @return El {@link ItemCarrito} encontrado, o {@code null} si no existe o el carrito actual es nulo.
     */
    private ItemCarrito encontrarItem(int codigoProducto) {
        if (carritoActual == null) return null;
        for (ItemCarrito item : carritoActual.obtenerItems()) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                return item;
            }
        }
        return null;
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
    public JTextField getTxtCodigo() { return txtCodigo; }
    /**
     * Obtiene el botón de buscar.
     * @return El componente JButton para buscar.
     */
    public JButton getBtnBuscar() { return btnBuscar; }
    /**
     * Obtiene el campo de texto del usuario.
     * @return El componente JTextField para el usuario.
     */
    public JTextField getTxtUsuario() { return txtUsuario; }
    /**
     * Obtiene el campo de texto de la fecha.
     * @return El componente JTextField para la fecha.
     */
    public JTextField getTxtFecha() { return txtFecha; }
    /**
     * Obtiene el botón de modificar.
     * @return El componente JButton para modificar.
     */
    public JButton getBtnModificar() { return btnModificar; }
    /**
     * Obtiene el carrito que se está mostrando y modificando actualmente en la vista.
     * @return El objeto {@link Carrito} actual.
     */
    public Carrito getCarritoActual() { return carritoActual; }
}