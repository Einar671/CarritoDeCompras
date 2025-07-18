/**
 * Controlador para la gestión de productos en el sistema.
 * Maneja las operaciones CRUD de productos y coordina la interacción entre las vistas y el modelo.
 *
 * @author Einar Kaalhus
 * @version 1.0
 * @since 2023-05-15
 */
package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.util.List;

public class ProductoController {

    // DAO para acceso a datos de productos
    private final ProductoDAO productoDAO;

    // Handler para mensajes internacionalizados
    private final MensajeInternacionalizacionHandler mensajes;

    // Vistas asociadas al controlador
    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoEliminarView productoEliminarView;
    private final ProductoModificarView productoModificarView;
    private final CarritoAñadirView carritoAñadirView;

    /**
     * Constructor principal del controlador de productos.
     *
     * @param productoDAO DAO para operaciones con productos
     * @param carritoAñadirView Vista para añadir productos al carrito
     * @param productoModificarView Vista para modificar productos
     * @param productoEliminarView Vista para eliminar productos
     * @param productoListaView Vista para listar productos
     * @param productoAnadirView Vista para añadir nuevos productos
     * @param mensajes Handler para mensajes internacionalizados
     */
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

    /**
     * Configura los listeners de eventos para todas las vistas asociadas.
     */
    private void configurarEventos() {
        // Eventos para vista de añadir producto
        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());
        productoAnadirView.getBtnLimpiar().addActionListener(e -> productoAnadirView.limpiarCampos());

        // Eventos para vista de listar productos
        productoListaView.getBtnBuscar().addActionListener(e -> buscarProductoPorNombre());
        productoListaView.getBtnListar().addActionListener(e -> listarTodosLosProductos());

        // Eventos para vista de eliminar producto
        productoEliminarView.getBtnBuscar().addActionListener(e -> buscarProductoParaEliminar());
        productoEliminarView.getBtnEliminar().addActionListener(e -> eliminarProducto());

        // Eventos para vista de modificar producto
        productoModificarView.getBtnBuscar().addActionListener(e -> buscarProductoParaModificar());
        productoModificarView.getBtnModificar().addActionListener(e -> modificarProducto());

        // Evento para vista de carrito (búsqueda de productos)
        carritoAñadirView.getBtnBuscar().addActionListener(e -> buscarProductoParaCarrito());
    }

    /**
     * Guarda un nuevo producto en la base de datos.
     * Valida los campos antes de realizar la operación.
     */
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

    /**
     * Busca productos por nombre y los muestra en la vista correspondiente.
     */
    private void buscarProductoPorNombre() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productos = productoDAO.buscarPorNombre(nombre);
        productoListaView.mostrarProductos(productos);
    }

    /**
     * Lista todos los productos existentes en la base de datos.
     */
    private void listarTodosLosProductos() {
        productoListaView.mostrarProductos(productoDAO.listarTodos());
    }

    /**
     * Busca un producto para eliminarlo, mostrando sus detalles en la vista.
     */
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

    /**
     * Elimina un producto después de confirmación del usuario.
     */
    private void eliminarProducto() {
        String mensajeConfirmacion = mensajes.get("yesNo.producto.eliminar");
        int respuesta = JOptionPane.showConfirmDialog(productoEliminarView,
                mensajeConfirmacion,
                mensajes.get("yesNo.app.titulo"),
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
            productoDAO.eliminar(codigo);
            productoEliminarView.mostrarMensaje(mensajes.get("mensaje.producto.eliminado"));
            productoEliminarView.limpiarCampos();
        }
    }

    /**
     * Busca un producto para modificarlo, mostrando sus detalles en la vista.
     */
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

    /**
     * Modifica un producto existente después de confirmación del usuario.
     */
    private void modificarProducto() {
        String mensajeConfirmacion = mensajes.get("yesNo.producto.modificar");
        int respuesta = JOptionPane.showConfirmDialog(productoModificarView,
                mensajeConfirmacion,
                mensajes.get("yesNo.app.titulo"),
                JOptionPane.YES_NO_OPTION);

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

                productoModificarView.mostrarMensaje(mensajes.get("mensaje.producto.guardado"));
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

    /**
     * Busca un producto para añadirlo al carrito, mostrando sus detalles en la vista.
     */
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