package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;

public class ProductoModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JButton btnModificar;
    private JTextField txtNombre;
    private JTextField txtPrecio;

    private MensajeInternacionalizacionHandler mensajes;

    public ProductoModificarView(MensajeInternacionalizacionHandler mensajes) {
        super("",true,true,false,true);
        this.mensajes = mensajes;

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);

        actualizarTextos();
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("producto.modificar.titulo.app"));

        lblCodigo.setText(mensajes.get("global.codigo"));
        lblNombre.setText(mensajes.get("global.nombre"));
        lblPrecio.setText(mensajes.get("global.precio"));

        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnModificar.setText(mensajes.get("global.boton.modificar"));
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}