package ec.edu.ups.vista;

import javax.swing.*;

public class CarritoAñadirView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JPanel txt;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JTable table1;
    private JButton btnGuardar;
    private JButton btnBuscar;
    private JButton btnAñadir;
    private JButton btnLimpiar;
    private JComboBox cbxCantidad;

    public CarritoAñadirView() {
        super("Carrito de Compras", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        cargarDatos();
    }


    private void cargarDatos(){
        cbxCantidad.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(String.valueOf(i));
        }
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    public JTextField getTxtIVA() {
        return txtIVA;
    }

    public void setTxtIVA(JTextField txtIVA) {
        this.txtIVA = txtIVA;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JButton getBtnAñadir() {
        return btnAñadir;
    }

    public void setBtnAñadir(JButton btnAñadir) {
        this.btnAñadir = btnAñadir;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }

    public void setCbxCantidad(JComboBox cbxCantidad) {
        this.cbxCantidad = cbxCantidad;
    }

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
