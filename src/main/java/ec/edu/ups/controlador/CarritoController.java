package ec.edu.ups.controlador;


import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.CarritoAñadirView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarritoController {

    private Carrito carrito;
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAñadirView carritoAñadirView;

    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO, CarritoAñadirView carritoAñadirView) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAñadirView = carritoAñadirView;
        this.carrito = new Carrito();
        configurarEventosEnVistas();
    }

    public void configurarEventosEnVistas(){
        carritoAñadirView.getBtnAñadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                añadirProducto();
            }
        });

        carritoAñadirView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCarrito();
            }
        });
    }

    private void guardarCarrito() {
        carritoDAO.crear(carrito);
        carritoAñadirView.mostrarMensaje("Carrito guardado correctamente");
        System.out.println(carritoDAO.listarTodos());
    }

    private void añadirProducto() {
        Producto producto = productoDAO.buscarPorCodigo(Integer.parseInt(carritoAñadirView.getTxtCodigo().getText()));
        int cantidad = carritoAñadirView.getCbxCantidad().getSelectedIndex();
        carrito.agregarProducto(producto, cantidad);
        cargarProductos();
        mostrarTotales();
    }

    private void cargarProductos(){
        List< ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAñadirView.getTable1().getModel();
        modelo.setRowCount(0);
        for (ItemCarrito item : items) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getCantidad() * item.getProducto().getPrecio()
            });
        }
    }

    private void mostrarTotales(){
        carritoAñadirView.getTxtSubtotal().setText(String.valueOf(carrito.calcularSubtotal()));
        carritoAñadirView.getTxtIVA().setText(String.valueOf(carrito.calcularIVA()));
        carritoAñadirView.getTxtTotal().setText(String.valueOf(carrito.calcularTotal()));
    }


}