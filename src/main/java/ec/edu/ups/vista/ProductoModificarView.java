package ec.edu.ups.vista;

// 1. Importar el manejador de internacionalización
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Vista (JInternalFrame) para modificar un producto existente en el sistema.
 * <p>
 * Esta clase proporciona la interfaz gráfica para que un usuario (generalmente un administrador)
 * pueda buscar un producto por su código, ver sus datos actuales (nombre y precio) y
 * luego modificar estos datos y guardar los cambios.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class ProductoModificarView extends JInternalFrame {
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Campo de texto para ingresar el código del producto a buscar.
     */
    private JTextField txtCodigo;
    /**
     * Campo de texto para mostrar y editar el nombre del producto.
     */
    private JTextField txtNombre;
    /**
     * Campo de texto para mostrar y editar el precio del producto.
     */
    private JTextField txtPrecio;
    /**
     * Botón para iniciar la búsqueda del producto por su código.
     */
    private JButton btnBuscar;
    /**
     * Botón para confirmar y guardar las modificaciones realizadas al producto.
     */
    private JButton btnModificar;
    /**
     * Etiqueta para el campo de código.
     */
    private JLabel lblCodigo;
    /**
     * Etiqueta para el campo de nombre.
     */
    private JLabel lblNombre;
    /**
     * Etiqueta para el campo de precio.
     */
    private JLabel lblPrecio;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;

    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor de la vista ProductoModificarView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga los iconos
     * para los botones y llama al método para actualizar los textos según el idioma.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public ProductoModificarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;
        setContentPane(panelPrincipal);

        URL urlBuscar = getClass().getResource("/search.png");
        URL urlModificar = getClass().getResource("/edit.png");

        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        btnModificar.setIcon(new ImageIcon(urlModificar));

        actualizarTextos();

        setSize(500, 500);
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, los textos de los botones y
     * los tooltips de los campos de texto según el idioma configurado en el
     * manejador de mensajes.
     */
    public void actualizarTextos() {
        setTitle(mensajes.get("producto.modificar.titulo.app"));
        lblTitulo.setText(mensajes.get("producto.modificar.titulo"));
        lblCodigo.setText(mensajes.get("global.codigo"));
        lblNombre.setText(mensajes.get("global.nombre"));
        lblPrecio.setText(mensajes.get("global.precio"));
        txtCodigo.setToolTipText(mensajes.get("producto.top.codigo"));
        txtNombre.setToolTipText(mensajes.get("producto.top.nombre"));
        txtPrecio.setToolTipText(mensajes.get("producto.top.precio"));
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnModificar.setText(mensajes.get("global.boton.modificar"));
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
     * Obtiene el botón de modificar.
     * @return El componente JButton para modificar.
     */
    public JButton getBtnModificar() {
        return btnModificar;
    }

    /**
     * Establece el botón de modificar.
     * @param btnModificar El nuevo componente JButton para modificar.
     */
    public void setBtnModificar(JButton btnModificar) {
        this.btnModificar = btnModificar;
    }

    /**
     * Limpia los campos de texto de código, nombre y precio, y reactiva
     * el campo de código para una nueva búsqueda.
     */
    public void limpiarCampos() {
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