package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Locale;

public class CarritoAñadirView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JPanel txt;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JTable tblItems;
    private JButton btnGuardar;
    private JButton btnBuscar;
    private JButton btnAñadir;
    private JButton btnLimpiar;
    private JComboBox<String> cbxCantidad;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel IVA;
    private JLabel lblTotal;
    private DefaultTableModel modelo;
    private Carrito carritoActual;


    private MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public CarritoAñadirView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 550);
        setClosable(true);
        setIconifiable(true);

        modelo = new DefaultTableModel();
        tblItems.setModel(modelo);

        actualizarTextos();
        cargarDatos();
    }

    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        setTitle(mensajes.get("carrito.crear.titulo.app"));

        lblCodigo.setText(mensajes.get("global.codigo"));
        lblNombre.setText(mensajes.get("global.nombre"));
        lblPrecio.setText(mensajes.get("global.precio"));
        lblCantidad.setText(mensajes.get("global.cantidad"));
        lblSubtotal.setText(mensajes.get("global.subtotal"));
        IVA.setText(mensajes.get("global.IVA"));
        lblTotal.setText(mensajes.get("global.total"));

        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnAñadir.setText(mensajes.get("global.añadir"));
        btnGuardar.setText(mensajes.get("global.boton.guardar"));
        btnLimpiar.setText(mensajes.get("global.boton.limpiar"));

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

    private void cargarDatos() {
        cbxCantidad.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(String.valueOf(i));
        }
    }


    public void mostrarCarrito(Carrito carrito) {
        this.carritoActual=carrito;
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

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtSubtotal() { return txtSubtotal; }
    public JTextField getTxtIVA() { return txtIVA; }
    public JTextField getTxtTotal() { return txtTotal; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnAñadir() { return btnAñadir; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JComboBox<String> getCbxCantidad() { return cbxCantidad; }
    public JTable getTblItems() { return tblItems; }
    public DefaultTableModel getModelo() { return modelo; }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}