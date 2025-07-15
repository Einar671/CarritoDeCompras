package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;

public class SeleccionarDAO extends JFrame {
    private JPanel principalPane;
    private JCheckBox cbxMemoria;
    private JCheckBox cbxArchivo;
    private JFileChooser Archivos;
    private JLabel titulo;
    private JButton btnAceptar;
    private JMenuBar menubar;
    private JMenu menuIdiomas;
    private JMenuItem menuItemEspañol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemNoruego;

    private MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

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

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public JCheckBox getCbxMemoria() {
        return cbxMemoria;
    }

    public JCheckBox getCbxArchivo() {
        return cbxArchivo;
    }

    public JFileChooser getArchivos() {
        return Archivos;
    }

    public JMenu getMenuIdiomas() {
        return menuIdiomas;
    }

    public JMenuItem getMenuItemEspañol() {
        return menuItemEspañol;
    }

    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    public JMenuItem getMenuItemNoruego() {
        return menuItemNoruego;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}
