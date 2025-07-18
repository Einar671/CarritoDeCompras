package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
// 1. Importar el manejador de internacionalización
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

/**
 * Vista (JInternalFrame) para añadir un nuevo producto al sistema.
 * <p>
 * Esta clase proporciona la interfaz gráfica para que un usuario (generalmente un administrador)
 * pueda ingresar los datos de un nuevo producto (código, nombre y precio) y
 * guardarlo.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class ProductoAnadirView extends JInternalFrame {

    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Campo de texto para ingresar el precio del producto.
     */
    private JTextField txtPrecio;
    /**
     * Campo de texto para ingresar el nombre del producto.
     */
    private JTextField txtNombre;
    /**
     * Campo de texto para ingresar el código del producto.
     */
    private JTextField txtCodigo;
    /**
     * Botón para confirmar y guardar el nuevo producto.
     */
    private JButton btnAceptar;
    /**
     * Botón para limpiar todos los campos de entrada de texto.
     */
    private JButton btnLimpiar;
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
     * Constructor de la vista ProductoAnadirView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga los iconos
     * para los botones, establece un listener para el botón de limpiar y llama al método
     * para actualizar los textos según el idioma.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public ProductoAnadirView(MensajeInternacionalizacionHandler mensajes) {
        super(mensajes.get("producto.crear.titulo.app"), true, true, true, true);
        setContentPane(panelPrincipal);
        this.mensajes = mensajes;
        URL urlAñadir = getClass().getResource("/check.png");
        URL urlLimpiar = getClass().getResource("/clean.png");

        btnAceptar.setIcon(new ImageIcon(urlAñadir));
        btnLimpiar.setIcon(new ImageIcon(urlLimpiar));

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


    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, los textos de los botones y
     * los tooltips de los campos de texto según el idioma configurado en el
     * manejador de mensajes.
     */
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
     * Obtiene el botón de aceptar/crear.
     * @return El componente JButton para aceptar.
     */
    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    /**
     * Establece el botón de aceptar/crear.
     * @param btnAceptar El nuevo componente JButton para aceptar.
     */
    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    /**
     * Obtiene el botón de limpiar.
     * @return El componente JButton para limpiar.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Establece el botón de limpiar.
     * @param btnLimpiar El nuevo componente JButton para limpiar.
     */
    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje informativo.
     * @param mensaje El texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Limpia los campos de texto de código, nombre y precio.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    /**
     * Imprime una lista de productos en la consola del sistema.
     * Este método es principalmente para propósitos de depuración.
     * @param productos La lista de {@link Producto} a mostrar.
     */
    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }
}