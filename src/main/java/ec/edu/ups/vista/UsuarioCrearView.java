package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Vista (JInternalFrame) para la creación de nuevos usuarios por parte de un administrador.
 * <p>
 * Esta clase proporciona la interfaz gráfica para que un administrador pueda
 * ingresar un nombre de usuario, una contraseña y asignar un rol (Administrador o Usuario)
 * para crear una nueva cuenta en el sistema.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class UsuarioCrearView extends JInternalFrame {
    /**
     * Etiqueta para el campo de nombre de usuario.
     */
    private JLabel lblUsuarioA;
    /**
     * Etiqueta para el campo de contraseña.
     */
    private JLabel lblContraseña;
    /**
     * Etiqueta para el ComboBox de rol.
     */
    private JLabel lblRol;
    /**
     * Campo de texto para ingresar el nombre de usuario.
     */
    private JTextField txtUsuario;
    /**
     * Campo de texto para ingresar la contraseña.
     */
    private JTextField txtContraseña;
    /**
     * Botón para confirmar y crear el nuevo usuario.
     */
    private JButton btnCrear;
    /**
     * ComboBox para seleccionar el rol del nuevo usuario.
     */
    private JComboBox<Rol> cbxRoles;
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
     * Constructor de la vista UsuarioCrearView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga el icono
     * para el botón de crear y llama a los métodos para actualizar los textos
     * y cargar los roles en el ComboBox.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public UsuarioCrearView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        setContentPane(panelPrincipal);

        this.mensajes = mensajes;
        URL urlCrear = getClass().getResource("/plus.png");
        btnCrear.setIcon(new ImageIcon(urlCrear));
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);

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
        setTitle(mensajes.get("usuario.crear.titulo.app"));
        lblTitulo.setText(mensajes.get("usuario.crear.titulo.app"));
        lblUsuarioA.setText(mensajes.get("global.usuario")+": ");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");
        lblRol.setText(mensajes.get("global.rol") + ":");
        txtUsuario.setToolTipText(mensajes.get("usuario.crear.nombre"));
        txtContraseña.setToolTipText(mensajes.get("usuario.crear.contraseña"));
        cbxRoles.setToolTipText(mensajes.get("usuario.crear.rol"));
        btnCrear.setText(mensajes.get("global.crear"));


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
     * Obtiene el campo de texto de la contraseña.
     * @return El componente JTextField para la contraseña.
     */
    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    /**
     * Obtiene el botón de crear.
     * @return El componente JButton para crear el usuario.
     */
    public JButton getBtnCrear() {
        return btnCrear;
    }

    /**
     * Obtiene el ComboBox de roles.
     * @return El componente JComboBox para seleccionar el rol.
     */
    public JComboBox<Rol> getCbxRoles() {
        return cbxRoles;
    }

    /**
     * Limpia los campos de texto de usuario y contraseña y restablece
     * la selección del ComboBox de roles al primer ítem.
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        if (cbxRoles.getItemCount() > 0) {
            cbxRoles.setSelectedIndex(0);
        }
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje informativo.
     * @param mensaje El texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}