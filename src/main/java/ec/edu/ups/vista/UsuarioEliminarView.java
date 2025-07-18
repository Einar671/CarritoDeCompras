package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

/**
 * Vista (JInternalFrame) para la eliminación de usuarios por parte de un administrador.
 * <p>
 * Esta clase proporciona la interfaz gráfica para que un administrador pueda
 * buscar un usuario por su nombre de usuario (cédula), ver sus datos
 * (contraseña y rol) y proceder a su eliminación del sistema.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class UsuarioEliminarView extends JInternalFrame {
    /**
     * Etiqueta para el campo de nombre de usuario.
     */
    private JLabel lblUsuario;
    /**
     * Botón para iniciar la búsqueda del usuario por su nombre de usuario.
     */
    private JButton btnBuscar;
    /**
     * Campo de texto para ingresar el nombre de usuario a buscar.
     */
    private JTextField txtUsuario;
    /**
     * Etiqueta para el campo de contraseña.
     */
    private JLabel lblContraseña;
    /**
     * Etiqueta para el campo de rol.
     */
    private JLabel lblRol;
    /**
     * Campo de texto para mostrar la contraseña del usuario encontrado. No es editable.
     */
    private JTextField txtContraseña;
    /**
     * Botón para confirmar la eliminación del usuario mostrado.
     */
    private JButton btnEliminar;
    /**
     * Campo de texto para mostrar el rol del usuario encontrado. No es editable.
     */
    private JTextField txtRol;
    /**
     * Panel para agrupar el título de la ventana.
     */
    private JPanel paneltitulo;
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;

    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor de la vista UsuarioEliminarView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga los iconos
     * para los botones y llama al método para actualizar los textos según el idioma.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public UsuarioEliminarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        setContentPane(panelPrincipal);

        this.mensajes = mensajes;
        setSize(600, 400);
        URL urlBuscar = getClass().getResource("/search.png");
        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        URL urlEliminar = getClass().getResource("/trash.png");
        btnEliminar.setIcon(new ImageIcon(urlEliminar));


        actualizarTextos();
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, los textos de los botones y
     * el tooltip del campo de usuario según el idioma configurado en el
     * manejador de mensajes.
     */
    public void actualizarTextos() {
        setTitle(mensajes.get("usuario.eliminar.titulo.app"));
        lblTitulo.setText(mensajes.get("usuario.eliminar.titulo.app"));
        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");
        lblRol.setText(mensajes.get("global.rol") + ":");
        txtUsuario.setToolTipText(mensajes.get("usuario.crear.nombre"));
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnEliminar.setText(mensajes.get("global.boton.eliminar"));
    }

    /**
     * Obtiene el botón de buscar.
     * @return El componente JButton para buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Obtiene el campo de texto del nombre de usuario.
     * @return El componente JTextField para el nombre de usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Obtiene el campo de texto de la contraseña.
     * @return El componente JTextField para la contraseña.
     */
    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    /**
     * Obtiene el botón de eliminar.
     * @return El componente JButton para eliminar.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Obtiene el campo de texto del rol.
     * @return El componente JTextField para el rol.
     */
    public JTextField getTxtRol() {
        return txtRol;
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje informativo.
     * @param mensaje El texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Limpia todos los campos de texto y restablece el estado de los componentes
     * a su configuración inicial para permitir una nueva búsqueda.
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        txtRol.setText("");
        btnEliminar.setEnabled(false);
        txtContraseña.setEnabled(false);
        txtRol.setEnabled(false);
        txtUsuario.setEditable(true);
        btnBuscar.setEnabled(true);
    }
}