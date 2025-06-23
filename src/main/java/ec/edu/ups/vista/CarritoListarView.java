package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.GregorianCalendar;
import java.util.List;

public class CarritoListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btBuscar;
    private JTable tblCarritos;
    private JButton btnListar;
    private DefaultTableModel modelo;

    public CarritoListarView() {
        super("[ADMIN] Listar Carrito de compras", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Código Carrito", "Usuario", "Fecha", "Cantidad Items", "Subtotal", "IVA", "Total"};
        modelo.setColumnIdentifiers(columnas);
        tblCarritos.setModel(modelo);
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtBuscar() {
        return btBuscar;
    }

    public JButton getListarButton() {
        return btnListar;
    }

    public void mostrarCarritos(List<Carrito> carritos) {
        modelo.setRowCount(0);
        for (Carrito carrito : carritos) {
            Object[] fila = {
                    carrito.getCodigo(),
                    carrito.getUsuario() != null ? carrito.getUsuario().getUsername() : "N/A",
                    carrito.getFecha() != null ? String.format("%02d/%02d/%d", carrito.getFecha().get(GregorianCalendar.DAY_OF_MONTH), carrito.getFecha().get(GregorianCalendar.MONTH) + 1, carrito.getFecha().get(GregorianCalendar.YEAR)) : "N/A",
                    carrito.obtenerItems().size(),
                    String.format("%.2f", carrito.calcularSubtotal()),
                    String.format("%.2f", carrito.calcularIVA()),
                    String.format("%.2f", carrito.calcularTotal())
            };
            modelo.addRow(fila);
        }
    }


    public void mostrarDetallesCarrito(Carrito carrito) {
        if (carrito == null) {
            mostrarMensaje("No se encontró el carrito para mostrar detalles.");
            return;
        }

        String detalles = "Detalles del Carrito Código: " + carrito.getCodigo() + "\n";
        detalles += "Usuario: " + carrito.getUsuario().getUsername() + "\n";
        detalles += "--------------------------------------------------\n";
        detalles += "ITEMS DEL CARRITO:\n";

        if (carrito.obtenerItems().isEmpty()) {
            detalles += " (El carrito no tiene ítems)\n";
        } else {
            for (ItemCarrito item : carrito.obtenerItems()) {
                detalles += "- Producto: " + item.getProducto().getNombre() + "\n";
                detalles += "  Cantidad: " + item.getCantidad() + "\n";
                detalles += "  Precio Unit.: $" + String.format("%.2f", item.getProducto().getPrecio()) + "\n";
                detalles += "  Subtotal Ítem: $" + String.format("%.2f", item.getSubtotal()) + "\n\n";
            }
        }

        detalles += "--------------------------------------------------\n";
        detalles += "Total Carrito: $" + String.format("%.2f", carrito.calcularTotal()) + "\n";

        JOptionPane.showMessageDialog(this, detalles, "Detalles del Carrito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}