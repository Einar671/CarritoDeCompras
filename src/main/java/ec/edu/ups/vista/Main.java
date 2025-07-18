package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*; // Importar JInternalFrame
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays; // Importar Arrays
import java.util.List;   // Importar List

public class Main {

    private static UsuarioDAO usuarioDAO;
    private static ProductoDAO productoDAO;
    private static CarritoDAO carritoDAO;
    private static PreguntaDAO preguntaDAO;

    private static final MensajeInternacionalizacionHandler mensajes = new MensajeInternacionalizacionHandler("en", "US");
    private static PrincipalView principalView;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(Main::seleccionarTipoDePersistencia);
    }

    public static void seleccionarTipoDePersistencia() {
        SeleccionarDAO seleccionarDAOView = new SeleccionarDAO(mensajes);

        seleccionarDAOView.getMenuItemEspañol().addActionListener(e -> {
            mensajes.setLenguaje("es", "EC");
            seleccionarDAOView.actualizar();
        });
        seleccionarDAOView.getMenuItemIngles().addActionListener(e -> {
            mensajes.setLenguaje("en", "US");
            seleccionarDAOView.actualizar();
        });
        seleccionarDAOView.getMenuItemNoruego().addActionListener(e -> {
            mensajes.setLenguaje("nk", "NK");
            seleccionarDAOView.actualizar();
        });

        seleccionarDAOView.getBtnAceptar().addActionListener(e -> {
            boolean memoriaSeleccionada = seleccionarDAOView.getCbxMemoria().isSelected();
            boolean archivoSeleccionado = seleccionarDAOView.getCbxArchivo().isSelected();

            if (memoriaSeleccionada) {
                usuarioDAO = new UsuarioDAOMemoria();
                productoDAO = new ProductoDAOMemoria();
                carritoDAO = new CarritoDAOMemoria();
                preguntaDAO = new PreguntaDAOMemoria(mensajes);
                seleccionarDAOView.dispose();
                mostrarVentanaDeLogin();
            } else if (archivoSeleccionado) {
                JFileChooser fileChooser = seleccionarDAOView.getArchivos();
                fileChooser.setDialogTitle(mensajes.get("seleccionadorDAO.dialogo.titulo"));

                int resultado = fileChooser.showOpenDialog(seleccionarDAOView);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                    java.io.File carpetaSeleccionada = fileChooser.getSelectedFile();
                    String rutaBase = carpetaSeleccionada.getAbsolutePath();

                    String rutaUsuarios = rutaBase + java.io.File.separator + "usuarios.txt";
                    String rutaCarritos = rutaBase + java.io.File.separator + "carritos.txt";
                    String rutaProductos = rutaBase + java.io.File.separator + "productos.dat";
                    String rutaPreguntas = rutaBase + java.io.File.separator + "preguntas.dat";

                    usuarioDAO = new UsuarioDAOArchivoTexto(rutaUsuarios);
                    productoDAO = new ProductoDAOArchivoBinario(rutaProductos);
                    preguntaDAO = new PreguntaDAOArchivoBinario(rutaPreguntas, mensajes);
                    carritoDAO = new CarritoDAOArchivoTexto(rutaCarritos, usuarioDAO, productoDAO);

                    seleccionarDAOView.dispose();
                    mostrarVentanaDeLogin();
                }
            } else {
                seleccionarDAOView.mostrarMensaje(mensajes.get("mensaje.seleccionadorDAO.noSeleccionado"));
            }
        });

        seleccionarDAOView.setVisible(true);
    }

    private static void mostrarVentanaDeLogin() {
        LogInView loginView = new LogInView(mensajes);
        UsuarioCrearView usuarioCrearView = new UsuarioCrearView(mensajes);
        UsuarioModificarView usuarioModificarView = new UsuarioModificarView(mensajes);
        UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView(mensajes);
        UsuarioListarView usuarioListarView = new UsuarioListarView(mensajes);
        UsuarioModificarMisView usuarioModificarMisView = new UsuarioModificarMisView(mensajes);
        RegistrarseView registrarseView = new RegistrarseView(mensajes);
        PreguntasRegisterView preguntasView = new PreguntasRegisterView(mensajes);
        PreguntasModificarView preguntasModificarView = new PreguntasModificarView(mensajes);

        registrarseView.getMenuItemEspañol().addActionListener(e -> {
            mensajes.setLenguaje("es", "EC");
            loginView.actualizarTextos();
            registrarseView.actualizarTextos();
            preguntasView.actualizarTextos();
            preguntasModificarView.actualizarTextos();
        });
        registrarseView.getMenuItemIngles().addActionListener(e -> {
            mensajes.setLenguaje("en", "US");
            loginView.actualizarTextos();
            registrarseView.actualizarTextos();
            preguntasView.actualizarTextos();
            preguntasModificarView.actualizarTextos();
        });
        registrarseView.getMenuItemNoruego().addActionListener(e -> {
            mensajes.setLenguaje("nk", "NK");
            loginView.actualizarTextos();
            registrarseView.actualizarTextos();
            preguntasView.actualizarTextos();
            preguntasModificarView.actualizarTextos();        });

        loginView.getMenuItemEspañol().addActionListener(e -> {
            mensajes.setLenguaje("es", "EC");
            loginView.actualizarTextos();
            registrarseView.actualizarTextos();
            preguntasView.actualizarTextos();
            preguntasModificarView.actualizarTextos();
        });
        loginView.getMenuItemIngles().addActionListener(e -> {
            mensajes.setLenguaje("en", "US");
            loginView.actualizarTextos();
            registrarseView.actualizarTextos();
            preguntasView.actualizarTextos();
            preguntasModificarView.actualizarTextos();
        });
        loginView.getMenuItemNoruego().addActionListener(e -> {
            mensajes.setLenguaje("nk", "NK");
            loginView.actualizarTextos();
            registrarseView.actualizarTextos();
            preguntasView.actualizarTextos();
            preguntasModificarView.actualizarTextos();        });

        UsuarioController usuarioController = new UsuarioController(usuarioCrearView, usuarioDAO, preguntaDAO, loginView, usuarioModificarView, usuarioEliminarView, usuarioModificarMisView, usuarioListarView, mensajes, registrarseView, preguntasView, preguntasModificarView);

        loginView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Usuario usuarioAutenticado = usuarioController.getUsuarioAutentificado();
                if (usuarioAutenticado != null) {
                    iniciarAplicacionPrincipal(usuarioAutenticado, usuarioCrearView, usuarioModificarView, usuarioEliminarView, usuarioModificarMisView, usuarioListarView, registrarseView, preguntasView, preguntasModificarView);
                } else {
                    System.exit(0);
                }
            }
        });

        loginView.setVisible(true);
    }

    private static void iniciarAplicacionPrincipal(Usuario usuarioAutenticado, UsuarioCrearView usuarioCrearView, UsuarioModificarView usuarioModificarView, UsuarioEliminarView usuarioEliminarView, UsuarioModificarMisView usuarioModificarMisView, UsuarioListarView usuarioListarView, RegistrarseView registrarseView, PreguntasRegisterView preguntasView, PreguntasModificarView preguntasModificarView) {

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

        List<JInternalFrame> vistasInternas = Arrays.asList(
                carritoAñadirView, productoAnadirView, productoListaView, productoEliminarView,
                productoModificarView, carritoListarView, carritoModificarView, carritoEliminarView,
                carritoListarMisView, usuarioCrearView, usuarioModificarView, usuarioEliminarView,
                usuarioModificarMisView, usuarioListarView
        );

        CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, carritoAñadirView, carritoListarView, carritoModificarView, carritoEliminarView, carritoListarMisView, usuarioAutenticado, mensajes);
        ProductoController productoController = new ProductoController(productoDAO, carritoAñadirView, productoModificarView, productoEliminarView, productoListaView, productoAnadirView, mensajes);

        principalView.mostrarMensaje(mensajes.get("mensaje.usuario.login.exito") + " " + usuarioAutenticado.getUsername() + "!");
        if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
            principalView.desactivar();
        }

        configurarMenu(principalView, carritoAñadirView, productoAnadirView, productoListaView, productoEliminarView,
                productoModificarView, carritoListarView, carritoModificarView, carritoEliminarView, carritoListarMisView,
                usuarioCrearView, usuarioModificarView, usuarioEliminarView, usuarioModificarMisView, usuarioListarView,
                registrarseView,preguntasView, vistasInternas);

        principalView.setVisible(true);
    }


    private static void actualizarTodasLasVistas(List<JInternalFrame> vistas) {
        if (principalView != null) {
            principalView.actualizarTextos();
        }
        for (JInternalFrame vista : vistas) {
            if (vista instanceof CarritoAñadirView) ((CarritoAñadirView) vista).actualizarTextos();
            else if (vista instanceof CarritoEliminarView) ((CarritoEliminarView) vista).actualizarTextos();
            else if (vista instanceof CarritoListarView) ((CarritoListarView) vista).actualizarTextos();
            else if (vista instanceof CarritoListarMisView) ((CarritoListarMisView) vista).actualizarTextos();
            else if (vista instanceof CarritoModificarView) ((CarritoModificarView) vista).actualizarTextos();
            else if (vista instanceof ProductoAnadirView) ((ProductoAnadirView) vista).actualizarTextos();
            else if (vista instanceof ProductoEliminarView) ((ProductoEliminarView) vista).actualizarTextos();
            else if (vista instanceof ProductoListaView) ((ProductoListaView) vista).actualizarTextos();
            else if (vista instanceof ProductoModificarView) ((ProductoModificarView) vista).actualizarTextos();
            else if (vista instanceof UsuarioCrearView) ((UsuarioCrearView) vista).actualizarTextos();
            else if (vista instanceof UsuarioEliminarView) ((UsuarioEliminarView) vista).actualizarTextos();
            else if (vista instanceof UsuarioListarView) ((UsuarioListarView) vista).actualizarTextos();
            else if (vista instanceof UsuarioModificarMisView) ((UsuarioModificarMisView) vista).actualizarTextos();
            else if (vista instanceof UsuarioModificarView) ((UsuarioModificarView) vista).actualizarTextos();

        }
    }

    private static void configurarMenu(PrincipalView principalView, CarritoAñadirView carritoAñadirView, ProductoAnadirView productoAnadirView,
                                       ProductoListaView productoListaView, ProductoEliminarView productoEliminarView,
                                       ProductoModificarView productoModificarView, CarritoListarView carritoListarView,
                                       CarritoModificarView carritoModificarView, CarritoEliminarView carritoEliminarView, CarritoListarMisView carritoListarMisView,
                                       UsuarioCrearView usuarioCrearView, UsuarioModificarView usuarioModificarView, UsuarioEliminarView usuarioEliminarView,
                                       UsuarioModificarMisView usuarioModificarMisView, UsuarioListarView usuarioListarView, RegistrarseView registrarseView, PreguntasRegisterView preguntasView, List<JInternalFrame> vistas) {
        principalView.getMenuItemCerrarSesión().addActionListener(e -> {
            principalView.dispose();
            mostrarVentanaDeLogin();
        });
        principalView.getMenuItemAñadirCarrito().addActionListener(e -> {
            JInternalFrame vista = vistas.stream().filter(v -> v instanceof CarritoAñadirView).findFirst().get();
            if (!vista.isVisible()) {
                principalView.getDesktop().add(vista);
                vista.setVisible(true);
            }
            vista.toFront();
        });
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
        principalView.getMenuItemIngles().addActionListener(e -> {
            mensajes.setLenguaje("en", "US");
            actualizarTodasLasVistas(vistas);
        });
        principalView.getMenuItemNoruego().addActionListener(e -> {
            mensajes.setLenguaje("nk", "NK");
            actualizarTodasLasVistas(vistas);
        });
        principalView.getMenuItemEspañol().addActionListener(e -> {
            mensajes.setLenguaje("es", "EC");
            actualizarTodasLasVistas(vistas);
        });
    }
}