package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Vista (JInternalFrame) para la modificación de usuarios por parte de un administrador.
 * <p>
 * Esta clase proporciona la interfaz gráfica para que un administrador pueda
 * buscar un usuario por su nombre de usuario, ver sus datos y modificar
 * su contraseña y rol.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class UsuarioModificarView extends JInternalFrame {
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Etiqueta para el campo de nombre de usuario.
     */
    private JLabel lblUsuario;
    /**
     * Campo de texto para ingresar el nombre de usuario a buscar.
     */
    private JTextField txtUsuario;
    /**
     * Botón para iniciar la búsqueda del usuario.
     */
    private JButton btnBuscar;
    /**
     * Etiqueta para el campo de contraseña.
     */
    private JLabel lblContraseña;
    /**
     * Campo de texto para mostrar y editar la contraseña del usuario.
     */
    private JTextField txtContraseña;
    /**
     * Etiqueta para el ComboBox de rol.
     */
    private JLabel lblRol;
    /**
     * ComboBox para mostrar y seleccionar el nuevo rol del usuario.
     */
    private JComboBox<Rol> cbxRoles;
    /**
     * Botón para confirmar y guardar las modificaciones.
     */
    private JButton btnModificar;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;

    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor de la vista UsuarioModificarView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga los iconos
     * para los botones y llama a los métodos para actualizar los textos y
     * cargar los roles en el ComboBox.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public UsuarioModificarView(MensajeInternacionalizacionHandler mensajes) {

        super("", true, true, false, true);
        setContentPane(panelPrincipal);

        this.mensajes = mensajes;

        setSize(600, 400);
        URL urlBuscar = getClass().getResource("/search.png");
        // Nota: El icono para el botón de modificar se está cargando desde un recurso llamado "trash.png".
        URL urlEliminar=getClass().getResource("/trash.png");

        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        btnModificar.setIcon(new ImageIcon(urlEliminar));
        actualizarTextos();
        cargarRoles();
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, los textos de los botones y
     * los tooltips. También configura un {@link ListCellRenderer} personalizado para
     * el ComboBox de roles, de modo que los valores del enum {@link Rol} se
     * muestren en el idioma seleccionado.
     */
    public void actualizarTextos() {
        setTitle(mensajes.get("usuario.modificar.titulo.app"));

        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");
        lblRol.setText(mensajes.get("global.rol") + ":");
        lblTitulo.setText(mensajes.get("usuario.modificar.titulo.app"));
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnModificar.setText(mensajes.get("global.boton.modificar"));
        txtUsuario.setToolTipText(mensajes.get("mensaje.usuario.buscar.vacio"));
        cbxRoles.setToolTipText(mensajes.get("usuario.crear.rol"));
        txtContraseña.setToolTipText(mensajes.get("usuario.crear.contraseña"));

        cbxRoles.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Rol) {
                    Rol rol = (Rol) value;
                    if (rol == Rol.ADMINISTRADOR) {
                        setText(mensajes.get("global.rol.admin"));
                    } else if (rol == Rol.USUARIO) {
                        setText(mensajes.get("global.rol.user"));
                    }
                }
                return this;
            }
        });
    }

    /**
     * Carga los roles disponibles en el ComboBox.
     * <p>
     * Limpia el ComboBox y lo vuelve a poblar con todos los valores del enum {@link Rol}.
     */
    private void cargarRoles() {
        cbxRoles.removeAllItems();
        for (Rol rol : Rol.values()) {
            cbxRoles.addItem(rol);
        }
    }

    /**
     * Obtiene el campo de texto del nombre de usuario.
     * @return El componente JTextField para el nombre de usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Obtiene el botón de buscar.
     * @return El componente JButton para buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Obtiene el campo de texto de la contraseña.
     * @return El componente JTextField para la contraseña.
     */
    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    /**
     * Obtiene el ComboBox de roles.
     * @return El componente JComboBox para seleccionar el rol.
     */
    public JComboBox<Rol> getCbxRoles() {
        return cbxRoles;
    }

    /**
     * Obtiene el botón de modificar.
     * @return El componente JButton para modificar.
     */
    public JButton getBtnModificar() {
        return btnModificar;
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
        if (cbxRoles.getItemCount() > 0) {
            cbxRoles.setSelectedIndex(0);
        }
        txtUsuario.setEditable(true);
        btnBuscar.setEnabled(true);
        txtContraseña.setEnabled(false);
        cbxRoles.setEnabled(false);
        btnModificar.setEnabled(false);
    }
}