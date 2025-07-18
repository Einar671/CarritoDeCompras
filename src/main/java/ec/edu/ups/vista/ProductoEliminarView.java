package ec.edu.ups.vista;

// 1. Importar el manejador de internacionalización
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

/**
 * Vista (JInternalFrame) para eliminar un producto del sistema.
 * <p>
 * Esta clase proporciona la interfaz gráfica para que un usuario (generalmente un administrador)
 * pueda buscar un producto por su código, ver sus detalles (nombre y precio) y
 * confirmar su eliminación.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class ProductoEliminarView extends JInternalFrame {
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Campo de texto para ingresar el código del producto a buscar.
     */
    private JTextField txtCodigo;
    /**
     * Campo de texto para mostrar el precio del producto encontrado. No es editable.
     */
    private JTextField txtPrecio;
    /**
     * Botón para iniciar la búsqueda del producto por su código.
     */
    private JButton btnBuscar;
    /**
     * Etiqueta para el campo de código.
     */
    private JLabel lblCodigo;
    /**
     * Etiqueta para el campo de nombre.
     */
    private JLabel lblNombre;
    /**
     * Campo de texto para mostrar el nombre del producto encontrado. No es editable.
     */
    private JTextField txtNombre;
    /**
     * Etiqueta para el campo de precio.
     */
    private JLabel lblPrecio;
    /**
     * Botón para confirmar la eliminación del producto mostrado.
     */
    private JButton btnEliminar;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;

    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor de la vista ProductoEliminarView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga los iconos
     * para los botones y llama al método para actualizar los textos según el idioma.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public ProductoEliminarView(MensajeInternacionalizacionHandler mensajes){

        super(mensajes.get("producto.eliminar.titulo.app"),true,true,false,true);
        this.mensajes = mensajes;
        setContentPane(panelPrincipal);

        URL urlBuscar=getClass().getResource("/search.png");
        URL urlEliminar=getClass().getResource("/trash.png");

        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        btnEliminar.setIcon(new ImageIcon(urlEliminar));

        actualizarTextos();

        setSize(500,500);
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, los textos de los botones y
     * el tooltip del campo de código según el idioma configurado en el
     * manejador de mensajes.
     */
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
     * Obtiene el campo de texto del código.
     * @return El componente JTextField para el código.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Establece el campo de texto del código.
     * @param txtCodigo El nuevo componente JTextField para el código.
     */
    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    /**
     * Obtiene el campo de texto del precio.
     * @return El componente JTextField para el precio.
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Establece el campo de texto del precio.
     * @param txtPrecio El nuevo componente JTextField para el precio.
     */
    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
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
     * Obtiene la etiqueta del campo de código.
     * @return El componente JLabel para el código.
     */
    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    /**
     * Establece la etiqueta del campo de código.
     * @param lblCodigo El nuevo componente JLabel para el código.
     */
    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    /**
     * Obtiene la etiqueta del campo de nombre.
     * @return El componente JLabel para el nombre.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Establece la etiqueta del campo de nombre.
     * @param lblNombre El nuevo componente JLabel para el nombre.
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * Obtiene el campo de texto del nombre.
     * @return El componente JTextField para el nombre.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto del nombre.
     * @param txtNombre El nuevo componente JTextField para el nombre.
     */
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * Obtiene la etiqueta del campo de precio.
     * @return El componente JLabel para el precio.
     */
    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    /**
     * Establece la etiqueta del campo de precio.
     * @param lblPrecio El nuevo componente JLabel para el precio.
     */
    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
    }

    /**
     * Obtiene el botón de eliminar.
     * @return El componente JButton para eliminar.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Establece el botón de eliminar.
     * @param btnEliminar El nuevo componente JButton para eliminar.
     */
    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
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
     * Limpia los campos de texto de código, nombre y precio, y reactiva
     * el campo de código para una nueva búsqueda.
     */
    public void limpiarCampos(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCodigo.setEnabled(true);
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje informativo.
     * @param mensaje El texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}