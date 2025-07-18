package ec.edu.ups.vista;

import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.Respuesta;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Vista (JFrame) para la recuperación de contraseña mediante preguntas de seguridad.
 * <p>
 * Esta clase presenta al usuario las preguntas de seguridad que ha registrado previamente.
 * El usuario debe ingresar las respuestas correctas para poder proceder con la
 * recuperación de su cuenta. La vista se construye dinámicamente según la cantidad
 * y el texto de las preguntas del usuario.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class PreguntasModificarView extends JFrame {
    /**
     * Etiqueta principal que muestra el título de la ventana.
     */
    private JLabel lblTitulo;
    /**
     * Panel con barras de desplazamiento que contiene el panel de preguntas.
     */
    private JScrollPane scrollPane;
    /**
     * Botón para que el usuario envíe sus respuestas para verificación.
     */
    private JButton btnVerificar;
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel panelPrincipal;
    /**
     * Panel que contiene dinámicamente las etiquetas de las preguntas y los campos para las respuestas.
     */
    private JPanel containerPanel;

    /**
     * Mapa que almacena una referencia a las etiquetas (JLabel) de cada pregunta,
     * para poder actualizar su texto si cambia el idioma.
     */
    private final Map<Pregunta, JLabel> etiquetasPregunta;
    /**
     * Mapa que almacena una referencia a los campos de texto (JTextField) donde el usuario
     * ingresa sus respuestas.
     */
    private final Map<Pregunta, JTextField> camposDeRespuesta;

    /**
     * Manejador para la internacionalización de los mensajes de la aplicación.
     */
    private final MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor de la vista PreguntasModificarView.
     * <p>
     * Inicializa el JFrame, configura los componentes, carga los iconos,
     * inicializa los mapas para gestionar los componentes dinámicos y actualiza
     * los textos según el idioma.
     *
     * @param mensajes El manejador de internacionalización para obtener los textos.
     */
    public PreguntasModificarView(MensajeInternacionalizacionHandler mensajes) {
        setContentPane(panelPrincipal);
        this.mensajes = mensajes;
        this.etiquetasPregunta = new HashMap<>();
        this.camposDeRespuesta = new HashMap<>();

        URL urlVerificar = getClass().getResource("/check.png");

        containerPanel = new JPanel();

        btnVerificar.setIcon(new ImageIcon(urlVerificar));

        setSize(600, 400);

        actualizarTextos();
    }

    /**
     * Muestra dinámicamente las preguntas de seguridad de un usuario en la interfaz.
     * <p>
     * Limpia el panel contenedor y lo reconstruye con un JLabel y un JTextField
     * por cada pregunta de seguridad asociada al usuario.
     *
     * @param respuestasDelUsuario La lista de {@link Respuesta} del usuario, que contiene las preguntas a mostrar.
     */
    public void mostrarPreguntasDelUsuario(List<Respuesta> respuestasDelUsuario) {
        containerPanel.removeAll();
        etiquetasPregunta.clear();
        camposDeRespuesta.clear();

        if (respuestasDelUsuario == null || respuestasDelUsuario.isEmpty()) {
            containerPanel.revalidate();
            containerPanel.repaint();
            return;
        }

        containerPanel.setLayout(new GridLayout(respuestasDelUsuario.size(), 2, 10, 10));
        for (Respuesta respuesta : respuestasDelUsuario) {
            Pregunta pregunta = respuesta.getPregunta();
            JLabel etiqueta = new JLabel(pregunta.getTexto());
            JTextField campoTexto = new JTextField();

            // Guardar referencias a los componentes creados
            etiquetasPregunta.put(pregunta, etiqueta);
            camposDeRespuesta.put(pregunta, campoTexto);

            containerPanel.add(etiqueta);
            containerPanel.add(campoTexto);
        }

        scrollPane.setViewportView(containerPanel);
        containerPanel.revalidate();
        containerPanel.repaint();
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario al idioma actual.
     * <p>
     * Cambia el título de la ventana, las etiquetas estáticas y el texto del botón.
     * También recorre las etiquetas de las preguntas generadas dinámicamente y
     * actualiza su texto según el idioma configurado.
     */
    public void actualizarTextos() {
        // Actualizar textos estáticos
        setTitle(mensajes.get("pregunta.recuperar.titulo"));
        lblTitulo.setText(mensajes.get("pregunta.recuperar.titulo"));
        btnVerificar.setText(mensajes.get("global.boton.verificar"));

        // Actualizar las etiquetas de las preguntas dinámicas
        for (Map.Entry<Pregunta, JLabel> entry : etiquetasPregunta.entrySet()) {
            Pregunta pregunta = entry.getKey();
            JLabel etiqueta = entry.getValue();
            String nuevoTexto = mensajes.get("pregunta.seguridad." + pregunta.getId());
            etiqueta.setText(nuevoTexto);
            pregunta.setTexto(nuevoTexto);
        }
    }


    /**
     * Recopila las respuestas ingresadas por el usuario en los campos de texto.
     *
     * @return Un mapa que asocia cada {@link Pregunta} con la respuesta (String) ingresada por el usuario.
     */
    public Map<Pregunta, String> getRespuestasIngresadas() {
        Map<Pregunta, String> respuestas = new HashMap<>();
        for (Map.Entry<Pregunta, JTextField> entry : camposDeRespuesta.entrySet()) {
            respuestas.put(entry.getKey(), entry.getValue().getText());
        }
        return respuestas;
    }

    /**
     * Obtiene el botón de verificar.
     *
     * @return El componente JButton para verificar las respuestas.
     */
    public JButton getBtnVerificar() {
        return btnVerificar;
    }

    /**
     * Limpia todos los campos de texto de las respuestas.
     */
    public void limpiarCampos() {
        for (JTextField campo : camposDeRespuesta.values()) {
            campo.setText("");
        }
    }
}