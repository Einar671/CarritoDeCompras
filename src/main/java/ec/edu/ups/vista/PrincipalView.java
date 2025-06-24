package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalView extends JFrame {
    private JMenuBar menubar;
    private JMenu menuCarrito;
    private JMenu menuProducto;
    private JMenu menuUsuario;
    private JMenu menuAdministrador;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemModificarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemCargarProducto;
    private JDesktopPane desktop;
    private JMenuItem menuItemAñadirCarrito;
    private JMenuItem menuItemListarCarritos;
    private JMenuItem menuItemEliminarCarrito;
    private JMenuItem menuItemModificarCarrito;
    private JMenuItem menuItemCrearUsuario;
    private JMenuItem menuItemEliminarUsuario;
    private JMenuItem menuItemListarUsuarios;
    private JMenuItem menuItemModificarUsuario;
    private JMenuItem menuItemCerrarSesión;




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
        menuUsuario = new JMenu("Usuario");
        menuAdministrador = new JMenu("Administrador");

        menuItemEliminarProducto = new JMenuItem("Eliminar");
        menuItemModificarProducto = new JMenuItem("Modificar");
        menuItemActualizarProducto = new JMenuItem("Actualizar");
        menuItemCargarProducto = new JMenuItem("Cargar");
        menuItemAñadirCarrito = new JMenuItem("Carrito Añadir");
        menuItemCerrarSesión = new JMenuItem("Cerrar Sesión");
        menuItemListarCarritos = new JMenuItem("Listar Carritos");
        menuItemEliminarCarrito = new JMenuItem("Eliminar Carrito");
        menuItemModificarCarrito = new JMenuItem("Modificar Carrito");
        menuItemCrearUsuario = new JMenuItem("Crear Usuario");
        menuItemEliminarUsuario = new JMenuItem("Eliminar Usuario");
        menuItemListarUsuarios = new JMenuItem("Listar Usuarios");
        menuItemModificarUsuario = new JMenuItem("Modificar Usuario");

        menubar.add(menuUsuario);
        menuUsuario.add(menuItemCerrarSesión);
        menubar.add(menuAdministrador);
        menuAdministrador.add(menuItemCrearUsuario);
        menuAdministrador.add(menuItemEliminarUsuario);
        menuAdministrador.add(menuItemListarUsuarios);
        menuAdministrador.add(menuItemModificarUsuario);
        menubar.add(menuProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemModificarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemCargarProducto);
        menubar.add(menuCarrito);
        menuCarrito.add(menuItemAñadirCarrito);
        menuCarrito.add(menuItemListarCarritos);
        menuCarrito.add(menuItemEliminarCarrito);
        menuCarrito.add(menuItemModificarCarrito);




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

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JMenuItem getMenuItemCerrarSesión() {
        return menuItemCerrarSesión;
    }

    public void setMenuItemCerrarSesión(JMenuItem menuItemCerrarSesión) {
        this.menuItemCerrarSesión = menuItemCerrarSesión;
    }

    public JMenuItem getMenuItemListarCarritos() {
        return menuItemListarCarritos;
    }

    public void setMenuItemListarCarritos(JMenuItem menuItemListarCarritos) {
        this.menuItemListarCarritos = menuItemListarCarritos;
    }

    public JMenu getMenuCarrito() {
        return menuCarrito;
    }

    public void setMenuCarrito(JMenu menuCarrito) {
        this.menuCarrito = menuCarrito;
    }

    public JMenu getMenuProducto() {
        return menuProducto;
    }

    public void setMenuProducto(JMenu menuProducto) {
        this.menuProducto = menuProducto;
    }

    public JMenu getMenuUsuario() {
        return menuUsuario;
    }

    public void setMenuUsuario(JMenu menuUsuario) {
        this.menuUsuario = menuUsuario;
    }

    public void desactivar() {
        getMenuProducto().setVisible(false);
        getMenuItemListarCarritos().setEnabled(false);
        getMenuItemEliminarProducto().setEnabled(false);
        getMenuItemModificarProducto().setEnabled(false);
        getMenuItemActualizarProducto().setEnabled(false);
        getMenuItemCargarProducto().setEnabled(false);
    }
}
