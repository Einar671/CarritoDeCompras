/**
 * Controlador principal para la gestión de carritos de compras en el sistema.
 * Maneja las operaciones CRUD para carritos y la interacción entre las vistas y los modelos.
 *
 * @author Einar Kaalhus
 * @version 1.0
 * @since 2023-05-15
 */
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

/**
 * Clase que gestiona todas las operaciones relacionadas con los carritos de compras.
 * Coordina la interacción entre las vistas, los DAOs y los modelos.
 */
public class CarritoController {

    // DAOs para acceso a datos
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;

    // Usuario actualmente autenticado
    private final Usuario usuarioLogueado;

    // Vistas asociadas al controlador
    private final CarritoAñadirView carritoAñadirView;
    private final CarritoListarView carritoListarView;
    private final CarritoModificarView carritoModificarView;
    private final CarritoEliminarView carritoEliminarView;
    private final CarritoListarMisView carritoListarMisView;

    // Utilidades
    private final MensajeInternacionalizacionHandler mensajes;

    // Estado del controlador
    private Carrito carritoActual;
    private Carrito carritoSeleccionado;
    private final Locale locale;

    /**
     * Constructor principal del controlador de carritos.
     *
     * @param carritoDAO DAO para operaciones con carritos
     * @param productoDAO DAO para operaciones con productos
     * @param carritoAñadirView Vista para añadir productos al carrito
     * @param carritoListarView Vista para listar todos los carritos
     * @param carritoModificarView Vista para modificar carritos
     * @param carritoEliminarView Vista para eliminar carritos
     * @param carritoListarMisView Vista para listar carritos del usuario actual
     * @param usuarioLogueado Usuario autenticado en el sistema
     * @param mensajes Handler para mensajes internacionalizados
     */
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

    /**
     * Inicializa un nuevo carrito vacío asociado al usuario actual.
     */
    private void iniciarNuevoCarrito() {
        this.carritoActual = new Carrito();
        this.carritoActual.setUsuario(this.usuarioLogueado);
    }

    /**
     * Configura los listeners de eventos para todas las vistas asociadas.
     */
    public void configurarEventosEnVistas() {
        // Configuración de eventos para la vista de añadir
        carritoAñadirView.getBtnAñadir().addActionListener(e -> añadirProducto());
        carritoAñadirView.getBtnGuardar().addActionListener(e -> guardarCarrito());
        carritoAñadirView.getBtnLimpiar().addActionListener(e -> limpiarCarrito());

        // Configuración de eventos para la vista de listar
        carritoListarView.getBtnListar().addActionListener(e -> listarTodosLosCarritos());

        // Configuración de selección en la vista de mis carritos
        ListSelectionModel selectionModelMis = carritoListarMisView.getTblCarritos().getSelectionModel();
        selectionModelMis.addListSelectionListener(e->{
            if (!e.getValueIsAdjusting()) {
                int selectedRow = carritoListarMisView.getTblCarritos().getSelectedRow();

                if (selectedRow != -1) {
                    int codigo = (int) carritoListarMisView.getTblCarritos().getValueAt(selectedRow, 0);

                    Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

                    if (carritoEncontrado != null && carritoEncontrado.getUsuario().equals(usuarioLogueado)) {
                        carritoListarMisView.mostrarDetalles(carritoEncontrado);
                    }
                }
            }
        });

        // Configuración de eventos para la vista de modificar
        carritoModificarView.getBtnBuscar().addActionListener(e -> buscarCarritoParaModificar());
        carritoModificarView.getBtnModificar().addActionListener(e -> guardarModificacionCarrito());

        // Configuración de eventos para la vista de eliminar
        carritoEliminarView.getBtnBuscar().addActionListener(e -> buscarCarritoParaEliminar());
        carritoEliminarView.getBtnEliminar().addActionListener(e -> eliminarCarrito());

        // Configuración de eventos para la vista de listar mis carritos
        carritoListarMisView.getBtnListar().addActionListener(e-> listarMisCarritos());

        // Configuración de selección en la vista de listar todos los carritos
        ListSelectionModel selectionModel = carritoListarView.getTblCarritos().getSelectionModel();
        selectionModel.addListSelectionListener(e->{
            if (!e.getValueIsAdjusting()) {
                int selectedRow = carritoListarView.getTblCarritos().getSelectedRow();

                if (selectedRow != -1) {
                    int codigo = (int) carritoListarView.getTblCarritos().getValueAt(selectedRow, 0);

                    Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

                    if (carritoEncontrado != null) {
                        carritoListarView.mostrarDetalles(carritoEncontrado);
                    }
                }
            }
        });
    }

