package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.util.Sonido;
import ec.edu.ups.vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final Usuario usuarioLogueado;
    private final CarritoAñadirView carritoAñadirView;
    private final CarritoListarView carritoListarView;
    private final CarritoModificarView carritoModificarView;
    private final CarritoEliminarView carritoEliminarView;
    private final CarritoListarMisView carritoListarMisView;
    private final MensajeInternacionalizacionHandler mensajes;

    private Carrito carritoActual;
    private Carrito carritoSeleccionado;
    private final Locale locale;

    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO, CarritoAñadirView carritoAñadirView,
                             CarritoListarView carritoListarView, CarritoModificarView carritoModificarView,
                             CarritoEliminarView carritoEliminarView, CarritoListarMisView carritoListarMisView,
                             Usuario usuarioLogueado, MensajeInternacionalizacionHandler mensajes) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.usuarioLogueado = usuarioLogueado;
        this.carritoAñadirView = carritoAñadirView;
        this.carritoListarView = carritoListarView;
        this.carritoModificarView = carritoModificarView;
        this.carritoEliminarView = carritoEliminarView;
        this.carritoListarMisView = carritoListarMisView;
        this.mensajes = mensajes;

        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        iniciarNuevoCarrito();
        configurarEventosEnVistas();
    }

    private void iniciarNuevoCarrito() {
        this.carritoActual = new Carrito();
        this.carritoActual.setUsuario(this.usuarioLogueado);
    }

    public void configurarEventosEnVistas() {
        carritoAñadirView.getBtnAñadir().addActionListener(e -> añadirProducto());
        carritoAñadirView.getBtnGuardar().addActionListener(e -> guardarCarrito());
        carritoAñadirView.getBtnLimpiar().addActionListener(e -> limpiarCarrito());

        carritoListarView.getBtnListar().addActionListener(e -> listarTodosLosCarritos());
        carritoListarView.getBtBuscar().addActionListener(e -> buscarYMostrarDetalles());

        carritoModificarView.getBtnBuscar().addActionListener(e -> buscarCarritoParaModificar());
        carritoModificarView.getBtnModificar().addActionListener(e -> guardarModificacionCarrito());

        carritoEliminarView.getBtnBuscar().addActionListener(e -> buscarCarritoParaEliminar());
        carritoEliminarView.getBtnEliminar().addActionListener(e -> eliminarCarrito());

        carritoListarMisView.getBtnListar().addActionListener(e -> listarMisCarritos());
        carritoListarMisView.getBtBuscar().addActionListener(e -> buscarYMostrarDetallesMis());
    }


    private void buscarCarritoParaEliminar() {
        try {
            int codigo = Integer.parseInt(carritoEliminarView.getTxtCodigo().getText());
            if(usuarioLogueado.getRol()== Rol.ADMINISTRADOR) {
                this.carritoSeleccionado = carritoDAO.buscarPorCodigo(codigo);
            }else{
                this.carritoSeleccionado=carritoDAO.buscarPorCodigoYUsuario(codigo,usuarioLogueado);
            }

            if (carritoSeleccionado != null) {
                carritoEliminarView.getTxtUsuario().setText(carritoSeleccionado.getUsuario().getUsername());
                carritoEliminarView.getTxtFecha().setText(FormateadorUtils.formatearFecha(carritoSeleccionado.getFecha().getTime(), locale));
                carritoEliminarView.mostrarItemsCarrito(carritoSeleccionado);

                carritoEliminarView.getTxtCodigo().setEditable(false);
                carritoEliminarView.getBtnBuscar().setEnabled(false);
                carritoEliminarView.getBtnEliminar().setEnabled(true);
            } else {
                carritoEliminarView.mostrarMensaje(mensajes.get("mensaje.carrito.noEncontrado") + " " + codigo);
                limpiarVistaEliminar();
            }
        } catch (NumberFormatException ex) {
            carritoEliminarView.mostrarMensaje(mensajes.get("mensaje.carrito.codigoInvalido"));
        }
    }

    private void buscarCarritoParaModificar() {
        try {
            int codigo = Integer.parseInt(carritoModificarView.getTxtCodigo().getText());
            if(usuarioLogueado.getRol()== Rol.ADMINISTRADOR) {
                this.carritoSeleccionado = carritoDAO.buscarPorCodigo(codigo);
            }else{
                this.carritoSeleccionado=carritoDAO.buscarPorCodigoYUsuario(codigo,usuarioLogueado);
            }
            if (carritoSeleccionado != null) {
                carritoModificarView.getTxtUsuario().setText(carritoSeleccionado.getUsuario().getUsername());
                carritoModificarView.getTxtFecha().setText(FormateadorUtils.formatearFecha(carritoSeleccionado.getFecha().getTime(), locale));
                carritoModificarView.mostrarItemsCarrito(carritoSeleccionado);

                carritoModificarView.getTxtCodigo().setEditable(false);
                carritoModificarView.getBtnBuscar().setEnabled(false);
                carritoModificarView.getBtnModificar().setEnabled(true);
            } else {
                carritoModificarView.mostrarMensaje(mensajes.get("mensaje.carrito.noEncontrado") + " " + codigo);
                limpiarVistaModificar();
            }
        } catch (NumberFormatException ex) {
            carritoModificarView.mostrarMensaje(mensajes.get("mensaje.carrito.codigoInvalido"));
        }

    }

    private void guardarModificacionCarrito() {
        if (carritoSeleccionado == null) {
            carritoModificarView.mostrarMensaje(mensajes.get("mensaje.carrito.noSeleccionado"));
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(carritoModificarView, mensajes.get("yesNo.carrito.modificar"), mensajes.get("yesNo.app.titulo"), JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {

            carritoDAO.actualizar(carritoSeleccionado);
            carritoModificarView.mostrarMensaje(mensajes.get("mensaje.carrito.guardado"));
            limpiarVistaModificar();
        }

    }

    private void cargarProductosEnTabla() {
        carritoAñadirView.mostrarCarrito(carritoActual);
    }

    private void mostrarTotales() {
        carritoAñadirView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carritoActual.calcularSubtotal(), locale));
        carritoAñadirView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carritoActual.calcularIVA(), locale));
        carritoAñadirView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carritoActual.calcularTotal(), locale));
    }

    private void listarMisCarritos() {
        List<Carrito> carritos = carritoDAO.buscarPorUsuario(usuarioLogueado);
        if (carritos.isEmpty()) {
            carritoListarMisView.mostrarMensaje(mensajes.get("mensaje.carrito.noHay"));
        }
        carritoListarMisView.mostrarCarritos(carritos);
    }

    private void eliminarCarrito() {
        if (carritoSeleccionado == null) return;

        String mensajeConfirmacion = mensajes.get("yesNo.carrito.eliminar");
        int respuesta = JOptionPane.showConfirmDialog(carritoEliminarView, mensajeConfirmacion, mensajes.get("yesNo.app.titulo"), JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            carritoDAO.eliminar(carritoSeleccionado.getCodigo());
            carritoEliminarView.mostrarMensaje(mensajes.get("mensaje.carrito.eliminado"));
            limpiarVistaEliminar();
        }
    }

    private void limpiarVistaModificar() {
        carritoModificarView.getTxtCodigo().setText("");
        carritoModificarView.getTxtUsuario().setText("");
        carritoModificarView.getTxtFecha().setText("");
        carritoModificarView.mostrarItemsCarrito(null);
        carritoModificarView.getTxtCodigo().setEditable(true);
        carritoModificarView.getBtnBuscar().setEnabled(true);
        carritoModificarView.getBtnModificar().setEnabled(false);
        this.carritoSeleccionado = null;
    }

    private void limpiarVistaEliminar() {
        carritoEliminarView.getTxtCodigo().setText("");
        carritoEliminarView.getTxtUsuario().setText("");
        carritoEliminarView.getTxtFecha().setText("");
        carritoEliminarView.mostrarItemsCarrito(null);
        carritoEliminarView.getTxtCodigo().setEditable(true);
        carritoEliminarView.getBtnBuscar().setEnabled(true);
        carritoEliminarView.getBtnEliminar().setEnabled(false);
        this.carritoSeleccionado = null;
    }

    private void buscarYMostrarDetallesMis() {
        try {
            int codigo = Integer.parseInt(carritoListarMisView.getTxtCodigo().getText());
            Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

            if (carritoEncontrado != null && carritoEncontrado.getUsuario().equals(usuarioLogueado)) {
                carritoListarMisView.mostrarDetalles(carritoEncontrado);
            } else {
                carritoListarMisView.mostrarMensaje(mensajes.get("mensaje.carrito.noEncontrado") + " " + codigo);
            }
        } catch (NumberFormatException ex) {
            carritoListarMisView.mostrarMensaje(mensajes.get("mensaje.carrito.codigoInvalido"));
        }
    }

    private void buscarYMostrarDetalles() {
        try {
            int codigo = Integer.parseInt(carritoListarView.getTxtCodigo().getText());
            Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

            if (carritoEncontrado != null) {
                carritoListarView.mostrarDetallesCarrito(carritoEncontrado);
            } else {
                carritoListarView.mostrarMensaje(mensajes.get("mensaje.carrito.noEncontrado") + " " + codigo);
            }
        } catch (NumberFormatException ex) {
            carritoListarView.mostrarMensaje(mensajes.get("mensaje.carrito.codigoInvalido"));
        }
    }

    private void listarTodosLosCarritos() {
        List<Carrito> carritos = carritoDAO.listarTodos();
        if (carritos.isEmpty()) {
            carritoListarView.mostrarMensaje(mensajes.get("mensaje.carrito.noHay"));
        }
        carritoListarView.mostrarCarritos(carritos);
    }

    private void guardarCarrito() {
        if (carritoActual.obtenerItems().isEmpty()) {
            carritoAñadirView.mostrarMensaje(mensajes.get("mensaje.carrito.vacio"));
            return;
        }
        Sonido sonido = new Sonido();
        sonido.cargarSonido("/sonidoCompra.wav");
        sonido.reproducir();
        carritoDAO.crear(carritoActual);
        carritoAñadirView.mostrarMensaje(mensajes.get("mensaje.carrito.guardado") + " " + usuarioLogueado.getUsername());

        iniciarNuevoCarrito();
        cargarProductosEnTabla();
        mostrarTotales();
        carritoAñadirView.getTxtCodigo().setText("");
        carritoAñadirView.getTxtNombre().setText("");
        carritoAñadirView.getTxtPrecio().setText("");


    }

    private void añadirProducto() {
        try {
            Producto producto = productoDAO.buscarPorCodigo(Integer.parseInt(carritoAñadirView.getTxtCodigo().getText()));
            if (producto == null) {
                carritoAñadirView.mostrarMensaje(mensajes.get("mensaje.producto.noEncontrado"));
                return;
            }
            int cantidad = carritoAñadirView.getCbxCantidad().getSelectedIndex() + 1;
            if (cantidad <= 0) {
                carritoAñadirView.mostrarMensaje(mensajes.get("mensaje.carrito.cantidadInvalida"));
                return;
            }

            carritoActual.agregarProducto(producto, cantidad);
            cargarProductosEnTabla();
            mostrarTotales();
        } catch (NumberFormatException ex) {
            carritoAñadirView.mostrarMensaje(mensajes.get("mensaje.carrito.codigoInvalido"));
        }
    }

    private void limpiarCarrito() {
        int respuesta = JOptionPane.showConfirmDialog(carritoAñadirView, mensajes.get("yesNo.carrito.vaciar"), mensajes.get("yesNo.app.titulo"), JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            iniciarNuevoCarrito();
            cargarProductosEnTabla();
            mostrarTotales();
            carritoAñadirView.getTxtCodigo().setText("");
            carritoAñadirView.getTxtNombre().setText("");
            carritoAñadirView.getTxtPrecio().setText("");
        }
    }
}