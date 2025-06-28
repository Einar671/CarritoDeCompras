package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler; // Importar el handler

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    private static final UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
    private static final ProductoDAO productoDAO = new ProductoDAOMemoria();
    private static final CarritoDAO carritoDAO = new CarritoDAOMemoria();

    private static final MensajeInternacionalizacionHandler mensajes = new MensajeInternacionalizacionHandler("es", "EC");
    private static PrincipalView principalView;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(Main::mostrarVentanaDeLogin);
    }

    public static void mostrarVentanaDeLogin() {
        LogInView loginView = new LogInView(mensajes);
        UsuarioCrearView usuarioCrearView = new UsuarioCrearView(mensajes);
        UsuarioModificarView usuarioModificarView = new UsuarioModificarView(mensajes);
        UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView(mensajes);
        UsuarioListarView usuarioListarView = new UsuarioListarView(mensajes);
        UsuarioModificarMisView usuarioModificarMisView = new UsuarioModificarMisView(mensajes);

        UsuarioController usuarioController = new UsuarioController(usuarioCrearView, usuarioDAO, loginView, usuarioModificarView, usuarioEliminarView, usuarioModificarMisView, usuarioListarView, mensajes);

        loginView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Usuario usuarioAutenticado = usuarioController.getUsuarioAutentificado();
                if (usuarioAutenticado != null) {
                    iniciarAplicacionPrincipal(loginView,usuarioAutenticado, usuarioCrearView, usuarioModificarView, usuarioEliminarView, usuarioModificarMisView, usuarioListarView);
                } else {
                    System.exit(0);
                }
            }
        });

        loginView.setVisible(true);
    }

    public static void iniciarAplicacionPrincipal(LogInView logInView, Usuario usuarioAutenticado, UsuarioCrearView usuarioCrearView, UsuarioModificarView usuarioModificarView, UsuarioEliminarView usuarioEliminarView, UsuarioModificarMisView usuarioModificarMisView, UsuarioListarView usuarioListarView) {

        principalView = new PrincipalView(mensajes);
        ProductoModificarView productoModificarView = new ProductoModificarView(mensajes);
        ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensajes);
        CarritoListarView carritoListarView = new CarritoListarView(mensajes);
        CarritoAñadirView carritoAñadirView = new CarritoAñadirView(mensajes);
        ProductoListaView productoListaView = new ProductoListaView(mensajes);
        ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensajes);
        CarritoModificarView carritoModificarView = new CarritoModificarView(mensajes);
        CarritoEliminarView carritoEliminarView = new CarritoEliminarView(mensajes);
        CarritoListarMisView carritoListarMisView = new CarritoListarMisView(mensajes);

        CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, carritoAñadirView, carritoListarView, carritoModificarView, carritoEliminarView, carritoListarMisView, usuarioAutenticado, mensajes);
        ProductoController productoController = new ProductoController(productoDAO, carritoAñadirView, productoModificarView, productoEliminarView, productoListaView, productoAnadirView, mensajes);

        principalView.mostrarMensaje(mensajes.get("mensaje.usuario.login.exito") + " " + usuarioAutenticado.getUsername() + "!");
        if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
            principalView.desactivar();
        }

        configurarMenu(principalView, carritoAñadirView, productoAnadirView, productoListaView, productoEliminarView,
                productoModificarView, carritoListarView, carritoModificarView, carritoEliminarView, carritoListarMisView,
                usuarioCrearView, usuarioModificarView, usuarioEliminarView, usuarioModificarMisView, usuarioListarView,logInView);

        principalView.setVisible(true);
    }

    private static void cambiarIdioma(String idioma, String pais) {
        mensajes.setLenguaje(idioma, pais);
        if (principalView != null) {
            principalView.actualizarTextos();
        }

    }

    private static void configurarMenu(PrincipalView principalView, CarritoAñadirView carritoAñadirView, ProductoAnadirView productoAnadirView,
                                       ProductoListaView productoListaView, ProductoEliminarView productoEliminarView,
                                       ProductoModificarView productoModificarView, CarritoListarView carritoListarView,
                                       CarritoModificarView carritoModificarView, CarritoEliminarView carritoEliminarView, CarritoListarMisView carritoListarMisView,
                                       UsuarioCrearView usuarioCrearView, UsuarioModificarView usuarioModificarView, UsuarioEliminarView usuarioEliminarView,
                                       UsuarioModificarMisView usuarioModificarMisView, UsuarioListarView usuarioListarView,LogInView logInView) {

        principalView.getMenuItemEspañol().addActionListener(e -> cambiarIdioma("es", "EC"));
        principalView.getMenuItemIngles().addActionListener(e -> cambiarIdioma("en", "US"));
        principalView.getMenuItemNoruego().addActionListener(e -> cambiarIdioma("nk", "NW"));

        principalView.getMenuItemCerrarSesión().addActionListener(e -> {
            principalView.dispose();
            mostrarVentanaDeLogin();
        });

        principalView.getMenuItemAñadirCarrito().addActionListener(e -> {
            if (!carritoAñadirView.isVisible()) {
                principalView.getDesktop().add(carritoAñadirView);
                carritoAñadirView.setVisible(true);
            }
            carritoAñadirView.toFront();
        });
        principalView.getMenuItemListarCarritos().addActionListener(e -> {
            if (!carritoListarView.isVisible()) {
                principalView.getDesktop().add(carritoListarView);
                carritoListarView.setVisible(true);
            }
            carritoListarView.toFront();
        });
        principalView.getMenuItemModificarCarrito().addActionListener(e -> {
            if (!carritoModificarView.isVisible()) {
                principalView.getDesktop().add(carritoModificarView);
                carritoModificarView.setVisible(true);
            }
            carritoModificarView.toFront();
        });
        principalView.getMenuItemEliminarCarrito().addActionListener(e -> {
            if (!carritoEliminarView.isVisible()) {
                principalView.getDesktop().add(carritoEliminarView);
                carritoEliminarView.setVisible(true);
            }
            carritoEliminarView.toFront();
        });
        principalView.getMenuItemCargarProducto().addActionListener(e -> {
            if (!productoAnadirView.isVisible()) {
                principalView.getDesktop().add(productoAnadirView);
                productoAnadirView.setVisible(true);
            }
            productoAnadirView.toFront();
        });
        principalView.getMenuItemActualizarProducto().addActionListener(e -> {
            if (!productoListaView.isVisible()) {
                principalView.getDesktop().add(productoListaView);
                productoListaView.setVisible(true);
            }
            productoListaView.toFront();
        });
        principalView.getMenuItemEliminarProducto().addActionListener(e -> {
            if (!productoEliminarView.isVisible()) {
                principalView.getDesktop().add(productoEliminarView);
                productoEliminarView.setVisible(true);
            }
            productoEliminarView.toFront();
        });
        principalView.getMenuItemModificarProducto().addActionListener(e -> {
            if (!productoModificarView.isVisible()) {
                principalView.getDesktop().add(productoModificarView);
                productoModificarView.setVisible(true);
            }
            productoModificarView.toFront();
        });
        principalView.getMenuItemCrearUsuario().addActionListener(e -> {
            if (!usuarioCrearView.isVisible()) {
                principalView.getDesktop().add(usuarioCrearView);
                usuarioCrearView.setVisible(true);
            }
            usuarioCrearView.toFront();
        });
        principalView.getMenuItemModificarUsuario().addActionListener(e -> {
            if (!usuarioModificarView.isVisible()) {
                principalView.getDesktop().add(usuarioModificarView);
                usuarioModificarView.setVisible(true);
            }
            usuarioModificarView.toFront();
        });
        principalView.getMenuItemEliminarUsuario().addActionListener(e -> {
            if (!usuarioEliminarView.isVisible()) {
                principalView.getDesktop().add(usuarioEliminarView);
                usuarioEliminarView.setVisible(true);
            }
            usuarioEliminarView.toFront();
        });
        principalView.getMenuItemListarUsuarios().addActionListener(e -> {
            if (!usuarioListarView.isVisible()) {
                principalView.getDesktop().add(usuarioListarView);
                usuarioListarView.setVisible(true);
            }
            usuarioListarView.toFront();
        });
        principalView.getMenuItemListarCarritoMis().addActionListener(e -> {
            if (!carritoListarMisView.isVisible()) {
                principalView.getDesktop().add(carritoListarMisView);
                carritoListarMisView.setVisible(true);
            }
            carritoListarMisView.toFront();
        });
        principalView.getMenuItemModificarMisUsuario().addActionListener(e -> {
            if (!usuarioModificarMisView.isVisible()) {
                principalView.getDesktop().add(usuarioModificarMisView);
                usuarioModificarMisView.setVisible(true);
            }
            usuarioModificarMisView.toFront();
        });

        principalView.getMenuItemIngles().addActionListener(e->{
            cambiarIdioma("en","US");
            principalView.actualizarTextos();
            carritoAñadirView.actualizarTextos();
            carritoEliminarView.actualizarTextos();
            carritoListarView.actualizarTextos();
            carritoListarMisView.actualizarTextos();
            carritoModificarView.actualizarTextos();
            productoAnadirView.actualizarTextos();
            productoEliminarView.actualizarTextos();
            productoListaView.actualizarTextos();
            productoModificarView.actualizarTextos();
            usuarioCrearView.actualizarTextos();
            usuarioEliminarView.actualizarTextos();
            usuarioListarView.actualizarTextos();
            usuarioModificarMisView.actualizarTextos();
            usuarioModificarView.actualizarTextos();
            logInView.actualizarTextos();

        });
        principalView.getMenuItemNoruego().addActionListener(e->{
            cambiarIdioma("nk","NK");
            principalView.actualizarTextos();
            carritoAñadirView.actualizarTextos();
            carritoEliminarView.actualizarTextos();
            carritoListarView.actualizarTextos();
            carritoListarMisView.actualizarTextos();
            carritoModificarView.actualizarTextos();
            productoAnadirView.actualizarTextos();
            productoEliminarView.actualizarTextos();
            productoListaView.actualizarTextos();
            productoModificarView.actualizarTextos();
            usuarioCrearView.actualizarTextos();
            usuarioEliminarView.actualizarTextos();
            usuarioListarView.actualizarTextos();
            usuarioModificarMisView.actualizarTextos();
            usuarioModificarView.actualizarTextos();
            logInView.actualizarTextos();

        });
    }
}