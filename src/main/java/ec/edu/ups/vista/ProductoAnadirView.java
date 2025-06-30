package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
// 1. Importar el manejador de internacionalización
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

public class ProductoAnadirView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtPrecio;
    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblTitulo;

    private MensajeInternacionalizacionHandler mensajes;

    public ProductoAnadirView(MensajeInternacionalizacionHandler mensajes) {
        super(mensajes.get("producto.crear.titulo.app"), true, true, true, true);
        this.mensajes = mensajes;
        URL urlAñadir = getClass().getResource("/check.png");
        URL urlLimpiar = getClass().getResource("/clean.png");

        btnAceptar.setIcon(new ImageIcon(urlAñadir));
        btnLimpiar.setIcon(new ImageIcon(urlLimpiar));
        setContentPane(panelPrincipal);

        actualizarTextos();

        setSize(500, 500);
        setClosable(true);
        setIconifiable(Boolean.TRUE);
        setResizable(Boolean.TRUE);

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

    }


    public void actualizarTextos() {
        setTitle(mensajes.get("producto.crear.titulo.app"));
        lblTitulo.setText(mensajes.get("producto.crear.titulo"));
        btnAceptar.setText(mensajes.get("global.crear"));
        btnLimpiar.setText(mensajes.get("global.boton.limpiar"));
        txtCodigo.setToolTipText(mensajes.get("producto.top.codigo"));
        txtNombre.setToolTipText(mensajes.get("producto.top.nombre"));
        txtPrecio.setToolTipText(mensajes.get("producto.top.precio"));
        lblCodigo.setText(mensajes.get("global.codigo"));
        lblNombre.setText(mensajes.get("global.nombre"));
        lblPrecio.setText(mensajes.get("global.precio"));
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }
}