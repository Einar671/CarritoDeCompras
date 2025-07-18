package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Vista (JInternalFrame) para listar y buscar usuarios del sistema.
 * <p>
 * Esta clase proporciona una interfaz gráfica para que un administrador pueda
 * ver una lista de todos los usuarios registrados, así como realizar búsquedas
 * por nombre de usuario para filtrar los resultados en la tabla.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class UsuarioListarView extends JInternalFrame {
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Botón para mostrar (o volver a cargar) la lista completa de todos los usuarios.
     */
    private JButton btnListar;
    /**
     * Tabla que muestra la lista de usuarios.
     */
    private JTable tblUsuarios;
    /**
     * Etiqueta para el campo de búsqueda de usuario.
     */
    private JLabel lblListado;
    /**
     * Campo de texto para que el administrador ingrese el nombre de usuario a buscar.
     */
    private JTextField txtUsuario;
    /**
     * Botón para iniciar la búsqueda de usuarios según el nombre ingresado.
     */
    private JButton btnBuscar;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;
    /**
     * Modelo de datos para la tabla {@code tblUsuarios}.
     */
    private DefaultTableModel tableModel;
    /**
     * El locale actual, utilizado para la internacionalización.
     */
    private Locale locale;
    /**
     * La lista de usuarios que se muestra actualmente en la tabla.
     */
    private List<Usuario> listaActual;
    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor de la vista UsuarioListarView.
     * <p>
     * Inicializa el JInternalFrame, configura los componentes, carga los iconos
     * para los botones, configura la tabla y llama al método para actualizar
     * los textos según el idioma.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public UsuarioListarView(MensajeInternacionalizacionHandler mensajes) {

        super("", true, true, false, true);
        this.mensajes = mensajes;
        setContentPane(panelPrincipal);
        setSize(600, 400);
        URL urlBuscar = getClass().getResource("/search.png");
        URL urlListar = getClass().getResource("/list.png");
        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        btnListar.setIcon(new ImageIcon(urlListar));
        configurarTabla();
        actualizarTextos();
    }

    /**
     * Configura la tabla de usuarios inicializando su modelo de datos.
     */
    private void configurarTabla() {
        tableModel = new DefaultTableModel();
        tblUsuarios.setModel(tableModel);
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, los textos de los botones,
     * el tooltip del campo de búsqueda y los encabezados de la tabla según el
     * {@code Locale} configurado en el manejador de mensajes.
     * Al final, vuelve a mostrar los usuarios para que los formatos se actualicen.
     */
    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));
        setTitle(mensajes.get("usuario.listar.titulo.app"));
        lblTitulo.setText(mensajes.get("usuario.listar.titulo.app"));
        lblListado.setText(mensajes.get("global.usuario"));
        btnListar.setText(mensajes.get("menu.usuario.listar"));
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        txtUsuario.setToolTipText(mensajes.get("mensaje.usuario.buscar.vacio"));

        tableModel.setColumnIdentifiers(new Object[]{
                mensajes.get("global.usuario"),
                mensajes.get("global.rol")
        });
        mostrarUsuarios(listaActual);
    }

    /**
     * Muestra una lista de objetos {@link Usuario} en la tabla de la interfaz.
     * <p>
     * Limpia la tabla y la vuelve a poblar con la lista de usuarios proporcionada.
     * El rol del usuario se traduce al idioma actual antes de ser mostrado.
     *
     * @param usuarios La lista de usuarios a mostrar. Si es nula, la tabla se limpia.
     */
    public void mostrarUsuarios(List<Usuario> usuarios) {
        this.listaActual = usuarios;
        tableModel.setRowCount(0);
        if(usuarios==null) return;
        for (Usuario usuario : usuarios) {
            String rolTraducido = "";
            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                rolTraducido = mensajes.get("global.rol.admin");
            } else if (usuario.getRol() == Rol.USUARIO) {
                rolTraducido = mensajes.get("global.rol.user");
            }

            tableModel.addRow(new Object[]{
                    usuario.getUsername(),
                    rolTraducido
            });
        }
    }

    /**
     * Obtiene el botón de listar todos los usuarios.
     * @return El componente JButton para listar.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Obtiene el botón de buscar.
     * @return El componente JButton para buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Obtiene el campo de texto para la búsqueda de usuario.
     * @return El componente JTextField para la búsqueda.
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
}