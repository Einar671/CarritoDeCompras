package ec.edu.ups.vista;

import ec.edu.ups.modelo.Genero;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class UsuarioModificarMisView extends JInternalFrame {
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JTextField txtContraseña;
    private JButton btnModificar;
    private JTextField txtUsuario;
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JTextField txtNuevoUser;
    private JLabel lblNuevoUser;
    private JSpinner sprEdad;
    private JComboBox cbxGenero;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtNombreCom;
    private JPanel lblNombre;
    private JLabel lblEdad;
    private JLabel lblGenero;
    private JLabel lblTelefono;
    private JLabel lblEmail;
    private JLabel lblNombreCom;

    private MensajeInternacionalizacionHandler mensajes;

    public UsuarioModificarMisView(MensajeInternacionalizacionHandler mensajes) {

        super("", true, true, false, true);
        this.mensajes = mensajes;

        URL urlModificar = getClass().getResource("/edit.png");
        btnModificar.setIcon(new ImageIcon(urlModificar));

        setContentPane(panelPrincipal);
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        sprEdad.setModel(new SpinnerNumberModel(18, 18, 120, 1));

        txtUsuario.setEditable(false);
        actualizarComboBox();

        actualizarTextos();
    }

    private void actualizarComboBox() {
        cbxGenero.removeAllItems();
        for (Genero g : Genero.values()) {
            cbxGenero.addItem(g);
        }
        cbxGenero.setSelectedIndex(-1);
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("menu.usuario.modificarMis"));
        lblTitulo.setText(mensajes.get("menu.usuario.modificarMis"));
        lblNuevoUser.setText(mensajes.get("global.nuevoUsuario"));
        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");
        btnModificar.setText(mensajes.get("global.boton.modificar"));
        lblEmail.setText(mensajes.get("global.email"));
        lblTelefono.setText(mensajes.get("global.telefono"));
        lblEdad.setText(mensajes.get("global.edad"));
        lblGenero.setText(mensajes.get("global.genero"));
        lblNombreCom.setText(mensajes.get("global.nombreCompleto"));
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

    public JSpinner getSprEdad() {
        return sprEdad;
    }

    public JComboBox getCbxGenero() {
        return cbxGenero;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTextField getTxtNombreCom() {
        return txtNombreCom;
    }

    public JTextField getTxtNuevoUser() {
        return txtNuevoUser;
    }

    public void setTxtNuevoUser(JTextField txtNuevoUser) {
        this.txtNuevoUser = txtNuevoUser;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }


    public void limpiarCampos() {
        txtContraseña.setText("");
    }
}