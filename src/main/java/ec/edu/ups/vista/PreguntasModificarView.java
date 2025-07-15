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

    private final Map<Pregunta, JTextField> camposDeRespuesta;
    private List<Respuesta> respuestasActuales;

    private final MensajeInternacionalizacionHandler mensajes;

    public PreguntasModificarView(MensajeInternacionalizacionHandler mensajes) {
        setContentPane(panelPrincipal);

        this.mensajes = mensajes;
        scrollPane.setViewportView(containerPanel);
        this.camposDeRespuesta = new HashMap<>();
        this.respuestasActuales = null;
        URL urlVerificar = getClass().getResource("/check.png");

        containerPanel = new JPanel();

        btnVerificar.setIcon(new ImageIcon(urlVerificar));

        setSize(600, 400);

        actualizarTextos();
    }

    public void mostrarPreguntasDelUsuario(List<Respuesta> respuestasDelUsuario) {
        this.respuestasActuales = respuestasDelUsuario;

        containerPanel.removeAll();
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

            camposDeRespuesta.put(pregunta, campoTexto);

            containerPanel.add(etiqueta);
            containerPanel.add(campoTexto);
        }

        containerPanel.revalidate();
        containerPanel.repaint();
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("pregunta.recuperar.titulo"));
        lblTitulo.setText(mensajes.get("pregunta.recuperar.titulo"));
        btnVerificar.setText(mensajes.get("global.boton.verificar")); // Clave nueva

        if (respuestasActuales != null && !respuestasActuales.isEmpty()) {
            for (Map.Entry<Pregunta, JTextField> entry : camposDeRespuesta.entrySet()) {
                Pregunta pregunta = entry.getKey();
                for (Component comp : containerPanel.getComponents()) {
                    if (comp instanceof JLabel) {
                        JLabel etiqueta = (JLabel) comp;
                        String clave = "pregunta.seguridad." + pregunta.getId();
                        pregunta.setTexto(mensajes.get(clave));
                        etiqueta.setText(pregunta.getTexto());
                    }
                }
            }
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