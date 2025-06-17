package ec.edu.ups.vista;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                ProductoDAO productoDAO = new ProductoDAOMemoria();
                ProductoModificarView productoModificarView = new ProductoModificarView();
                ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                PrincipalView principalView = new PrincipalView();
                ProductoListaView productoListaView = new ProductoListaView();
                ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                ProductoController productoController = new ProductoController(productoDAO);


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
                        if(!productoListaView.isVisible()) {
                            productoListaView.setVisible(true);
                            principalView.getDesktop().add(productoListaView);
                        }
                    }
                });

                principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!productoEliminarView.isVisible()) {
                            productoEliminarView.setVisible(true);
                            principalView.getDesktop().add(productoEliminarView);
                        }
                    }
                });

                principalView.getMenuItemModificarProducto().addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!productoModificarView.isVisible()) {
                            productoModificarView.setVisible(true);
                            principalView.getDesktop().add(productoModificarView);
                        }
                    }
                });
            }
        });


    }
}