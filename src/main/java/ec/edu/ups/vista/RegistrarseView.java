package ec.edu.ups.vista;

import ec.edu.ups.modelo.Genero;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Vista (JFrame) para el registro de nuevos usuarios.
 * <p>
 * Esta clase proporciona la interfaz gráfica para que un nuevo usuario pueda
 * ingresar sus datos personales y credenciales (nombre de usuario, contraseña,
 * nombre completo, edad, género, etc.) para crear una cuenta en el sistema.
 * <p>
 * Incluye un menú para cambiar el idioma de la interfaz en tiempo real.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class RegistrarseView extends JFrame {
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Panel secundario, posiblemente para agrupar componentes.
     */
    private JPanel panelSecundario;
    /**
     * Etiqueta para el campo de nombre de usuario.
     */
    private JLabel lblUsername;
    /**
     * Etiqueta para el campo de contraseña.
     */
    private JLabel lblPassword;
    /**
     * Campo de texto para que el usuario ingrese su nombre de usuario (cédula).
     */
    private JTextField txtUsername;
    /**
     * Campo de contraseña para que el usuario ingrese su clave.
     */
    private JPasswordField txtContraseña;
    /**
     * Botón para enviar el formulario de registro.
     */
    private JButton btnRegistrarse;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;
    /**
     * Campo de contraseña para que el usuario repita su clave.
     */
    private JPasswordField txtRepContra;
    /**
     * Etiqueta para el campo de repetir contraseña.
     */
    private JLabel lblRepContra;
    /**
     * Campo de texto para que el usuario ingrese su nombre completo.
     */
    private JTextField txtNombreCom;
    /**
     * Spinner para que el usuario seleccione su edad.
     */
    private JSpinner spnEdad;
    /**
     * ComboBox para que el usuario seleccione su género.
     */
    private JComboBox cbxGenero;
    /**
     * Campo de texto para que el usuario ingrese su número de teléfono.
     */
    private JTextField txtTelefono;
    /**
     * Campo de texto para que el usuario ingrese su correo electrónico.
     */
    private JTextField txtEmail;
    /**
     * Etiqueta para el campo de nombre completo.
     */
    private JLabel lblNombre;
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
     * Botón para volver a la ventana anterior (login).
     */
    private JButton btnAtras;
    /**
     * La barra de menú principal de la ventana.
     */
    private JMenuBar menubar;
    /**
     * El menú desplegable para seleccionar el idioma de la aplicación.
     */
    private JMenu menuIdiomas;
    /**
     * La opción de menú para cambiar el idioma a español.
     */
    private JMenuItem menuItemEspañol;
    /**
     * La opción de menú para cambiar el idioma a inglés.
     */
    private JMenuItem menuItemIngles;
    /**
     * La opción de menú para cambiar el idioma a noruego.
     */
    private JMenuItem menuItemNoruego;

    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor de la vista RegistrarseView.
     * <p>
     * Inicializa el JFrame, configura los componentes, carga los iconos para los botones,
     * establece el menú de idiomas, configura el modelo del spinner de edad y llama a los
     * métodos para actualizar los textos y el ComboBox de género.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public RegistrarseView(MensajeInternacionalizacionHandler mensajes) {
        setContentPane(panelPrincipal);
        this.mensajes=mensajes;
        menubar = new JMenuBar();
        menuIdiomas = new JMenu();
        menuItemEspañol = new JMenuItem();
        menuItemIngles = new JMenuItem();
        menuItemNoruego = new JMenuItem();
        URL urlReg = getClass().getResource("/add.png");
        URL urlAtras = getClass().getResource("/back.png");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        btnRegistrarse.setIcon(new ImageIcon(urlReg));
        btnAtras.setIcon(new ImageIcon(urlAtras));
        setLocationRelativeTo(null);
        setResizable(false);
        spnEdad.setModel(new SpinnerNumberModel(18, 18, 120, 1));
        menubar.add(menuIdiomas);
        menuIdiomas.add(menuItemIngles);
        menuIdiomas.add(menuItemEspañol);
        menuIdiomas.add(menuItemNoruego);
        setJMenuBar(menubar);
        actualizarTextos();
        actualizarComboBox();
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, los textos de los botones y los
     * ítems del menú. También configura un {@link ListCellRenderer} personalizado para
     * el ComboBox de género, de modo que los valores del enum {@link Genero} se
     * muestren en el idioma seleccionado.
     */
    void actualizarTextos() {
        menuIdiomas.setText(mensajes.get("menu.idiomas"));
        menuItemEspañol.setText(mensajes.get("menu.idioma.es"));
        menuItemIngles.setText(mensajes.get("menu.idioma.en"));
        menuItemNoruego.setText(mensajes.get("menu.idioma.nw"));
        setTitle(mensajes.get("login.boton.reg"));
        btnAtras.setText(mensajes.get("global.atras"));
        lblTitulo.setText(mensajes.get("login.boton.reg"));
        lblUsername.setText(mensajes.get("global.usuario"));
        lblPassword.setText(mensajes.get("global.contraseña"));
        lblRepContra.setText(mensajes.get("register.app.rep"));
        btnRegistrarse.setText(mensajes.get("login.boton.reg"));
        lblEdad.setText(mensajes.get("global.edad"));
        lblGenero.setText(mensajes.get("global.genero"));
        lblTelefono.setText(mensajes.get("global.telefono"));
        lblEmail.setText(mensajes.get("global.email"));
        lblNombre.setText(mensajes.get("global.nombreCompleto"));
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
     * Carga o actualiza los ítems en el ComboBox de género.
     * <p>
     * Limpia el ComboBox y lo vuelve a poblar con todos los valores del enum {@link Genero}.
     * Finalmente, deselecciona cualquier ítem.
     */
    void actualizarComboBox() {
        cbxGenero.removeAllItems();
        for (Genero g : Genero.values()) {
            cbxGenero.addItem(g);
        }
        cbxGenero.setSelectedIndex(-1);
    }

    /**
     * Obtiene el botón de "Atrás".
     * @return El componente JButton para volver atrás.
     */
    public JButton getBtnAtras() {
        return btnAtras;
    }

    /**
     * Obtiene el campo de texto del nombre completo.
     * @return El componente JTextField para el nombre completo.
     */
    public JTextField getTxtNombreCom() {
        return txtNombreCom;
    }

    /**
     * Establece el campo de texto del nombre completo.
     * @param txtNombreCom El nuevo componente JTextField para el nombre completo.
     */
    public void setTxtNombreCom(JTextField txtNombreCom) {
        this.txtNombreCom = txtNombreCom;
    }

    /**
     * Obtiene el spinner de edad.
     * @return El componente JSpinner para la edad.
     */
    public JSpinner getSpnEdad() {
        return spnEdad;
    }

    /**
     * Establece el spinner de edad.
     * @param spnEdad El nuevo componente JSpinner para la edad.
     */
    public void setSpnEdad(JSpinner spnEdad) {
        this.spnEdad = spnEdad;
    }

    /**
     * Obtiene el ComboBox de género.
     * @return El componente JComboBox para el género.
     */
    public JComboBox getCbxGenero() {
        return cbxGenero;
    }

    /**
     * Establece el ComboBox de género.
     * @param cbxGenero El nuevo componente JComboBox para el género.
     */
    public void setCbxGenero(JComboBox cbxGenero) {
        this.cbxGenero = cbxGenero;
    }

    /**
     * Obtiene el campo de texto del teléfono.
     * @return El componente JTextField para el teléfono.
     */
    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    /**
     * Establece el campo de texto del teléfono.
     * @param txtTelefono El nuevo componente JTextField para el teléfono.
     */
    public void setTxtTelefono(JTextField txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    /**
     * Obtiene el campo de texto del email.
     * @return El componente JTextField para el email.
     */
    public JTextField getTxtEmail() {
        return txtEmail;
    }

    /**
     * Establece el campo de texto del email.
     * @param txtEmail El nuevo componente JTextField para el email.
     */
    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    /**
     * Obtiene el campo de texto del nombre de usuario.
     * @return El componente JTextField para el nombre de usuario.
     */
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    /**
     * Establece el campo de texto del nombre de usuario.
     * @param txtUsername El nuevo componente JTextField para el nombre de usuario.
     */
    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    /**
     * Obtiene el campo de contraseña.
     * @return El componente JPasswordField para la contraseña.
     */
    public JPasswordField getTxtContraseña() {
        return txtContraseña;
    }

    /**
     * Establece el campo de contraseña.
     * @param txtContraseña El nuevo componente JPasswordField para la contraseña.
     */
    public void setTxtContraseña(JPasswordField txtContraseña) {
        this.txtContraseña = txtContraseña;
    }

    /**
     * Obtiene el botón de "Registrarse".
     * @return El componente JButton para registrarse.
     */
    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    /**
     * Establece el botón de "Registrarse".
     * @param btnRegistrarse El nuevo componente JButton para registrarse.
     */
    public void setBtnRegistrarse(JButton btnRegistrarse) {
        this.btnRegistrarse = btnRegistrarse;
    }

    /**
     * Obtiene el campo de repetir contraseña.
     * @return El componente JPasswordField para repetir la contraseña.
     */
    public JPasswordField getTxtRepContra() {
        return txtRepContra;
    }

    /**
     * Establece el campo de repetir contraseña.
     * @param txtRepContra El nuevo componente JPasswordField para repetir la contraseña.
     */
    public void setTxtRepContra(JPasswordField txtRepContra) {
        this.txtRepContra = txtRepContra;
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje informativo.
     * @param mensaje El texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Obtiene la opción de menú para el idioma español.
     * @return El componente JMenuItem para español.
     */
    public JMenuItem getMenuItemEspañol() {
        return menuItemEspañol;
    }

    /**
     * Obtiene la opción de menú para el idioma inglés.
     * @return El componente JMenuItem para inglés.
     */
    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    /**
     * Obtiene la opción de menú para el idioma noruego.
     * @return El componente JMenuItem para noruego.
     */
    public JMenuItem getMenuItemNoruego() {
        return menuItemNoruego;
    }

    /**
     * Limpia los campos de texto de usuario y contraseñas.
     */
    public void limpiarCampos() {
        txtUsername.setText("");
        txtContraseña.setText("");
        txtRepContra.setText("");
    }
}