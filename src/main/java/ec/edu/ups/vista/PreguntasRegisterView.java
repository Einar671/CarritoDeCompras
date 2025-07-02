package ec.edu.ups.vista;

import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PreguntasRegisterView extends JFrame {
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JScrollPane scrollPane;
    private JButton btnGuardar;

    private final List<JLabel> etiquetasDePregunta;
    private final List<JTextField> camposDeRespuesta;

    private final MensajeInternacionalizacionHandler mensajes;

    public PreguntasRegisterView(MensajeInternacionalizacionHandler mensajes) {
        this.mensajes = mensajes;

        this.etiquetasDePregunta = new ArrayList<>();
        this.camposDeRespuesta = new ArrayList<>();

        setContentPane(panelPrincipal);
        setSize(600, 500);

        URL urlGuardar = getClass().getResource("/check.png");
        btnGuardar.setIcon(new ImageIcon(urlGuardar));


        generarCamposDePreguntas();
        actualizarTextos();
    }


    private void generarCamposDePreguntas() {
        JPanel containerPanel = new JPanel();


        containerPanel.setLayout(new GridLayout(10, 2, 10, 5));
        for (int i = 0; i < 10; i++) {
            JLabel etiqueta = new JLabel();
            JTextField campoTexto = new JTextField();
            etiqueta.setText(mensajes.get("pregunta.seguridad." + (i + 1)));
            etiquetasDePregunta.add(etiqueta);
            camposDeRespuesta.add(campoTexto);

            containerPanel.add(etiqueta);
            containerPanel.add(campoTexto);
        }

        scrollPane.setViewportView(containerPanel);
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("login.boton.reg"));
        lblTitulo.setText(mensajes.get("pregunta.titulo"));
        btnGuardar.setText(mensajes.get("global.boton.guardar"));
    }


    public void mostrarPreguntas(List<Pregunta> preguntas) {
        for (int i = 0; i < preguntas.size(); i++) {
            if (i < etiquetasDePregunta.size()) {
                etiquetasDePregunta.get(i).setText(preguntas.get(i).getTexto());
            }
        }
    }


    public List<String> getRespuestas() {
        return camposDeRespuesta.stream()
                .map(JTextField::getText)
                .collect(Collectors.toList());
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }



}