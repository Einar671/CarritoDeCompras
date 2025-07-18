package ec.edu.ups.vista;

import ec.edu.ups.modelo.Genero;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Vista (JInternalFrame) para que un usuario modifique sus propios datos personales.
 * <p>
 * Esta clase proporciona la interfaz gráfica que permite a un usuario logueado
 * ver y actualizar su información personal, como nombre de usuario, contraseña,
 * nombre completo, edad, género, teléfono y correo electrónico.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class UsuarioModificarMisView extends JInternalFrame {
    /**
     * Etiqueta para el campo de nombre de usuario actual.
     */
    private JLabel lblUsuario;
    /**
     * Etiqueta para el campo de contraseña.
     */
    private JLabel lblContraseña;
    /**
     * Campo de texto para ingresar la nueva contraseña.
     */
    private JTextField txtContraseña;
    /**
     * Botón para confirmar y guardar las modificaciones.
     */
    private JButton btnModificar;
    /**
     * Campo de texto para mostrar el nombre de usuario actual (no editable).
     */
    private JTextField txtUsuario;
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;
    /**
     * Campo de texto para ingresar el nuevo nombre de usuario.
     */
    private JTextField txtNuevoUser;
    /**
     * Etiqueta para el campo de nuevo nombre de usuario.
     */
    private JLabel lblNuevoUser;
    /**
     * Spinner para seleccionar la edad del usuario.
     */
    private JSpinner sprEdad;
    /**
     * ComboBox para seleccionar el género del usuario.
     */
    private JComboBox cbxGenero;
    /**
     * Campo de texto para ingresar el número de teléfono.
     */
    private JTextField txtTelefono;
    /**
     * Campo de texto para ingresar el correo electrónico.
     */
    private JTextField txtEmail;
    /**
     * Campo de texto para ingresar el nombre completo.
     */
    private JTextField txtNombreCom;
    /**
     * Panel que contiene la etiqueta del nombre. (Nombre de variable confuso).
     */
    private JPanel lblNombre;
    /**
     * Etiqueta para el spinner de edad.
     */
    private JLabel lblEdad;
    /**
     * Etiqueta para el ComboBox de género.
     */
    private JLabel lblGenero;
    /**
     * Etiqueta para el campo de teléfono.
     */
    private JLabel lblTelefono;
    /**
     * Etiqueta para el campo de email.
     */
    private JLabel lblEmail;
    /**
     * Etiqueta para el campo de nombre completo.
     */
    private JLabel lblNombreCom;

    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor de la vista UsuarioModificarMisView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga el icono
     * para el botón, establece el modelo del spinner de edad y llama a los métodos
     * para actualizar los textos y el ComboBox de género.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public UsuarioModificarMisView(MensajeInternacionalizacionHandler mensajes) {

        super("", true, true, false, true);
        setContentPane(panelPrincipal);

        URL urlModificar = getClass().getResource("/edit.png");
        btnModificar.setIcon(new ImageIcon(urlModificar));
        this.mensajes = mensajes;


        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        sprEdad.setModel(new SpinnerNumberModel(18, 18, 120, 1));

        txtUsuario.setEditable(false);
        actualizarComboBox();

        actualizarTextos();
    }

    /**
     * Carga o actualiza los ítems en el ComboBox de género.
     * <p>
     * Limpia el ComboBox y lo vuelve a poblar con todos los valores del enum {@link Genero}.
     * Finalmente, deselecciona cualquier ítem.
     */
    private void actualizarComboBox() {
        cbxGenero.removeAllItems();
        for (Genero g : Genero.values()) {
            cbxGenero.addItem(g);
        }
        cbxGenero.setSelectedIndex(-1);
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana y todas las etiquetas. También configura un
     * {@link ListCellRenderer} personalizado para el ComboBox de género, de modo que
     * los valores del enum {@link Genero} se muestren en el idioma seleccionado.
     */
    public void actualizarTextos() {
        setTitle(mensajes.get("menu.usuario.modificarMis"));
        lblTitulo.setText(mensajes.get("menu.usuario.modificarMis"));
        lblNuevoUser.setText(mensajes.get("global.nuevoUsuario"));
        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");
        btnModificar.setText(mensajes.get("global.boton.modificar"));
        lblEmail.setText(mensajes.get("global.email"));
        lblTelefono.setText(mensajes.get("global.telefono"));
        lblEdad.setText(mensajes.get("global.edad"));
        lblGenero.setText(mensajes.get("global.genero"));
        lblNombreCom.setText(mensajes.get("global.nombreCompleto"));
        cbxGenero.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Genero) {
                    Genero rol = (Genero) value;
                    if (rol == Genero.MASCULINO) {
                        setText(mensajes.get("global.genero.masculino"));
                    } else if (rol == Genero.FEMENINO) {
                        setText(mensajes.get("global.genero.femenino"));
                    } else if(rol == Genero.OTRO){
                        setText(mensajes.get("global.genero.otro"));
                    }
                }
                return this;
            }
        });
    }

    /**
     * Obtiene el spinner de edad.
     * @return El componente JSpinner para la edad.
     */
    public JSpinner getSprEdad() {
        return sprEdad;
    }

    /**
     * Obtiene el ComboBox de género.
     * @return El componente JComboBox para el género.
     */
    public JComboBox getCbxGenero() {
        return cbxGenero;
    }

    /**
     * Obtiene el campo de texto del teléfono.
     * @return El componente JTextField para el teléfono.
     */
    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    /**
     * Obtiene el campo de texto del email.
     * @return El componente JTextField para el email.
     */
    public JTextField getTxtEmail() {
        return txtEmail;
    }

    /**
     * Obtiene el campo de texto del nombre completo.
     * @return El componente JTextField para el nombre completo.
     */
    public JTextField getTxtNombreCom() {
        return txtNombreCom;
    }

    /**
     * Obtiene el campo de texto del nuevo nombre de usuario.
     * @return El componente JTextField para el nuevo nombre de usuario.
     */
    public JTextField getTxtNuevoUser() {
        return txtNuevoUser;
    }

    /**
     * Establece el campo de texto del nuevo nombre de usuario.
     * @param txtNuevoUser El nuevo componente JTextField para el nuevo nombre de usuario.
     */
    public void setTxtNuevoUser(JTextField txtNuevoUser) {
        this.txtNuevoUser = txtNuevoUser;
    }

    /**
     * Obtiene el campo de texto de la contraseña.
     * @return El componente JTextField para la contraseña.
     */
    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    /**
     * Obtiene el botón de modificar.
     * @return El componente JButton para modificar.
     */
    public JButton getBtnModificar() {
        return btnModificar;
    }

    /**
     * Obtiene el campo de texto del nombre de usuario actual.
     * @return El componente JTextField para el nombre de usuario actual.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje informativo.
     * @param mensaje El texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Limpia el campo de texto de la contraseña.
     */
    public void limpiarCampos() {
        txtContraseña.setText("");
    }
}