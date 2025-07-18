package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * La ventana principal de la aplicación que se muestra después de un inicio de sesión exitoso.
 * <p>
 * Esta clase extiende {@link JFrame} y actúa como el contenedor principal para todas
 * las demás vistas internas (JInternalFrame). Contiene una barra de menú ({@link JMenuBar})
 * para navegar por las diferentes funcionalidades de la aplicación y un
 * {@link MiJDesktopPane} personalizado como panel de contenido.
 * <p>
 * La visibilidad de los menús y sus ítems se puede ajustar según el rol del usuario.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class PrincipalView extends JFrame {
    /**
     * La barra de menú principal de la ventana.
     */
    private JMenuBar menubar;
    /**
     * Menú para las operaciones relacionadas con el carrito de compras.
     */
    private JMenu menuCarrito;
    /**
     * Menú para las operaciones relacionadas con los productos.
     */
    private JMenu menuProducto;
    /**
     * Menú para las operaciones del usuario sobre su propia cuenta.
     */
    private JMenu menuUsuario;
    /**
     * Menú para las operaciones de administración de usuarios, visible solo para administradores.
     */
    private JMenu menuAdministrador;
    /**
     * Menú para cambiar el idioma de la aplicación.
     */
    private JMenu menuIdiomas;
    /**
     * Opción de menú para abrir la vista de eliminación de productos.
     */
    private JMenuItem menuItemEliminarProducto;
    /**
     * Opción de menú para abrir la vista de modificación de productos.
     */
    private JMenuItem menuItemModificarProducto;
    /**
     * Opción de menú para abrir la vista de búsqueda de productos. (El nombre 'Actualizar' puede ser confuso).
     */
    private JMenuItem menuItemActualizarProducto;
    /**
     * Opción de menú para abrir la vista de creación de productos.
     */
    private JMenuItem menuItemCargarProducto;
    /**
     * El panel de escritorio personalizado que contiene las ventanas internas.
     */
    private MiJDesktopPane desktop;
    /**
     * Opción de menú para abrir la vista de creación de un nuevo carrito.
     */
    private JMenuItem menuItemAñadirCarrito;
    /**
     * Opción de menú para abrir la vista que lista todos los carritos (admin).
     */
    private JMenuItem menuItemListarCarritos;
    /**
     * Opción de menú para abrir la vista de eliminación de carritos.
     */
    private JMenuItem menuItemEliminarCarrito;
    /**
     * Opción de menú para abrir la vista de modificación de carritos.
     */
    private JMenuItem menuItemModificarCarrito;
    /**
     * Opción de menú para que un usuario liste sus propios carritos.
     */
    private JMenuItem menuItemListarCarritoMis;
    /**
     * Opción de menú para abrir la vista de creación de usuarios (admin).
     */
    private JMenuItem menuItemCrearUsuario;
    /**
     * Opción de menú para abrir la vista de eliminación de usuarios (admin).
     */
    private JMenuItem menuItemEliminarUsuario;
    /**
     * Opción de menú para abrir la vista que lista todos los usuarios (admin).
     */
    private JMenuItem menuItemListarUsuarios;
    /**
     * Opción de menú para abrir la vista de modificación de usuarios (admin).
     */
    private JMenuItem menuItemModificarUsuario;
    /**
     * Opción de menú para que un usuario modifique sus propios datos.
     */
    private JMenuItem menuItemModificarMisUsuario;
    /**
     * Opción de menú para cerrar la sesión actual y volver a la ventana de login.
     */
    private JMenuItem menuItemCerrarSesión;
    /**
     * Opción de menú para cambiar el idioma a español.
     */
    private JMenuItem menuItemEspañol;
    /**
     * Opción de menú para cambiar el idioma a inglés.
     */
    private JMenuItem menuItemIngles;
    /**
     * Opción de menú para cambiar el idioma a noruego.
     */
    private JMenuItem menuItemNoruego;
    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

    /**
     * Constructor de la vista PrincipalView.
     * <p>
     * Inicializa el JFrame, instancia todos los componentes de Swing (menús, ítems, etc.),
     * ensambla la barra de menú y la asigna a la ventana. Establece el {@link MiJDesktopPane}
     * como el panel de contenido y configura las propiedades de la ventana.
     *
     * @param mensajeInternacionalizacionHandler El manejador de internacionalización para obtener los textos.
     */
    public PrincipalView(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;

        desktop = new MiJDesktopPane(mensajeInternacionalizacionHandler);
        menubar = new JMenuBar();
        menuCarrito = new JMenu();
        menuProducto = new JMenu();
        menuUsuario = new JMenu();
        menuAdministrador = new JMenu();
        menuIdiomas = new JMenu();
        URL urlUsuario = getClass().getResource("/user.png");
        menuUsuario.setIcon(new ImageIcon(urlUsuario));
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
        menuUsuario.add(menuItemModificarMisUsuario);
        menuUsuario.add(menuItemCerrarSesión);

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

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, los textos de los menús y de todos los ítems de menú
     * utilizando el {@code MensajeInternacionalizacionHandler}. También invoca la actualización
     * del texto en el panel de escritorio.
     */
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

        menuItemModificarMisUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usuario.modificarMis"));
        menuItemCerrarSesión.setText(mensajeInternacionalizacionHandler.get("menu.salir.cerrar"));

        menuIdiomas.setText(mensajeInternacionalizacionHandler.get("menu.idiomas"));
        menuItemEspañol.setText(mensajeInternacionalizacionHandler.get("menu.idioma.es"));
        menuItemIngles.setText(mensajeInternacionalizacionHandler.get("menu.idioma.en"));
        menuItemNoruego.setText(mensajeInternacionalizacionHandler.get("menu.idioma.nw"));
    }

    /**
     * Obtiene el panel de escritorio.
     * @return El componente JDesktopPane.
     */
    public JDesktopPane getDesktop() {
        return desktop;
    }

    /**
     * Establece el panel de escritorio.
     * @param desktop El nuevo MiJDesktopPane.
     */
    public void setDesktop(MiJDesktopPane desktop) {
        this.desktop = desktop;
    }

    /**
     * Obtiene el ítem de menú para modificar los datos del propio usuario.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemModificarMisUsuario() {
        return menuItemModificarMisUsuario;
    }

    /**
     * Establece el ítem de menú para modificar los datos del propio usuario.
     * @param menuItemModificarMisUsuario El nuevo JMenuItem.
     */
    public void setMenuItemModificarMisUsuario(JMenuItem menuItemModificarMisUsuario) {
        this.menuItemModificarMisUsuario = menuItemModificarMisUsuario;
    }

    /**
     * Obtiene el menú de idiomas.
     * @return El JMenu de idiomas.
     */
    public JMenu getMenuIdiomas() {
        return menuIdiomas;
    }

    /**
     * Establece el menú de idiomas.
     * @param menuIdiomas El nuevo JMenu de idiomas.
     */
    public void setMenuIdiomas(JMenu menuIdiomas) {
        this.menuIdiomas = menuIdiomas;
    }

    /**
     * Obtiene el ítem de menú para listar los carritos del propio usuario.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemListarCarritoMis() {
        return menuItemListarCarritoMis;
    }

    /**
     * Establece el ítem de menú para listar los carritos del propio usuario.
     * @param menuItemListarCarritoMis El nuevo JMenuItem.
     */
    public void setMenuItemListarCarritoMis(JMenuItem menuItemListarCarritoMis) {
        this.menuItemListarCarritoMis = menuItemListarCarritoMis;
    }

    /**
     * Obtiene el ítem de menú para el idioma español.
     * @return El JMenuItem para español.
     */
    public JMenuItem getMenuItemEspañol() {
        return menuItemEspañol;
    }

    /**
     * Establece el ítem de menú para el idioma español.
     * @param menuItemEspañol El nuevo JMenuItem.
     */
    public void setMenuItemEspañol(JMenuItem menuItemEspañol) {
        this.menuItemEspañol = menuItemEspañol;
    }

    /**
     * Obtiene el ítem de menú para el idioma inglés.
     * @return El JMenuItem para inglés.
     */
    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    /**
     * Establece el ítem de menú para el idioma inglés.
     * @param menuItemIngles El nuevo JMenuItem.
     */
    public void setMenuItemIngles(JMenuItem menuItemIngles) {
        this.menuItemIngles = menuItemIngles;
    }

    /**
     * Obtiene el ítem de menú para el idioma noruego.
     * @return El JMenuItem para noruego.
     */
    public JMenuItem getMenuItemNoruego() {
        return menuItemNoruego;
    }

    /**
     * Establece el ítem de menú para el idioma noruego.
     * @param menuItemNoruego El nuevo JMenuItem.
     */
    public void setMenuItemNoruego(JMenuItem menuItemNoruego) {
        this.menuItemNoruego = menuItemNoruego;
    }

    /**
     * Obtiene el ítem de menú para añadir un carrito.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemAñadirCarrito() {
        return menuItemAñadirCarrito;
    }

    /**
     * Establece el ítem de menú para añadir un carrito.
     * @param menuItemAñadirCarrito El nuevo JMenuItem.
     */
    public void setMenuItemAñadirCarrito(JMenuItem menuItemAñadirCarrito) {
        this.menuItemAñadirCarrito = menuItemAñadirCarrito;
    }

    /**
     * Obtiene el ítem de menú para eliminar un producto.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    /**
     * Establece el ítem de menú para eliminar un producto.
     * @param menuItemEliminarProducto El nuevo JMenuItem.
     */
    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) {
        this.menuItemEliminarProducto = menuItemEliminarProducto;
    }

    /**
     * Obtiene el ítem de menú para modificar un producto.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemModificarProducto() {
        return menuItemModificarProducto;
    }

    /**
     * Establece el ítem de menú para modificar un producto.
     * @param menuItemModificarProducto El nuevo JMenuItem.
     */
    public void setMenuItemModificarProducto(JMenuItem menuItemModificarProducto) {
        this.menuItemModificarProducto = menuItemModificarProducto;
    }

    /**
     * Obtiene el ítem de menú para buscar un producto.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    /**
     * Establece el ítem de menú para buscar un producto.
     * @param menuItemActualizarProducto El nuevo JMenuItem.
     */
    public void setMenuItemActualizarProducto(JMenuItem menuItemActualizarProducto) {
        this.menuItemActualizarProducto = menuItemActualizarProducto;
    }

    /**
     * Obtiene el ítem de menú para cargar/crear un producto.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemCargarProducto() {
        return menuItemCargarProducto;
    }

    /**
     * Establece el ítem de menú para cargar/crear un producto.
     * @param menuItemCargarProducto El nuevo JMenuItem.
     */
    public void setMenuItemCargarProducto(JMenuItem menuItemCargarProducto) {
        this.menuItemCargarProducto = menuItemCargarProducto;
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje informativo.
     * @param mensaje El texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Obtiene el ítem de menú para cerrar sesión.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemCerrarSesión() {
        return menuItemCerrarSesión;
    }

    /**
     * Establece el ítem de menú para cerrar sesión.
     * @param menuItemCerrarSesión El nuevo JMenuItem.
     */
    public void setMenuItemCerrarSesión(JMenuItem menuItemCerrarSesión) {
        this.menuItemCerrarSesión = menuItemCerrarSesión;
    }

    /**
     * Obtiene el ítem de menú para listar todos los carritos.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemListarCarritos() {
        return menuItemListarCarritos;
    }

    /**
     * Establece el ítem de menú para listar todos los carritos.
     * @param menuItemListarCarritos El nuevo JMenuItem.
     */
    public void setMenuItemListarCarritos(JMenuItem menuItemListarCarritos) {
        this.menuItemListarCarritos = menuItemListarCarritos;
    }

    /**
     * Obtiene el menú del carrito.
     * @return El JMenu del carrito.
     */
    public JMenu getMenuCarrito() {
        return menuCarrito;
    }

    /**
     * Establece el menú del carrito.
     * @param menuCarrito El nuevo JMenu del carrito.
     */
    public void setMenuCarrito(JMenu menuCarrito) {
        this.menuCarrito = menuCarrito;
    }

    /**
     * Obtiene el menú de producto.
     * @return El JMenu de producto.
     */
    public JMenu getMenuProducto() {
        return menuProducto;
    }

    /**
     * Establece el menú de producto.
     * @param menuProducto El nuevo JMenu de producto.
     */
    public void setMenuProducto(JMenu menuProducto) {
        this.menuProducto = menuProducto;
    }

    /**
     * Obtiene el menú de usuario.
     * @return El JMenu de usuario.
     */
    public JMenu getMenuUsuario() {
        return menuUsuario;
    }

    /**
     * Establece el menú de usuario.
     * @param menuUsuario El nuevo JMenu de usuario.
     */
    public void setMenuUsuario(JMenu menuUsuario) {
        this.menuUsuario = menuUsuario;
    }

    /**
     * Obtiene la barra de menú.
     * @return La JMenuBar.
     */
    public JMenuBar getMenubar() {
        return menubar;
    }

    /**
     * Establece la barra de menú.
     * @param menubar La nueva JMenuBar.
     */
    public void setMenubar(JMenuBar menubar) {
        this.menubar = menubar;
    }

    /**
     * Obtiene el menú de administrador.
     * @return El JMenu de administrador.
     */
    public JMenu getMenuAdministrador() {
        return menuAdministrador;
    }

    /**
     * Establece el menú de administrador.
     * @param menuAdministrador El nuevo JMenu de administrador.
     */
    public void setMenuAdministrador(JMenu menuAdministrador) {
        this.menuAdministrador = menuAdministrador;
    }

    /**
     * Obtiene el ítem de menú para eliminar un carrito.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemEliminarCarrito() {
        return menuItemEliminarCarrito;
    }

    /**
     * Establece el ítem de menú para eliminar un carrito.
     * @param menuItemEliminarCarrito El nuevo JMenuItem.
     */
    public void setMenuItemEliminarCarrito(JMenuItem menuItemEliminarCarrito) {
        this.menuItemEliminarCarrito = menuItemEliminarCarrito;
    }

    /**
     * Obtiene el ítem de menú para modificar un carrito.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemModificarCarrito() {
        return menuItemModificarCarrito;
    }

    /**
     * Establece el ítem de menú para modificar un carrito.
     * @param menuItemModificarCarrito El nuevo JMenuItem.
     */
    public void setMenuItemModificarCarrito(JMenuItem menuItemModificarCarrito) {
        this.menuItemModificarCarrito = menuItemModificarCarrito;
    }

    /**
     * Obtiene el ítem de menú para crear un usuario.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemCrearUsuario() {
        return menuItemCrearUsuario;
    }

    /**
     * Establece el ítem de menú para crear un usuario.
     * @param menuItemCrearUsuario El nuevo JMenuItem.
     */
    public void setMenuItemCrearUsuario(JMenuItem menuItemCrearUsuario) {
        this.menuItemCrearUsuario = menuItemCrearUsuario;
    }

    /**
     * Obtiene el ítem de menú para eliminar un usuario.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemEliminarUsuario() {
        return menuItemEliminarUsuario;
    }

    /**
     * Establece el ítem de menú para eliminar un usuario.
     * @param menuItemEliminarUsuario El nuevo JMenuItem.
     */
    public void setMenuItemEliminarUsuario(JMenuItem menuItemEliminarUsuario) {
        this.menuItemEliminarUsuario = menuItemEliminarUsuario;
    }

    /**
     * Obtiene el ítem de menú para listar usuarios.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemListarUsuarios() {
        return menuItemListarUsuarios;
    }

    /**
     * Establece el ítem de menú para listar usuarios.
     * @param menuItemListarUsuarios El nuevo JMenuItem.
     */
    public void setMenuItemListarUsuarios(JMenuItem menuItemListarUsuarios) {
        this.menuItemListarUsuarios = menuItemListarUsuarios;
    }

    /**
     * Obtiene el ítem de menú para modificar un usuario.
     * @return El JMenuItem correspondiente.
     */
    public JMenuItem getMenuItemModificarUsuario() {
        return menuItemModificarUsuario;
    }

    /**
     * Establece el ítem de menú para modificar un usuario.
     * @param menuItemModificarUsuario El nuevo JMenuItem.
     */
    public void setMenuItemModificarUsuario(JMenuItem menuItemModificarUsuario) {
        this.menuItemModificarUsuario = menuItemModificarUsuario;
    }

    /**
     * Configura la visibilidad de los menús y sus ítems para un usuario con rol estándar (no administrador).
     * Oculta las opciones de menú que son exclusivas para administradores.
     */
    public void desactivar() {
        getMenuAdministrador().setVisible(false);
        getMenuItemModificarCarrito().setVisible(true);
        getMenuItemListarCarritos().setVisible(false);
        getMenuItemEliminarProducto().setVisible(false);
        getMenuItemModificarProducto().setVisible(false);
        getMenuItemCargarProducto().setVisible(false);
    }
}