package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class RegistrarseView extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelSecundario;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtContraseña;
    private JButton btnRegistrarse;
    private JLabel lblTitulo;
    private JPasswordField txtRepContra;
    private JLabel lblRepContra;

    private MensajeInternacionalizacionHandler mensajes;

    public RegistrarseView(MensajeInternacionalizacionHandler mensajes) {
        this.mensajes=mensajes;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        actualizarTextos();
    }

    void actualizarTextos() {
        setTitle(mensajes.get("login.boton.reg"));
        lblTitulo.setText(mensajes.get("login.boton.reg"));
        lblUsername.setText(mensajes.get("global.usuario"));
        lblPassword.setText(mensajes.get("global.contraseña"));
        lblRepContra.setText(mensajes.get("register.app.rep"));
        btnRegistrarse.setText(mensajes.get("login.boton.reg"));
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JPasswordField getTxtContraseña() {
        return txtContraseña;
    }

    public void setTxtContraseña(JPasswordField txtContraseña) {
        this.txtContraseña = txtContraseña;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public void setBtnRegistrarse(JButton btnRegistrarse) {
        this.btnRegistrarse = btnRegistrarse;
    }

    public JPasswordField getTxtRepContra() {
        return txtRepContra;
    }

    public void setTxtRepContra(JPasswordField txtRepContra) {
        this.txtRepContra = txtRepContra;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarCampos() {
        txtUsername.setText("");
        txtContraseña.setText("");
        txtRepContra.setText("");
    }
}
