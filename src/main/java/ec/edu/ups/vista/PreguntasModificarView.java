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

public class PreguntasModificarView extends JFrame {
    private JLabel lblTitulo;
    private JScrollPane scrollPane;
    private JButton btnVerificar;
    private JPanel panelPrincipal;
    private JPanel containerPanel;

    private final Map<Pregunta, JLabel> etiquetasPregunta;
    private final Map<Pregunta, JTextField> camposDeRespuesta;

    private final MensajeInternacionalizacionHandler mensajes;

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


    public Map<Pregunta, String> getRespuestasIngresadas() {
        Map<Pregunta, String> respuestas = new HashMap<>();
        for (Map.Entry<Pregunta, JTextField> entry : camposDeRespuesta.entrySet()) {
            respuestas.put(entry.getKey(), entry.getValue().getText());
        }
        return respuestas;
    }

    public JButton getBtnVerificar() {
        return btnVerificar;
    }

    public void limpiarCampos() {
        for (JTextField campo : camposDeRespuesta.values()) {
            campo.setText("");
        }
    }
}