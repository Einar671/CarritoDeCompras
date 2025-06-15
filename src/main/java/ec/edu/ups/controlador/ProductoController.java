package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.ProductoAnadirView;
import ec.edu.ups.vista.ProductoEliminarView;
import ec.edu.ups.vista.ProductoListaView;

import java.awt.event.*;
import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoEliminarView productoEliminarView;
    private final ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO, ProductoAnadirView productoView, ProductoListaView productoListaView, ProductoEliminarView productoEliminarView) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoView;
        this.productoListaView = productoListaView;
        this.productoEliminarView = productoEliminarView;
        configurarEventos();
    }

    private void configurarEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
        productoListaView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                mostrarProductos();
            }

        });
        productoEliminarView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                mostrarProductos();
            }
            });

        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProductos();
            }
        });

        productoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               int codigo = Integer.parseInt(productoEliminarView.getTxtNombre().getText());
               Producto productoEncontrado = productoDAO.buscarPorCodigo(codigo);
               if (productoEncontrado == null) {
                   productoEliminarView.mostrarMensaje("El producto no existe");
               }else {
                   productoEliminarView.mostrarMensaje("El producto " + productoEncontrado.getNombre()+" fue eliminado correctamente");
                   productoDAO.eliminar(codigo);
                   productoEliminarView.getTxtNombre().setText("");
                   productoEliminarView.mostrarProductos(productoDAO.listarTodos());
               }
           }
        });


    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productos = productoDAO.buscarPorNombre(nombre);
        productoListaView.getTxtBuscar().setText("");
        productoListaView.mostrarProductos(productos);
    }
    private void mostrarProductos() {
        productoListaView.mostrarProductos(productoDAO.listarTodos());
        productoEliminarView.mostrarProductos(productoDAO.listarTodos());
    }
    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
    }

    private void buscarProductoCodigo() {
        String nombre = productoEliminarView.getTxtNombre().getText();
        List<Producto> productos = productoDAO.buscarPorNombre(nombre);
        productoListaView.getTxtBuscar().setText("");
        productoListaView.mostrarProductos(productos);
    }



}
