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

public class CarritoListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblCarritos;
    private JButton btnListar;
    private JTable tblDetalles;
    private DefaultTableModel modelo;
    private DefaultTableModel modeloDetalles;
    private JLabel lblTitulo;
    private JLabel lblDetalles;
    private JLabel lblDetallesCar;
    private List<Carrito> listaActual;
    private Carrito carritoActual;
    private MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public CarritoListarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));
        setContentPane(panelPrincipal);

        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        URL urlListar=getClass().getResource("/list.png");

        modelo = new DefaultTableModel();
        tblCarritos.setModel(modelo);

        btnListar.setIcon(new ImageIcon(urlListar));

        modeloDetalles = new DefaultTableModel();
        tblDetalles.setModel(modeloDetalles);

        actualizarTextos();
    }

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

    public void mostrarCarritos(List<Carrito> carritos) {
        this.listaActual=carritos;
        modelo.setRowCount(0);
        if(carritos == null) {
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

    public void mostrarDetallesCarrito(Carrito carrito) {
        this.carritoActual=carrito;
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
    public void mostrarDetalles(Carrito carrito) {
        this.carritoActual=carrito;
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

    public void limpiarTablaDetalles() {
        modeloDetalles.setRowCount(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }


    public JButton getBtnListar() {
        return btnListar;
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }
}