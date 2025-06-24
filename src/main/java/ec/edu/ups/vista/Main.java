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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    private static UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
    private static ProductoDAO productoDAO = new ProductoDAOMemoria();
    private static CarritoDAO carritoDAO = new CarritoDAOMemoria();

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> mostrarVentanaDeLogin());
    }

    public static void mostrarVentanaDeLogin() {
        LogInView logInView = new LogInView();
        UsuarioController usuarioController = new UsuarioController(usuarioDAO, logInView);

        logInView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Usuario usuarioAutenticado = usuarioController.getUsuarioAutentificado();
                if (usuarioAutenticado != null) {
                    iniciarAplicacionPrincipal(usuarioAutenticado);
                } else {
                    System.exit(0);
                }
            }
        });
        logInView.setVisible(true);
    }

    public static void iniciarAplicacionPrincipal(Usuario usuarioAutenticado) {
        PrincipalView principalView = new PrincipalView();
        ProductoModificarView productoModificarView = new ProductoModificarView();
        ProductoEliminarView productoEliminarView = new ProductoEliminarView();
        CarritoListarView carritoListarView = new CarritoListarView();
        CarritoAñadirView carritoAñadirView = new CarritoAñadirView();
        ProductoListaView productoListaView = new ProductoListaView();
        ProductoAnadirView productoAnadirView = new ProductoAnadirView();

        CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, carritoAñadirView,carritoListarView, usuarioAutenticado);
        ProductoController productoController = new ProductoController(productoDAO, carritoAñadirView, productoModificarView, productoEliminarView, productoListaView, productoAnadirView);

        principalView.mostrarMensaje("Bienvenido al sistema, " + usuarioAutenticado.getUsername());
        if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
            principalView.desactivar();
        }

        configurarMenu(principalView, carritoAñadirView, productoAnadirView, productoListaView, productoEliminarView, productoModificarView,carritoListarView);

        principalView.setVisible(true);
    }

    private static void configurarMenu(PrincipalView principalView, CarritoAñadirView carritoAñadirView, ProductoAnadirView productoAnadirView, ProductoListaView productoListaView, ProductoEliminarView productoEliminarView, ProductoModificarView productoModificarView, CarritoListarView carritoListarView) {

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
        principalView.getMenuItemListarCarritos().addActionListener(e -> {
           if(!carritoListarView.isVisible()){
               principalView.getDesktop().add(carritoListarView);
               carritoListarView.setVisible(true);
           }
           carritoListarView.toFront();
        });
    }
}