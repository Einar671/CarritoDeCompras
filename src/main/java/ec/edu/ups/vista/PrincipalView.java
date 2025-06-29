package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalView extends JFrame {
    private JMenuBar menubar;
    private JMenu menuCarrito;
    private JMenu menuProducto;
    private JMenu menuUsuario;
    private JMenu menuAdministrador;
    private JMenu menuIdiomas;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemModificarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemCargarProducto;
    private MiJDesktopPane desktop;
    private JMenuItem menuItemAñadirCarrito;
    private JMenuItem menuItemListarCarritos;
    private JMenuItem menuItemEliminarCarrito;
    private JMenuItem menuItemModificarCarrito;
    private JMenuItem menuItemListarCarritoMis;
    private JMenuItem menuItemCrearUsuario;
    private JMenuItem menuItemEliminarUsuario;
    private JMenuItem menuItemListarUsuarios;
    private JMenuItem menuItemModificarUsuario;
    private JMenuItem menuItemModificarMisUsuario;
    private JMenuItem menuItemCerrarSesión;
    private JMenuItem menuItemEspañol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemNoruego;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

    public PrincipalView(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;

        desktop = new MiJDesktopPane(mensajeInternacionalizacionHandler);
        menubar = new JMenuBar();
        menuCarrito = new JMenu();
        menuProducto = new JMenu();
        menuUsuario = new JMenu();
        menuAdministrador = new JMenu();
        menuIdiomas = new JMenu();

        menuItemEliminarProducto = new JMenuItem();
        menuItemModificarProducto = new JMenuItem();
        menuItemActualizarProducto = new JMenuItem();
        menuItemCargarProducto = new JMenuItem();
        menuItemAñadirCarrito = new JMenuItem();
        menuItemCerrarSesión = new JMenuItem();
        menuItemListarCarritos = new JMenuItem();
        menuItemEliminarCarrito = new JMenuItem();
        menuItemModificarCarrito = new JMenuItem();
        menuItemListarCarritoMis = new JMenuItem();
        menuItemCrearUsuario = new JMenuItem();
        menuItemEliminarUsuario = new JMenuItem();
        menuItemListarUsuarios = new JMenuItem();
        menuItemModificarUsuario = new JMenuItem();
        menuItemModificarMisUsuario = new JMenuItem();
        menuItemEspañol = new JMenuItem();
        menuItemIngles = new JMenuItem();
        menuItemNoruego = new JMenuItem();

        actualizarTextos();

        menubar.add(menuUsuario);
        menuUsuario.add(menuItemCerrarSesión);
        menuUsuario.add(menuItemModificarMisUsuario);

        menubar.add(menuAdministrador);
        menuAdministrador.add(menuItemCrearUsuario);
        menuAdministrador.add(menuItemEliminarUsuario);
        menuAdministrador.add(menuItemListarUsuarios);
        menuAdministrador.add(menuItemModificarUsuario);

        menubar.add(menuProducto);
        menuProducto.add(menuItemCargarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemModificarProducto);
        menuProducto.add(menuItemEliminarProducto);


        menubar.add(menuCarrito);
        menuCarrito.add(menuItemAñadirCarrito);
        menuCarrito.add(menuItemListarCarritoMis);
        menuCarrito.add(menuItemListarCarritos);
        menuCarrito.add(menuItemEliminarCarrito);
        menuCarrito.add(menuItemModificarCarrito);

        menubar.add(menuIdiomas);
        menuIdiomas.add(menuItemEspañol);
        menuIdiomas.add(menuItemIngles);
        menuIdiomas.add(menuItemNoruego);

        setJMenuBar(menubar);
        setContentPane(desktop);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void actualizarTextos() {
        setTitle(mensajeInternacionalizacionHandler.get("app.titulo"));
        desktop.actualizarTextos();
        menuProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto"));
        menuItemCargarProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.crear"));
        menuItemActualizarProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.buscar"));
        menuItemModificarProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.actualizar"));
        menuItemEliminarProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.eliminar"));

        menuCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito"));
        menuItemAñadirCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito.crear"));
        menuItemListarCarritos.setText(mensajeInternacionalizacionHandler.get("menu.carrito.listar"));
        menuItemEliminarCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito.eliminar"));
        menuItemModificarCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito.actualizar"));
        menuItemListarCarritoMis.setText(mensajeInternacionalizacionHandler.get("menu.carrito.listarMis"));

        menuAdministrador.setText(mensajeInternacionalizacionHandler.get("menu.administrador"));
        menuItemCrearUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usuario.crear"));
        menuItemListarUsuarios.setText(mensajeInternacionalizacionHandler.get("menu.usuario.listar"));
        menuItemEliminarUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usuario.eliminar"));
        menuItemModificarUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usuario.modificar"));

        menuUsuario.setText(mensajeInternacionalizacionHandler.get("global.usuario"));
        menuItemModificarMisUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usuario.modificarMis"));
        menuItemCerrarSesión.setText(mensajeInternacionalizacionHandler.get("menu.salir.cerrar"));

        menuIdiomas.setText(mensajeInternacionalizacionHandler.get("menu.idiomas"));
        menuItemEspañol.setText(mensajeInternacionalizacionHandler.get("menu.idioma.es"));
        menuItemIngles.setText(mensajeInternacionalizacionHandler.get("menu.idioma.en"));
        menuItemNoruego.setText(mensajeInternacionalizacionHandler.get("menu.idioma.nw"));
    }




    public JDesktopPane getDesktop() {
        return desktop;
    }

    public void setDesktop(MiJDesktopPane desktop) {
        this.desktop = desktop;
    }

    public JMenuItem getMenuItemModificarMisUsuario() {
        return menuItemModificarMisUsuario;
    }

    public void setMenuItemModificarMisUsuario(JMenuItem menuItemModificarMisUsuario) {
        this.menuItemModificarMisUsuario = menuItemModificarMisUsuario;
    }

    public JMenu getMenuIdiomas() {
        return menuIdiomas;
    }

    public void setMenuIdiomas(JMenu menuIdiomas) {
        this.menuIdiomas = menuIdiomas;
    }

    public JMenuItem getMenuItemListarCarritoMis() {
        return menuItemListarCarritoMis;
    }

    public void setMenuItemListarCarritoMis(JMenuItem menuItemListarCarritoMis) {
        this.menuItemListarCarritoMis = menuItemListarCarritoMis;
    }

    public JMenuItem getMenuItemEspañol() {
        return menuItemEspañol;
    }

    public void setMenuItemEspañol(JMenuItem menuItemEspañol) {
        this.menuItemEspañol = menuItemEspañol;
    }

    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    public void setMenuItemIngles(JMenuItem menuItemIngles) {
        this.menuItemIngles = menuItemIngles;
    }

    public JMenuItem getMenuItemNoruego() {
        return menuItemNoruego;
    }

    public void setMenuItemNoruego(JMenuItem menuItemNoruego) {
        this.menuItemNoruego = menuItemNoruego;
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

    public JMenuBar getMenubar() {
        return menubar;
    }

    public void setMenubar(JMenuBar menubar) {
        this.menubar = menubar;
    }

    public JMenu getMenuAdministrador() {
        return menuAdministrador;
    }

    public void setMenuAdministrador(JMenu menuAdministrador) {
        this.menuAdministrador = menuAdministrador;
    }

    public JMenuItem getMenuItemEliminarCarrito() {
        return menuItemEliminarCarrito;
    }

    public void setMenuItemEliminarCarrito(JMenuItem menuItemEliminarCarrito) {
        this.menuItemEliminarCarrito = menuItemEliminarCarrito;
    }

    public JMenuItem getMenuItemModificarCarrito() {
        return menuItemModificarCarrito;
    }

    public void setMenuItemModificarCarrito(JMenuItem menuItemModificarCarrito) {
        this.menuItemModificarCarrito = menuItemModificarCarrito;
    }

    public JMenuItem getMenuItemCrearUsuario() {
        return menuItemCrearUsuario;
    }

    public void setMenuItemCrearUsuario(JMenuItem menuItemCrearUsuario) {
        this.menuItemCrearUsuario = menuItemCrearUsuario;
    }

    public JMenuItem getMenuItemEliminarUsuario() {
        return menuItemEliminarUsuario;
    }

    public void setMenuItemEliminarUsuario(JMenuItem menuItemEliminarUsuario) {
        this.menuItemEliminarUsuario = menuItemEliminarUsuario;
    }

    public JMenuItem getMenuItemListarUsuarios() {
        return menuItemListarUsuarios;
    }

    public void setMenuItemListarUsuarios(JMenuItem menuItemListarUsuarios) {
        this.menuItemListarUsuarios = menuItemListarUsuarios;
    }

    public JMenuItem getMenuItemModificarUsuario() {
        return menuItemModificarUsuario;
    }

    public void setMenuItemModificarUsuario(JMenuItem menuItemModificarUsuario) {
        this.menuItemModificarUsuario = menuItemModificarUsuario;
    }

    public void desactivar() {
        getMenuProducto().setVisible(false);
        getMenuAdministrador().setVisible(false);
        getMenuItemEliminarCarrito().setEnabled(false);
        getMenuItemModificarCarrito().setEnabled(false);
        getMenuItemListarCarritos().setEnabled(false);
        getMenuItemEliminarProducto().setEnabled(false);
        getMenuItemModificarProducto().setEnabled(false);
        getMenuItemActualizarProducto().setEnabled(false);
        getMenuItemCargarProducto().setEnabled(false);
    }
}