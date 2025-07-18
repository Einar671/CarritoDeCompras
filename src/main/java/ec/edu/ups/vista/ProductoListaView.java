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

/**
 * Vista (JInternalFrame) para listar y buscar productos.
 * <p>
 * Esta clase proporciona una interfaz gráfica que permite a los usuarios
 * ver una lista de todos los productos disponibles y realizar búsquedas
 * por nombre de producto para filtrar la lista.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class ProductoListaView extends JInternalFrame {
    /**
     * Campo de texto para que el usuario ingrese el término de búsqueda (nombre del producto).
     */
    private JTextField txtBuscar;
    /**
     * Botón para iniciar la búsqueda de productos según el término ingresado.
     */
    private JButton btnBuscar;
    /**
     * Etiqueta para el campo de búsqueda.
     */
    private JLabel lblBuscar;
    /**
     * Tabla que muestra la lista de productos.
     */
    private JTable tblProductos;
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Botón para mostrar (o volver a cargar) la lista completa de todos los productos.
     */
    private JButton btnListar;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;
    /**
     * Modelo de datos para la tabla {@code tblProductos}.
     */
    private DefaultTableModel modelo;
    /**
     * El locale actual, utilizado para formatear números y monedas.
     */
    private Locale locale;
    /**
     * La lista de productos que se muestra actualmente en la tabla.
     */
    private List<Producto> productosActuales;

    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor de la vista ProductoListaView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga los iconos
     * para los botones, inicializa el modelo de la tabla y llama al método
     * para actualizar los textos según el idioma.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public ProductoListaView(MensajeInternacionalizacionHandler mensajes) {

        super("",true,true,false,true);
        setContentPane(panelPrincipal);
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));
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

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, los textos de los botones,
     * el tooltip del campo de búsqueda y los encabezados de la tabla según el
     * {@code Locale} configurado en el manejador de mensajes.
     * Al final, vuelve a mostrar los productos para que los formatos se actualicen.
     */
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

    /**
     * Obtiene la etiqueta del título principal.
     * @return El componente JLabel para el título.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título principal.
     * @param lblTitulo El nuevo componente JLabel para el título.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene el campo de texto para la búsqueda.
     * @return El componente JTextField para la búsqueda.
     */
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    /**
     * Establece el campo de texto para la búsqueda.
     * @param txtBuscar El nuevo componente JTextField para la búsqueda.
     */
    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    /**
     * Obtiene el botón de buscar.
     * @return El componente JButton para buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón de buscar.
     * @param btnBuscar El nuevo componente JButton para buscar.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene la etiqueta del campo de búsqueda.
     * @return El componente JLabel para la búsqueda.
     */
    public JLabel getLblBuscar() {
        return lblBuscar;
    }

    /**
     * Establece la etiqueta del campo de búsqueda.
     * @param lblBuscar El nuevo componente JLabel para la búsqueda.
     */
    public void setLblBuscar(JLabel lblBuscar) {
        this.lblBuscar = lblBuscar;
    }

    /**
     * Obtiene la tabla de productos.
     * @return El componente JTable que muestra los productos.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Establece la tabla de productos.
     * @param tblProductos El nuevo componente JTable para los productos.
     */
    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    /**
     * Obtiene el panel principal de la vista.
     * @return El componente JPanel principal.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    /**
     * Establece el panel principal de la vista.
     * @param panelPrincipal El nuevo componente JPanel principal.
     */
    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    /**
     * Obtiene el botón de listar todos los productos.
     * @return El componente JButton para listar.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Establece el botón de listar todos los productos.
     * @param btnListar El nuevo componente JButton para listar.
     */
    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    /**
     * Muestra una lista de objetos {@link Producto} en la tabla de la interfaz.
     * <p>
     * Limpia la tabla y la vuelve a poblar con la lista de productos proporcionada.
     * Los valores de precio se formatean como moneda según el {@code Locale} actual.
     *
     * @param productos La lista de productos a mostrar. Si es nula, la tabla se limpia.
     */
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