package ec.edu.ups.vista;

import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Vista (JFrame) para el registro de respuestas a las preguntas de seguridad.
 * <p>
 * Esta clase se presenta al usuario durante el proceso de registro para que
 * ingrese las respuestas a un conjunto predefinido de preguntas de seguridad.
 * La interfaz se construye dinámicamente para mostrar cada pregunta con un
 * campo de texto correspondiente para la respuesta.
 * <p>
 * También incluye un menú para cambiar el idioma de la interfaz.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class PreguntasRegisterView extends JFrame {
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;
    /**
     * Botón para guardar las respuestas ingresadas por el usuario.
     */
    private JButton btnGuardar;
    /**
     * Panel que contiene dinámicamente las etiquetas de las preguntas y los campos para las respuestas.
     */
    private JPanel containerPanel;
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
    private final MensajeInternacionalizacionHandler mensajes;
    /**
     * La lista de preguntas de seguridad que se muestran actualmente en la vista.
     */
    private final List<Pregunta> preguntasActuales;
    /**
     * Mapa que almacena una referencia a los campos de texto (JTextField) donde el usuario
     * ingresa sus respuestas, asociando cada pregunta con su campo.
     */
    private final Map<Pregunta, JTextField> camposDeRespuesta;
    /**
     * Mapa que almacena una referencia a las etiquetas (JLabel) de cada pregunta,
     * para poder actualizar su texto si cambia el idioma.
     */
    private final Map<Pregunta, JLabel> etiquetasPregunta;

    /**
     * Constructor de la vista PreguntasRegisterView.
     * <p>
     * Inicializa el JFrame, configura los componentes, carga el icono del botón,
     * establece el menú de idiomas y actualiza los textos según el idioma por defecto.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public PreguntasRegisterView(MensajeInternacionalizacionHandler mensajes) {
        this.mensajes = mensajes;
        this.preguntasActuales = new ArrayList<>();
        this.camposDeRespuesta = new HashMap<>();
        this.etiquetasPregunta = new HashMap<>();
        menubar = new JMenuBar();
        menuIdiomas = new JMenu();
        menuItemEspañol = new JMenuItem();
        menuItemIngles = new JMenuItem();
        menuItemNoruego = new JMenuItem();

        // Esta línea es CRÍTICA y asume que panelPrincipal es el nombre del
        // componente raíz en tu archivo .form
        setContentPane(panelPrincipal);

        URL urlGuardar = getClass().getResource("/check.png");
        btnGuardar.setIcon(new ImageIcon(urlGuardar));

        // Configuración del menú (asumiendo que los componentes existen en el .form)
        menubar.add(menuIdiomas);
        menuIdiomas.add(menuItemIngles);
        menuIdiomas.add(menuItemEspañol);
        menuIdiomas.add(menuItemNoruego);
        setJMenuBar(menubar);

        // Configuración final de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        actualizarTextos();
    }

    /**
     * Muestra dinámicamente las preguntas de seguridad en la interfaz.
     * <p>
     * Limpia el panel contenedor y lo reconstruye con un JLabel y un JTextField
     * por cada {@link Pregunta} en la lista proporcionada. Guarda referencias
     * a los componentes creados para futuras actualizaciones.
     *
     * @param preguntas La lista de preguntas de seguridad a mostrar.
     */
    public void mostrarPreguntas(List<Pregunta> preguntas) {
        // 1. Limpiar estado y componentes previos
        containerPanel.removeAll();
        preguntasActuales.clear();
        etiquetasPregunta.clear();
        camposDeRespuesta.clear();

        this.preguntasActuales.addAll(preguntas);

        // 2. Construir la nueva UI dinámicamente
        containerPanel.setLayout(new GridLayout(preguntas.size(), 2, 10, 10));
        for (Pregunta pregunta : this.preguntasActuales) {
            JLabel lblPregunta = new JLabel(pregunta.getTexto());
            JTextField txtRespuesta = new JTextField(20);

            containerPanel.add(lblPregunta);
            containerPanel.add(txtRespuesta);

            // 3. Guardar referencias a los componentes para poder actualizarlos después
            etiquetasPregunta.put(pregunta, lblPregunta);
            camposDeRespuesta.put(pregunta, txtRespuesta);
        }

        // 4. Refrescar el panel para que los cambios sean visibles
        containerPanel.revalidate();
        containerPanel.repaint();
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas estáticas, los textos de los botones,
     * los menús y las etiquetas de las preguntas generadas dinámicamente.
     */
    public void actualizarTextos() {
        // Actualizar textos estáticos
        setTitle(mensajes.get("pregunta.titulo"));
        lblTitulo.setText(mensajes.get("pregunta.titulo"));
        btnGuardar.setText(mensajes.get("global.boton.guardar"));
        menuIdiomas.setText(mensajes.get("menu.idiomas"));
        menuItemEspañol.setText(mensajes.get("menu.idioma.es"));
        menuItemIngles.setText(mensajes.get("menu.idioma.en"));
        menuItemNoruego.setText(mensajes.get("menu.idioma.nw"));

        // Actualizar textos dinámicos (las etiquetas de las preguntas)
        for (Pregunta pregunta : preguntasActuales) {
            JLabel etiqueta = etiquetasPregunta.get(pregunta);
            if (etiqueta != null) {
                String nuevoTexto = mensajes.get("pregunta.seguridad." + pregunta.getId());
                etiqueta.setText(nuevoTexto);
                pregunta.setTexto(nuevoTexto);
            }
        }
    }

    /**
     * Recopila las respuestas ingresadas por el usuario en los campos de texto.
     *
     * @return Un mapa que asocia cada {@link Pregunta} con la respuesta (String) ingresada por el usuario.
     *         Las respuestas son recortadas para eliminar espacios en blanco al inicio y al final.
     */
    public Map<Pregunta, String> getRespuestasIngresadas() {
        Map<Pregunta, String> respuestas = new HashMap<>();
        for (Map.Entry<Pregunta, JTextField> entry : camposDeRespuesta.entrySet()) {
            respuestas.put(entry.getKey(), entry.getValue().getText().trim());
        }
        return respuestas;
    }

    // --- Getters para que el controlador se conecte ---

    /**
     * Obtiene el botón de guardar.
     * @return El componente JButton para guardar.
     */
    public JButton getBtnGuardar() { return btnGuardar; }
    /**
     * Obtiene la opción de menú para el idioma español.
     * @return El componente JMenuItem para español.
     */
    public JMenuItem getMenuItemEspañol() { return menuItemEspañol; }
    /**
     * Obtiene la opción de menú para el idioma inglés.
     * @return El componente JMenuItem para inglés.
     */
    public JMenuItem getMenuItemIngles() { return menuItemIngles; }
    /**
     * Obtiene la opción de menú para el idioma noruego.
     * @return El componente JMenuItem para noruego.
     */
    public JMenuItem getMenuItemNoruego() { return menuItemNoruego; }

    /**
     * Limpia todos los campos de texto de las respuestas.
     */
    public void limpiarCampos() {
        for (JTextField campo : camposDeRespuesta.values()) {
            campo.setText("");
        }
    }
}