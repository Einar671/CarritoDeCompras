package ec.edu.ups.vista;

// 1. Importar el manejador de internacionalización
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

public class ProductoEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtPrecio;
    private JButton btnBuscar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblPrecio;
    private JButton btnEliminar;
    private JLabel lblTitulo;

    private MensajeInternacionalizacionHandler mensajes;

    public ProductoEliminarView(MensajeInternacionalizacionHandler mensajes){

        super(mensajes.get("producto.eliminar.titulo.app"),true,true,false,true);
        setContentPane(panelPrincipal);
        this.mensajes = mensajes;

        URL urlBuscar=getClass().getResource("/search.png");
        URL urlEliminar=getClass().getResource("/trash.png");

        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        btnEliminar.setIcon(new ImageIcon(urlEliminar));

        actualizarTextos();

        setSize(600,400);
    }


    public void actualizarTextos() {
        setTitle(mensajes.get("producto.eliminar.titulo.app"));

        lblTitulo.setText(mensajes.get("producto.eliminar.titulo"));
        lblCodigo.setText(mensajes.get("global.codigo"));
        lblNombre.setText(mensajes.get("global.nombre"));
        lblPrecio.setText(mensajes.get("global.precio"));
        txtCodigo.setToolTipText(mensajes.get("producto.top.codigo"));
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnEliminar.setText(mensajes.get("global.boton.eliminar"));
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public void limpiarCampos(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCodigo.setEnabled(true);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}