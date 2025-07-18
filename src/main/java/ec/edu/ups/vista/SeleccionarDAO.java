package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;

/**
 * Vista (JFrame) para seleccionar el modo de persistencia de datos (DAO).
 * <p>
 * Esta clase presenta al usuario una interfaz para elegir entre dos
 * mecanismos de almacenamiento de datos: en memoria o basado en archivos.
 * Si se elige la opción de archivos, se muestra un {@link JFileChooser} para
 * que el usuario seleccione el directorio base donde se guardarán los datos.
 * <p>
 * También incluye un menú para cambiar el idioma de la interfaz.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class SeleccionarDAO extends JFrame {
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel principalPane;
    /**
     * Checkbox para seleccionar el modo de persistencia en memoria.
     */
    private JCheckBox cbxMemoria;
    /**
     * Checkbox para seleccionar el modo de persistencia en archivos.
     */
    private JCheckBox cbxArchivo;
    /**
     * Selector de archivos para que el usuario elija el directorio de datos.
     * Se hace visible solo cuando se selecciona {@code cbxArchivo}.
     */
    private JFileChooser Archivos;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel titulo;
    /**
     * Botón para confirmar la selección del modo de persistencia.
     */
    private JButton btnAceptar;
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
     * El locale actual, utilizado para la internacionalización.
     */
    private Locale locale;

    /**
     * Constructor de la vista SeleccionarDAO.
     * <p>
     * Inicializa el JFrame, configura los componentes, establece los listeners
     * para los checkboxes que controlan la visibilidad del {@link JFileChooser},
     * construye el menú de idiomas y actualiza los textos de la UI.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public SeleccionarDAO(MensajeInternacionalizacionHandler mensajes) {
        setContentPane(principalPane);
        this.mensajes = mensajes;
        menubar = new JMenuBar();
        menuIdiomas = new JMenu();
        menuItemEspañol = new JMenuItem();
        menuItemIngles = new JMenuItem();
        menuItemNoruego = new JMenuItem();


        Archivos.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        Archivos.setAcceptAllFileFilterUsed(false);
        Archivos.setVisible(false);

        cbxArchivo.addActionListener(e -> {
            if (cbxArchivo.isSelected()) {
                cbxMemoria.setSelected(false);
                Archivos.setVisible(true);
            }
        });

        cbxMemoria.addActionListener(e -> {
            if (cbxMemoria.isSelected()) {
                cbxArchivo.setSelected(false);
                Archivos.setVisible(false);
            }
        });
        // Construir la barra de menú
        menubar.add(menuIdiomas);
        menuIdiomas.add(menuItemIngles);
        menuIdiomas.add(menuItemEspañol);
        menuIdiomas.add(menuItemNoruego);
        setJMenuBar(menubar);

        // Configuración final de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null); // Centrar en pantalla
        setResizable(false);

        actualizar();
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas, los textos de los botones,
     * los checkboxes y los ítems del menú según el idioma configurado en el
     * manejador de mensajes.
     */
    public void actualizar() {
        setTitle(mensajes.get("titulo.seleccionadorDAO"));
        titulo.setText(mensajes.get("titulo.seleccionadorDAO"));
        menuIdiomas.setText(mensajes.get("menu.idiomas"));
        menuItemEspañol.setText(mensajes.get("menu.idioma.es"));
        menuItemIngles.setText(mensajes.get("menu.idioma.en"));
        menuItemNoruego.setText(mensajes.get("menu.idioma.nw"));
        cbxMemoria.setText(mensajes.get("seleccionadorDAO.cbxMemoria"));
        cbxArchivo.setText(mensajes.get("seleccionadorDAO.cbxArchivo"));
        btnAceptar.setText(mensajes.get("global.boton.aceptar"));
    }

    /**
     * Obtiene el botón de aceptar.
     * @return El componente JButton para aceptar la selección.
     */
    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    /**
     * Obtiene el checkbox para la opción de persistencia en memoria.
     * @return El componente JCheckBox para la opción de memoria.
     */
    public JCheckBox getCbxMemoria() {
        return cbxMemoria;
    }

    /**
     * Obtiene el checkbox para la opción de persistencia en archivo.
     * @return El componente JCheckBox para la opción de archivo.
     */
    public JCheckBox getCbxArchivo() {
        return cbxArchivo;
    }

    /**
     * Obtiene el selector de archivos.
     * @return El componente JFileChooser para seleccionar el directorio.
     */
    public JFileChooser getArchivos() {
        return Archivos;
    }

    /**
     * Obtiene el menú de idiomas.
     * @return El componente JMenu para la selección de idioma.
     */
    public JMenu getMenuIdiomas() {
        return menuIdiomas;
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
     * Muestra un cuadro de diálogo con un mensaje informativo.
     * @param mensaje El texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}