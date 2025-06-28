package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CarritoEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTable tblItems;
    private JTextField txtUsuario;
    private JTextField txtFecha;
    private JButton btnEliminar;
    private JLabel lblCodigo;
    private JLabel lblUsuario;
    private JLabel lblFecha;
    private JLabel lblItems;
    private DefaultTableModel modeloDetalles;
    private Carrito carritoActual;

    private MensajeInternacionalizacionHandler mensajes;

    public CarritoEliminarView(MensajeInternacionalizacionHandler mensajes) {
        this.mensajes = mensajes;

        setContentPane(panelPrincipal);
        setSize(600, 400);

        modeloDetalles = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Generalmente, en una vista de eliminación, la tabla no es editable.
            }
        };
        tblItems.setModel(modeloDetalles);

        actualizarTextos();
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("carrito.eliminar.titulo.app"));

        lblCodigo.setText(mensajes.get("global.codigo"));
        lblUsuario.setText(mensajes.get("global.usuario"));
        lblFecha.setText(mensajes.get("global.fecha")); // Asegúrate de añadir la clave "global.fecha" a tus archivos .properties
        lblItems.setText(mensajes.get("global.item"));

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
    }

    public void mostrarItemsCarrito(Carrito carrito) {
        this.carritoActual = carrito;
        modeloDetalles.setRowCount(0);

        if (carrito != null) {
            List<ItemCarrito> items = carrito.obtenerItems();
            for (ItemCarrito item : items) {
                Object[] fila = {
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        item.getProducto().getPrecio(),
                        item.getCantidad(),
                        item.getSubtotal()
                };
                modeloDetalles.addRow(fila);
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblItems() {
        return tblItems;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public Carrito getCarritoActual() {
        return carritoActual;
    }
}