    /**
     * Busca un carrito para eliminarlo, verificando permisos según el rol del usuario.
     */
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

    /**
     * Busca un carrito para modificarlo, verificando permisos según el rol del usuario.
     */
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

    /**
     * Guarda las modificaciones realizadas a un carrito existente.
     */
    private void guardarModificacionCarrito() {
        if (carritoSeleccionado == null) {
            carritoModificarView.mostrarMensaje(mensajes.get("mensaje.carrito.noSeleccionado"));
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(carritoModificarView,
                mensajes.get("yesNo.carrito.modificar"),
                mensajes.get("yesNo.app.titulo"),
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            carritoDAO.actualizar(carritoSeleccionado);
            carritoModificarView.mostrarMensaje(mensajes.get("mensaje.carrito.guardado"));
            limpiarVistaModificar();
        }
    }

    /**
     * Carga los productos del carrito actual en la tabla de la vista.
     */
    private void cargarProductosEnTabla() {
        carritoAñadirView.mostrarCarrito(carritoActual);
    }

    /**
     * Calcula y muestra los totales (subtotal, IVA y total) del carrito actual.
     */
    private void mostrarTotales() {
        carritoAñadirView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carritoActual.calcularSubtotal(), locale));
        carritoAñadirView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carritoActual.calcularIVA(), locale));
        carritoAñadirView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carritoActual.calcularTotal(), locale));
    }

    /**
     * Lista todos los carritos pertenecientes al usuario actual.
     */
    private void listarMisCarritos() {
        List<Carrito> carritos = carritoDAO.buscarPorUsuario(usuarioLogueado);
        if (carritos.isEmpty()) {
            carritoListarMisView.mostrarMensaje(mensajes.get("mensaje.carrito.noHay"));
        }
        carritoListarMisView.mostrarCarritos(carritos);
    }

    /**
     * Elimina el carrito seleccionado después de confirmación.
     */
    private void eliminarCarrito() {
        if (carritoSeleccionado == null) return;

        String mensajeConfirmacion = mensajes.get("yesNo.carrito.eliminar");
        int respuesta = JOptionPane.showConfirmDialog(carritoEliminarView,
                mensajeConfirmacion,
                mensajes.get("yesNo.app.titulo"),
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            carritoDAO.eliminar(carritoSeleccionado.getCodigo());
            carritoEliminarView.mostrarMensaje(mensajes.get("mensaje.carrito.eliminado"));
            limpiarVistaEliminar();
        }
    }

    /**
     * Limpia los campos y el estado de la vista de modificación.
     */
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

    /**
     * Limpia los campos y el estado de la vista de eliminación.
     */
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

    /**
     * Lista todos los carritos del sistema (solo accesible para administradores).
     */
    private void listarTodosLosCarritos() {
        List<Carrito> carritos = carritoDAO.listarTodos();
        if (carritos.isEmpty()) {
            carritoListarView.mostrarMensaje(mensajes.get("mensaje.carrito.noHay"));
        }
        carritoListarView.mostrarCarritos(carritos);
    }

    /**
     * Guarda el carrito actual en la base de datos.
     */
    private void guardarCarrito() {
        if (carritoActual.obtenerItems().isEmpty()) {
            carritoAñadirView.mostrarMensaje(mensajes.get("mensaje.carrito.vacio"));
            return;
        }

        // Reproducir sonido de confirmación
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

    /**
     * Añade un producto al carrito actual.
     */
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

    /**
     * Limpia el carrito actual después de confirmación.
     */
    private void limpiarCarrito() {
        int respuesta = JOptionPane.showConfirmDialog(carritoAñadirView,
                mensajes.get("yesNo.carrito.vaciar"),
                mensajes.get("yesNo.app.titulo"),
                JOptionPane.YES_NO_OPTION);

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