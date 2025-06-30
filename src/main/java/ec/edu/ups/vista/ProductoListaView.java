package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
// 1. Importar el manejador de internacionalización
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class ProductoListaView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JLabel lblBuscar;
    private JTable tblProductos;
    private JPanel panelPrincipal;
    private JButton btnListar;
    private JLabel lblTitulo;
    private DefaultTableModel modelo;
    private Locale locale;
    private List<Producto> productosActuales;

    private MensajeInternacionalizacionHandler mensajes;

    public ProductoListaView(MensajeInternacionalizacionHandler mensajes) {

        super("",true,true,false,true);
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));
        setContentPane(panelPrincipal);
        this.mensajes = mensajes;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        URL urlBuscar = getClass().getResource("/search.png");
        URL urlListar= getClass().getResource("/list.png");
        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        btnListar.setIcon(new ImageIcon(urlListar));

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        actualizarTextos();
    }

    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));
        setTitle(mensajes.get("producto.listar.titulo.app"));

        lblTitulo.setText(mensajes.get("producto.listar.titulo"));
        lblBuscar.setText(mensajes.get("global.boton.buscar") + ":");
        txtBuscar.setToolTipText(mensajes.get("producto.top.nombre"));
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnListar.setText(mensajes.get("producto.listar.titulo.app"));

        Object[] columnas = {
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio")
        };
        modelo.setColumnIdentifiers(columnas);
        mostrarProductos(productosActuales);
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JLabel getLblBuscar() {
        return lblBuscar;
    }

    public void setLblBuscar(JLabel lblBuscar) {
        this.lblBuscar = lblBuscar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public void mostrarProductos(List<Producto> productos) {
        productosActuales = productos;
        if(productos==null) return;
        modelo.setRowCount(0);
        for(Producto producto: productos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    FormateadorUtils.formatearMoneda(producto.getPrecio(), locale) };
            modelo.addRow(fila);
        }
    }
}