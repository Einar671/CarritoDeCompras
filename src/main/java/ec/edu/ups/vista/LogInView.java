package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URI;
import java.net.URL;

/**
 * Vista (JFrame) para el inicio de sesión de usuarios.
 * <p>
 * Esta clase representa la ventana principal de login donde los usuarios pueden
 * ingresar sus credenciales (nombre de usuario y contraseña) para acceder al sistema.
 * También proporciona opciones para registrarse como un nuevo usuario o iniciar el proceso
 * de recuperación de contraseña.
 * <p>
 * Incluye un menú para cambiar el idioma de la interfaz gráfica en tiempo real.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class LogInView extends JFrame {
    /**
     * La barra de menú principal de la ventana.
     */
    private JMenuBar menubar;
    /**
     * El menú desplegable para seleccionar el idioma de la aplicación.
     */
    private JMenu menuIdiomas;
    /**
     * La opción de menú para cambiar el idioma a español.
     */
    private JMenuItem menuItemEspañol;
    /**
     * La opción de menú para cambiar el idioma a inglés.
     */
    private JMenuItem menuItemIngles;
    /**
     * La opción de menú para cambiar el idioma a noruego.
     */
    private JMenuItem menuItemNoruego;
    /**
     * El panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Un panel secundario, posiblemente para agrupar componentes.
     */
    private JPanel panelSecundario;
    /**
     * Campo de texto para que el usuario ingrese su nombre de usuario (cédula).
     */
    private JTextField txtUsername;
    /**
     * Campo de contraseña para que el usuario ingrese su clave.
     */
    private JPasswordField psfContraseña;
    /**
     * Botón para intentar iniciar sesión con las credenciales ingresadas.
     */
    private JButton btnIniciarSesion;
    /**
     * Botón para abrir la ventana de registro de nuevos usuarios.
     */
    private JButton btnRegistrarse;
    /**
     * Etiqueta para el campo de nombre de usuario.
     */
    private JLabel lblUsername;
    /**
     * Etiqueta para el campo de contraseña.
     */
    private JLabel lblPassword;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;
    /**
     * Botón para iniciar el proceso de recuperación de contraseña.
     */
    private JButton btnOlvidoContraseña;


    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor de la vista LogInView.
     * <p>
     * Inicializa el JFrame, configura los componentes, carga los iconos
     * para los botones, establece el menú de idiomas y llama al método
     * para actualizar los textos según el idioma por defecto.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public LogInView(MensajeInternacionalizacionHandler mensajes) {
        this.mensajes = mensajes;
        setContentPane(panelPrincipal);
        menubar = new JMenuBar();
        menuIdiomas = new JMenu();
        menuItemEspañol = new JMenuItem();
        menuItemIngles = new JMenuItem();
        menuItemNoruego = new JMenuItem();
        URL urlLog = getClass().getResource("/log-in.png");
        URL urlReg = getClass().getResource("/add.png");
        URL urlOlvido = getClass().getResource("/question-sign.png");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        btnIniciarSesion.setIcon(new ImageIcon(urlLog));
        btnRegistrarse.setIcon(new ImageIcon(urlReg));
        btnOlvidoContraseña.setIcon(new ImageIcon(urlOlvido));
        actualizarTextos();
        menubar.add(menuIdiomas);
        menuIdiomas.add(menuItemIngles);
        menuIdiomas.add(menuItemEspañol);
        menuIdiomas.add(menuItemNoruego);
        setJMenuBar(menubar);
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, los textos de los botones y
     * los ítems del menú según el idioma configurado en el manejador de mensajes.
     */
    public void actualizarTextos() {
        setTitle(mensajes.get("login.app.titulo"));
        lblUsername.setText(mensajes.get("global.usuario"));

        lblPassword.setText(mensajes.get("global.contraseña"));

        btnIniciarSesion.setText(mensajes.get("login.app.titulo"));
        btnRegistrarse.setText(mensajes.get("login.boton.reg"));
        lblTitulo.setText(mensajes.get("login.app.titulo"));
        btnOlvidoContraseña.setText(mensajes.get("login.app.olvido"));

        menuIdiomas.setText(mensajes.get("menu.idiomas"));
        menuItemEspañol.setText(mensajes.get("menu.idioma.es"));
        menuItemIngles.setText(mensajes.get("menu.idioma.en"));
        menuItemNoruego.setText(mensajes.get("menu.idioma.nw"));
    }

    /**
     * Obtiene el campo de texto del nombre de usuario.
     * @return El componente JTextField para el username.
     */
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    /**
     * Establece el campo de texto del nombre de usuario.
     * @param txtUsername El nuevo componente JTextField para el username.
     */
    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    /**
     * Obtiene el campo de texto de la contraseña.
     * @return El componente JPasswordField para la contraseña.
     */
    public JPasswordField getPsfContraseña() {
        return psfContraseña;
    }

    /**
     * Establece el campo de texto de la contraseña.
     * @param psfContraseña El nuevo componente JPasswordField para la contraseña.
     */
    public void setPsfContraseña(JPasswordField psfContraseña) {
        this.psfContraseña = psfContraseña;
    }

    /**
     * Obtiene el botón de iniciar sesión.
     * @return El componente JButton para iniciar sesión.
     */
    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    /**
     * Establece el botón de iniciar sesión.
     * @param btnIniciarSesion El nuevo componente JButton para iniciar sesión.
     */
    public void setBtnIniciarSesion(JButton btnIniciarSesion) {
        this.btnIniciarSesion = btnIniciarSesion;
    }

    /**
     * Obtiene el botón de registrarse.
     * @return El componente JButton para registrarse.
     */
    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    /**
     * Establece el botón de registrarse.
     * @param btnRegistrarse El nuevo componente JButton para registrarse.
     */
    public void setBtnRegistrarse(JButton btnRegistrarse) {
        this.btnRegistrarse = btnRegistrarse;
    }

    /**
     * Obtiene el botón de olvido de contraseña.
     * @return El componente JButton para olvido de contraseña.
     */
    public JButton getBtnOlvidoContraseña() {
        return btnOlvidoContraseña;
    }

    /**
     * Establece el botón de olvido de contraseña.
     * @param btnOlvidoContraseña El nuevo componente JButton para olvido de contraseña.
     */
    public void setBtnOlvidoContraseña(JButton btnOlvidoContraseña) {
        this.btnOlvidoContraseña = btnOlvidoContraseña;
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje informativo.
     * @param mensaje El texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Limpia los campos de texto de usuario y contraseña.
     */
    public void limpiarCampos(){
        txtUsername.setText("");
        psfContraseña.setText("");
    }

    /**
     * Obtiene la opción de menú para el idioma español.
     * @return El componente JMenuItem para español.
     */
    public JMenuItem getMenuItemEspañol() {
        return menuItemEspañol;
    }

    /**
     * Obtiene la opción de menú para el idioma inglés.
     * @return El componente JMenuItem para inglés.
     */
    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    /**
     * Obtiene la opción de menú para el idioma noruego.
     * @return El componente JMenuItem para noruego.
     */
    public JMenuItem getMenuItemNoruego() {
        return menuItemNoruego;
    }
}