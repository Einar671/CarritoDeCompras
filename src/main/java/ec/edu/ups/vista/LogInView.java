package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URI;
import java.net.URL;

public class LogInView extends JFrame {
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
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("login.app.titulo"));
        lblUsername.setText(mensajes.get("global.usuario"));

        lblPassword.setText(mensajes.get("global.contraseña"));

        btnIniciarSesion.setText(mensajes.get("login.app.titulo"));
        btnRegistrarse.setText(mensajes.get("login.boton.reg"));
        lblTitulo.setText(mensajes.get("login.app.titulo"));
        btnOlvidoContraseña.setText(mensajes.get("login.app.olvido"));
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
}