package ec.edu.ups.vista;

import ec.edu.ups.modelo.Genero;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

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
    private JTextField txtNombreCom;
    private JSpinner spnEdad;
    private JComboBox cbxGenero;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JLabel lblNombre;
    private JLabel lblEdad;
    private JLabel lblGenero;
    private JLabel lblTelefono;
    private JLabel lblEmail;
    private JButton btnAtras;
    private JMenuBar menubar;
    private JMenu menuIdiomas;
    private JMenuItem menuItemEspañol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemNoruego;

    private MensajeInternacionalizacionHandler mensajes;

    public RegistrarseView(MensajeInternacionalizacionHandler mensajes) {
        setContentPane(panelPrincipal);
        this.mensajes=mensajes;
        menubar = new JMenuBar();
        menuIdiomas = new JMenu();
        menuItemEspañol = new JMenuItem();
        menuItemIngles = new JMenuItem();
        menuItemNoruego = new JMenuItem();
        URL urlReg = getClass().getResource("/add.png");
        URL urlAtras = getClass().getResource("/back.png");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        btnRegistrarse.setIcon(new ImageIcon(urlReg));
        btnAtras.setIcon(new ImageIcon(urlAtras));
        setLocationRelativeTo(null);
        setResizable(false);
        spnEdad.setModel(new SpinnerNumberModel(18, 18, 120, 1));
        menubar.add(menuIdiomas);
        menuIdiomas.add(menuItemIngles);
        menuIdiomas.add(menuItemEspañol);
        menuIdiomas.add(menuItemNoruego);
        setJMenuBar(menubar);
        actualizarTextos();
        actualizarComboBox();
    }

    void actualizarTextos() {
        menuIdiomas.setText(mensajes.get("menu.idiomas"));
        menuItemEspañol.setText(mensajes.get("menu.idioma.es"));
        menuItemIngles.setText(mensajes.get("menu.idioma.en"));
        menuItemNoruego.setText(mensajes.get("menu.idioma.nw"));
        setTitle(mensajes.get("login.boton.reg"));
        btnAtras.setText(mensajes.get("global.atras"));
        lblTitulo.setText(mensajes.get("login.boton.reg"));
        lblUsername.setText(mensajes.get("global.usuario"));
        lblPassword.setText(mensajes.get("global.contraseña"));
        lblRepContra.setText(mensajes.get("register.app.rep"));
        btnRegistrarse.setText(mensajes.get("login.boton.reg"));
        lblEdad.setText(mensajes.get("global.edad"));
        lblGenero.setText(mensajes.get("global.genero"));
        lblTelefono.setText(mensajes.get("global.telefono"));
        lblEmail.setText(mensajes.get("global.email"));
        lblNombre.setText(mensajes.get("global.nombreCompleto"));
        cbxGenero.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Genero) {
                    Genero rol = (Genero) value;
                    if (rol == Genero.MASCULINO) {
                        setText(mensajes.get("global.genero.masculino"));
                    } else if (rol == Genero.FEMENINO) {
                        setText(mensajes.get("global.genero.femenino"));
                    } else if(rol == Genero.OTRO){
                        setText(mensajes.get("global.genero.otro"));
                    }
                }
                return this;
            }
        });
    }

    void actualizarComboBox() {
        cbxGenero.removeAllItems();
        for (Genero g : Genero.values()) {
            cbxGenero.addItem(g);
        }
        cbxGenero.setSelectedIndex(-1);
    }

    public JButton getBtnAtras() {
        return btnAtras;
    }

    public JTextField getTxtNombreCom() {
        return txtNombreCom;
    }

    public void setTxtNombreCom(JTextField txtNombreCom) {
        this.txtNombreCom = txtNombreCom;
    }

    public JSpinner getSpnEdad() {
        return spnEdad;
    }

    public void setSpnEdad(JSpinner spnEdad) {
        this.spnEdad = spnEdad;
    }

    public JComboBox getCbxGenero() {
        return cbxGenero;
    }

    public void setCbxGenero(JComboBox cbxGenero) {
        this.cbxGenero = cbxGenero;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public void setTxtTelefono(JTextField txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
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

    public JMenuItem getMenuItemEspañol() {
        return menuItemEspañol;
    }

    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    public JMenuItem getMenuItemNoruego() {
        return menuItemNoruego;
    }

    public void limpiarCampos() {
        txtUsername.setText("");
        txtContraseña.setText("");
        txtRepContra.setText("");
    }
}
