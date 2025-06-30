package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.net.URL;

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
    private JLabel lblTitulo;

    private MensajeInternacionalizacionHandler mensajes;

    public ProductoModificarView(MensajeInternacionalizacionHandler mensajes) {
        super("",true,true,false,true);
        this.mensajes = mensajes;

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        URL urlBuscar = getClass().getResource("/search.png");
        URL urlModificar = getClass().getResource("/edit.png");
        btnModificar.setIcon(new ImageIcon(urlModificar));
        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        setSize(500, 500);

        actualizarTextos();
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("producto.modificar.titulo.app"));
        lblTitulo.setText(mensajes.get("producto.modificar.titulo.app"));
        lblCodigo.setText(mensajes.get("global.codigo"));
        lblNombre.setText(mensajes.get("global.nombre"));
        lblPrecio.setText(mensajes.get("global.precio"));
        txtCodigo.setToolTipText(mensajes.get("producto.top.codigo"));
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