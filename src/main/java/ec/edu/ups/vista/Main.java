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

import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                //Iniciar Sesion
                UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
                LogInView logInView = new LogInView();
                logInView.setVisible(true);

                UsuarioController usuarioController = new UsuarioController(usuarioDAO, logInView);
                logInView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        Usuario usuarioAutentificacion = usuarioController.getUsuarioAutentificado();
                        if (usuarioAutentificacion != null) {

                            ProductoDAO productoDAO = new ProductoDAOMemoria();
                            CarritoDAO carritoDAO = new CarritoDAOMemoria();

                            ProductoModificarView productoModificarView = new ProductoModificarView();
                            ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                            CarritoAñadirView carritoAñadirView = new CarritoAñadirView();
                            PrincipalView principalView = new PrincipalView();
                            ProductoListaView productoListaView = new ProductoListaView();
                            ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                            CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, carritoAñadirView,usuarioAutentificacion);
                            ProductoController productoController = new ProductoController(productoDAO, carritoAñadirView, productoModificarView, productoEliminarView, productoListaView, productoAnadirView);

                            principalView.mostrarMensaje("Bienvenido al sistema"+usuarioAutentificacion);
                            if(usuarioAutentificacion.getRol().equals(Rol.USUARIO)) {
                                principalView.desactivar();
                            }

                            principalView.getMenuItemAñadirCarrito().addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!carritoAñadirView.isVisible()) {
                                        carritoAñadirView.setVisible(true);
                                        principalView.getDesktop().add(carritoAñadirView);
                                    }
                                }
                            });

                            principalView.getMenuItemCargarProducto().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!productoAnadirView.isVisible()) {
                                        productoAnadirView.setVisible(true);
                                        principalView.getDesktop().add(productoAnadirView);
                                    }
                                }
                            });

                            principalView.getMenuItemActualizarProducto().addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!productoListaView.isVisible()) {
                                        productoListaView.setVisible(true);
                                        principalView.getDesktop().add(productoListaView);
                                    }
                                }
                            });

                            principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!productoEliminarView.isVisible()) {
                                        productoEliminarView.setVisible(true);
                                        principalView.getDesktop().add(productoEliminarView);
                                    }
                                }
                            });

                            principalView.getMenuItemModificarProducto().addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!productoModificarView.isVisible()) {
                                        productoModificarView.setVisible(true);
                                        principalView.getDesktop().add(productoModificarView);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }

}