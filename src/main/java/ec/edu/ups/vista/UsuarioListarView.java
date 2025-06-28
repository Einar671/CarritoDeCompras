package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UsuarioListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JButton btnListar;
    private JTable tblUsuarios;
    private JLabel lblListado;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private DefaultTableModel tableModel;

    private MensajeInternacionalizacionHandler mensajes;

    public UsuarioListarView(MensajeInternacionalizacionHandler mensajes) {

        super("", true, true, false, true);
        this.mensajes = mensajes;
        setContentPane(panelPrincipal);
        setSize(600, 400);

        configurarTabla();
        actualizarTextos();
    }

    private void configurarTabla() {
        tableModel = new DefaultTableModel();
        tblUsuarios.setModel(tableModel);
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("usuario.listar.titulo.app"));

        lblListado.setText(mensajes.get("usuario.listar.titulo.app"));
        btnListar.setText(mensajes.get("menu.usuario.listar"));
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        txtUsuario.setToolTipText(mensajes.get("mensaje.usuario.buscar.vacio"));

        tableModel.setColumnIdentifiers(new Object[]{
                mensajes.get("global.usuario"),
                mensajes.get("global.rol")
        });
    }

    public void mostrarUsuarios(List<Usuario> usuarios) {
        tableModel.setRowCount(0);

        for (Usuario usuario : usuarios) {
            String rolTraducido = "";
            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                rolTraducido = mensajes.get("global.rol.admin");
            } else if (usuario.getRol() == Rol.USUARIO) {
                rolTraducido = mensajes.get("global.rol.user");
            }

            tableModel.addRow(new Object[]{
                    usuario.getUsername(),
                    rolTraducido
            });
        }
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}