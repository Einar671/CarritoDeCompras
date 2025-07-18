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

public class PreguntasRegisterView extends JFrame {
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JButton btnGuardar;
    private JPanel containerPanel;
    private JMenuBar menubar;
    private JMenu menuIdiomas;
    private JMenuItem menuItemEspañol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemNoruego;

    private final MensajeInternacionalizacionHandler mensajes;
    private final List<Pregunta> preguntasActuales;
    private final Map<Pregunta, JTextField> camposDeRespuesta;
    private final Map<Pregunta, JLabel> etiquetasPregunta;

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

    public Map<Pregunta, String> getRespuestasIngresadas() {
        Map<Pregunta, String> respuestas = new HashMap<>();
        for (Map.Entry<Pregunta, JTextField> entry : camposDeRespuesta.entrySet()) {
            respuestas.put(entry.getKey(), entry.getValue().getText().trim());
        }
        return respuestas;
    }

    // --- Getters para que el controlador se conecte ---

    public JButton getBtnGuardar() { return btnGuardar; }
    public JMenuItem getMenuItemEspañol() { return menuItemEspañol; }
    public JMenuItem getMenuItemIngles() { return menuItemIngles; }
    public JMenuItem getMenuItemNoruego() { return menuItemNoruego; }

    public void limpiarCampos() {
        for (JTextField campo : camposDeRespuesta.values()) {
            campo.setText("");
        }
    }
}