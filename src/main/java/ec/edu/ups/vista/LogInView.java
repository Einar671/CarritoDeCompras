package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URI;
import java.net.URL;

public class LogInView extends JFrame {
    private JMenuBar menubar;
    private JMenu menuIdiomas;
    private JMenuItem menuItemEspañol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemNoruego;
    private JPanel panelPrincipal;
    private JPanel panelSecundario;
    private JTextField txtUsername;
    private JPasswordField psfContraseña;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblTitulo;
    private JButton btnOlvidoContraseña;


    private MensajeInternacionalizacionHandler mensajes;

    public LogInView(MensajeInternacionalizacionHandler mensajes) {
        this.mensajes = mensajes;
        menubar = new JMenuBar();
        menuIdiomas = new JMenu();
        menuItemEspañol = new JMenuItem();
        menuItemIngles = new JMenuItem();
        menuItemNoruego = new JMenuItem();
        URL urlLog = getClass().getResource("/log-in.png");
        URL urlReg = getClass().getResource("/add.png");
        URL urlOlvido = getClass().getResource("/question-sign.png");
        setContentPane(panelPrincipal);
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

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JPasswordField getPsfContraseña() {
        return psfContraseña;
    }

    public void setPsfContraseña(JPasswordField psfContraseña) {
        this.psfContraseña = psfContraseña;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public void setBtnIniciarSesion(JButton btnIniciarSesion) {
        this.btnIniciarSesion = btnIniciarSesion;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public void setBtnRegistrarse(JButton btnRegistrarse) {
        this.btnRegistrarse = btnRegistrarse;
    }

    public JButton getBtnOlvidoContraseña() {
        return btnOlvidoContraseña;
    }

    public void setBtnOlvidoContraseña(JButton btnOlvidoContraseña) {
        this.btnOlvidoContraseña = btnOlvidoContraseña;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
    public void limpiarCampos(){
        txtUsername.setText("");
        psfContraseña.setText("");
    }

    public JMenuItem getMenuItemEspañol() {
        return menuItemEspañol;
    }

    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    public JMenuItem getMenuItemNoruego() {
        return menuItemNoruego;
    }
}