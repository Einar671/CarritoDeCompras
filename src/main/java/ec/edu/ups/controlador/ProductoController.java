package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.util.List;

public class ProductoController {

    private final ProductoDAO productoDAO;
    private final MensajeInternacionalizacionHandler mensajes;

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoEliminarView productoEliminarView;
    private final ProductoModificarView productoModificarView;
    private final CarritoAñadirView carritoAñadirView;

    public ProductoController(ProductoDAO productoDAO, CarritoAñadirView carritoAñadirView,
                              ProductoModificarView productoModificarView, ProductoEliminarView productoEliminarView,
                              ProductoListaView productoListaView, ProductoAnadirView productoAnadirView,
                              MensajeInternacionalizacionHandler mensajes) {
        this.productoDAO = productoDAO;
        this.mensajes = mensajes;

        this.carritoAñadirView = carritoAñadirView;
        this.productoModificarView = productoModificarView;
        this.productoEliminarView = productoEliminarView;
        this.productoListaView = productoListaView;
        this.productoAnadirView = productoAnadirView;


        configurarEventos();
    }

    private void configurarEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());
        productoAnadirView.getBtnLimpiar().addActionListener(e -> productoAnadirView.limpiarCampos());

        productoListaView.getBtnBuscar().addActionListener(e -> buscarProductoPorNombre());
        productoListaView.getBtnListar().addActionListener(e -> listarTodosLosProductos());

        productoEliminarView.getBtnBuscar().addActionListener(e -> buscarProductoParaEliminar());
        productoEliminarView.getBtnEliminar().addActionListener(e -> eliminarProducto());

        productoModificarView.getBtnBuscar().addActionListener(e -> buscarProductoParaModificar());
        productoModificarView.getBtnModificar().addActionListener(e -> modificarProducto());

        carritoAñadirView.getBtnBuscar().addActionListener(e -> buscarProductoParaCarrito());
    }

    private void guardarProducto() {
        try {
            int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
            String nombre = productoAnadirView.getTxtNombre().getText();
            double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

            if (nombre.isEmpty()) {
                productoAnadirView.mostrarMensaje(mensajes.get("mensaje.usuario.error.camposVacios"));
                return;
            }

            productoDAO.crear(new Producto(codigo, nombre, precio));
            productoAnadirView.mostrarMensaje(mensajes.get("mensaje.producto.guardado"));
            productoAnadirView.limpiarCampos();
        } catch (NumberFormatException ex) {
            productoAnadirView.mostrarMensaje(mensajes.get("mensaje.carrito.codigoInvalido"));
        }
    }

    private void buscarProductoPorNombre() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productos = productoDAO.buscarPorNombre(nombre);
        productoListaView.mostrarProductos(productos);
    }

    private void listarTodosLosProductos() {
        productoListaView.mostrarProductos(productoDAO.listarTodos());
    }

    private void buscarProductoParaEliminar() {
        try {
            int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                productoEliminarView.mostrarMensaje(mensajes.get("mensaje.noEncontrado"));
            } else {
                productoEliminarView.getTxtNombre().setText(producto.getNombre());
                productoEliminarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
                productoEliminarView.getTxtCodigo().setEnabled(false);
                productoEliminarView.getBtnEliminar().setEnabled(true);
            }
        } catch (NumberFormatException ex) {
            productoEliminarView.mostrarMensaje(mensajes.get("mensaje.carrito.codigoInvalido"));
        }
    }

    private void eliminarProducto() {
        String mensajeConfirmacion = mensajes.get("yesNo.producto.eliminar");
        int respuesta = JOptionPane.showConfirmDialog(productoEliminarView, mensajeConfirmacion, mensajes.get("yesNo.app.titulo"), JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
            productoDAO.eliminar(codigo);
            productoEliminarView.mostrarMensaje(mensajes.get("mensaje.producto.eliminado"));
            productoEliminarView.limpiarCampos();
        }
    }

    private void buscarProductoParaModificar() {
        try {
            int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                productoModificarView.mostrarMensaje(mensajes.get("mensaje.noEncontrado"));
            } else {
                productoModificarView.getTxtNombre().setText(producto.getNombre());
                productoModificarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
                productoModificarView.getBtnModificar().setEnabled(true);
                productoModificarView.getBtnBuscar().setEnabled(false);
                productoModificarView.getTxtCodigo().setEditable(false);
            }
        } catch (NumberFormatException ex) {
            productoModificarView.mostrarMensaje(mensajes.get("mensaje.carrito.codigoInvalido"));
        }
    }

    private void modificarProducto() {
        String mensajeConfirmacion = mensajes.get("yesNo.producto.modificar");
        int respuesta = JOptionPane.showConfirmDialog(productoModificarView, mensajeConfirmacion, mensajes.get("yesNo.app.titulo"), JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
                String nombre = productoModificarView.getTxtNombre().getText();
                double precio = Double.parseDouble(productoModificarView.getTxtPrecio().getText());

                if (nombre.trim().isEmpty()) {
                    productoModificarView.mostrarMensaje(mensajes.get("mensaje.usuario.error.camposVacios"));
                    return;
                }

                Producto productoActualizado = new Producto(codigo, nombre, precio);
                productoDAO.actualizar(productoActualizado);

                productoModificarView.mostrarMensaje(mensajes.get("mensaje.producto.modificado"));
                productoModificarView.getTxtCodigo().setText("");
                productoModificarView.getTxtNombre().setText("");
                productoModificarView.getTxtPrecio().setText("");
                productoModificarView.getBtnModificar().setEnabled(false);
                productoModificarView.getBtnBuscar().setEnabled(true);
                productoModificarView.getTxtCodigo().setEditable(true);

            } catch (NumberFormatException ex) {
                productoModificarView.mostrarMensaje(mensajes.get("mensaje.carrito.codigoInvalido"));
            }
        }
    }

    private void buscarProductoParaCarrito() {
        try {
            int codigo = Integer.parseInt(carritoAñadirView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                carritoAñadirView.mostrarMensaje(mensajes.get("mensaje.noEncontrado"));
                carritoAñadirView.getTxtNombre().setText("");
                carritoAñadirView.getTxtPrecio().setText("");
            } else {
                carritoAñadirView.getTxtNombre().setText(producto.getNombre());
                carritoAñadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            }
        } catch (NumberFormatException ex) {
            carritoAñadirView.mostrarMensaje(mensajes.get("mensaje.carrito.codigoInvalido"));
        }
    }
}