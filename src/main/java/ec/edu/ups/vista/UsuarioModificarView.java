package ec.edu.ups.vista;

import javax.swing.*;

public class UsuarioModificarView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JButton btnBuscar;
    private JTextField txtBuscar;
    private JTextField txtUsuario;
    private JTextField txtContraseña;
    private JButton btnModificar;
    private JLabel lblUsuario;
    private JLabel lblUsuarioA;
    private JLabel lblContraseña;
    private JLabel lblRol;
    private JComboBox cbxRoles;

    public UsuarioModificarView(){
        super("",true,true,false,true);
        setContentPane(panelPrincipal);
        setSize(600,400);

    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public void setTxtContraseña(JTextField txtContraseña) {
        this.txtContraseña = txtContraseña;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public void setBtnModificar(JButton btnModificar) {
        this.btnModificar = btnModificar;
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    public JLabel getLblUsuarioA() {
        return lblUsuarioA;
    }

    public void setLblUsuarioA(JLabel lblUsuarioA) {
        this.lblUsuarioA = lblUsuarioA;
    }

    public JLabel getLblContraseña() {
        return lblContraseña;
    }

    public void setLblContraseña(JLabel lblContraseña) {
        this.lblContraseña = lblContraseña;
    }

    public JLabel getLblRol() {
        return lblRol;
    }

    public void setLblRol(JLabel lblRol) {
        this.lblRol = lblRol;
    }

    public JComboBox getCbxRoles() {
        return cbxRoles;
    }

    public void setCbxRoles(JComboBox cbxRoles) {
        this.cbxRoles = cbxRoles;
    }
}
