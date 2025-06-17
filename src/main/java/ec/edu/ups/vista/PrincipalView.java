package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalView extends JFrame {
    private JMenuBar menubar;
    private JMenu menuCarrito;
    private JMenu menuProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemModificarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemCargarProducto;
    private JDesktopPane desktop;
    private JMenuItem menuItemAñadirCarrito;

    public JDesktopPane getDesktop() {
        return desktop;
    }

    public void setDesktop(JDesktopPane desktop) {
        this.desktop = desktop;
    }

    public PrincipalView() {
        desktop = new JDesktopPane();
        menubar = new JMenuBar();
        menuCarrito = new JMenu("Carrito");
        menuProducto = new JMenu("Producto");
        menuItemEliminarProducto = new JMenuItem("Eliminar");
        menuItemModificarProducto = new JMenuItem("Modificar");
        menuItemActualizarProducto = new JMenuItem("Actualizar");
        menuItemCargarProducto = new JMenuItem("Cargar");
        menuItemAñadirCarrito = new JMenuItem("Carrito Añadir");

        menubar.add(menuCarrito);
        menuCarrito.add(menuItemAñadirCarrito);
        menubar.add(menuProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemModificarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemCargarProducto);



        setJMenuBar(menubar);
        setContentPane(desktop);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sitema de CArrito de Compras En Linea");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    public JMenuItem getMenuItemAñadirCarrito() {
        return menuItemAñadirCarrito;
    }

    public void setMenuItemAñadirCarrito(JMenuItem menuItemAñadirCarrito) {
        this.menuItemAñadirCarrito = menuItemAñadirCarrito;
    }

    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) {
        this.menuItemEliminarProducto = menuItemEliminarProducto;
    }

    public JMenuItem getMenuItemModificarProducto() {
        return menuItemModificarProducto;
    }

    public void setMenuItemModificarProducto(JMenuItem menuItemModificarProducto) {
        this.menuItemModificarProducto = menuItemModificarProducto;
    }

    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    public void setMenuItemActualizarProducto(JMenuItem menuItemActualizarProducto) {
        this.menuItemActualizarProducto = menuItemActualizarProducto;
    }

    public JMenuItem getMenuItemCargarProducto() {
        return menuItemCargarProducto;
    }

    public void setMenuItemCargarProducto(JMenuItem menuItemCargarProducto) {
        this.menuItemCargarProducto = menuItemCargarProducto;
    }
}
