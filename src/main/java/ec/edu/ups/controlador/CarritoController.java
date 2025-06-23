package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario; // Importar Usuario
import ec.edu.ups.vista.CarritoAñadirView;
import ec.edu.ups.vista.CarritoListarView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarritoController {

    private Carrito carrito;
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAñadirView carritoAñadirView;
    private final CarritoListarView carritoListarView;
    private final Usuario usuarioLogueado;

    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO, CarritoAñadirView carritoAñadirView, CarritoListarView carritoListarView, Usuario usuarioLogueado) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAñadirView = carritoAñadirView;
        this.carritoListarView = carritoListarView;
        this.usuarioLogueado = usuarioLogueado;
        iniciarNuevoCarrito();
        configurarEventosEnVistas();
    }

    private void iniciarNuevoCarrito() {
        this.carrito = new Carrito();
        this.carrito.setUsuario(this.usuarioLogueado);
    }

    public void configurarEventosEnVistas() {
        carritoAñadirView.getBtnAñadir().addActionListener(e -> añadirProducto());
        carritoAñadirView.getBtnGuardar().addActionListener(e -> guardarCarrito());
        carritoAñadirView.getBtnLimpiar().addActionListener(e -> limpiarCarrito());
        carritoListarView.getListarButton().addActionListener(e -> listarTodosLosCarritos());
        carritoListarView.getBtBuscar().addActionListener(e -> buscarYMostrarDetalles());

    }

    private void buscarYMostrarDetalles() {
        String codigoStr = carritoListarView.getTxtCodigo().getText().trim();
        if (codigoStr.isEmpty()) {
            carritoListarView.mostrarMensaje("Por favor, ingrese un código de carrito para buscar.");
            return;
        }
            int codigo = Integer.parseInt(codigoStr);
            Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

            if (carritoEncontrado != null) {
                carritoListarView.mostrarDetallesCarrito(carritoEncontrado);
            } else {
                carritoListarView.mostrarMensaje("No se encontró ningún carrito con el código: " + codigo);
            }
    }

    private void listarTodosLosCarritos() {
        List<Carrito> carritos = carritoDAO.listarTodos();
        if (carritos.isEmpty()) {
            carritoListarView.mostrarMensaje("No hay carritos registrados.");
        }
        carritoListarView.mostrarCarritos(carritos);
    }

    private void guardarCarrito() {
        if (carrito.obtenerItems().isEmpty()) {
            carritoAñadirView.mostrarMensaje("El carrito está vacío. Añada productos antes de guardar.");
            return;
        }
        carritoDAO.crear(carrito);
        carritoAñadirView.mostrarMensaje("Carrito guardado correctamente para el usuario: " + usuarioLogueado.getUsername());
        System.out.println("Carritos guardados: " + carritoDAO.listarTodos());

        limpiarCarrito();
    }

    private void añadirProducto() {
            Producto producto = productoDAO.buscarPorCodigo(Integer.parseInt(carritoAñadirView.getTxtCodigo().getText()));
            if (producto == null) {
                carritoAñadirView.mostrarMensaje("Producto no encontrado.");
                return;
            }
            int cantidad = carritoAñadirView.getCbxCantidad().getSelectedIndex() + 1;
            if (cantidad <= 0) {
                carritoAñadirView.mostrarMensaje("Seleccione una cantidad válida.");
                return;
            }

            carrito.agregarProducto(producto, cantidad);
            cargarProductosEnTabla();
            mostrarTotales();

    }

    private void limpiarCarrito() {
        int respuesta = JOptionPane.showConfirmDialog(carritoAñadirView, "¿Desea vaciar el carrito actual?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            iniciarNuevoCarrito();
            cargarProductosEnTabla();
            mostrarTotales();
            carritoAñadirView.getTxtCodigo().setText("");
            carritoAñadirView.getTxtNombre().setText("");
            carritoAñadirView.getTxtPrecio().setText("");
        }
    }

    private void cargarProductosEnTabla() {
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAñadirView.getTblItems().getModel();
        modelo.setRowCount(0);
        for (ItemCarrito item : items) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getSubtotal()
            });
        }
    }

    private void mostrarTotales() {
        carritoAñadirView.getTxtSubtotal().setText(String.format("%.2f", carrito.calcularSubtotal()));
        carritoAñadirView.getTxtIVA().setText(String.format("%.2f", carrito.calcularIVA()));
        carritoAñadirView.getTxtTotal().setText(String.format("%.2f", carrito.calcularTotal()));
    }
}