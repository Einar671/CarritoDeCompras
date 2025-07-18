package ec.edu.ups.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * Clase de utilidad para cargar y reproducir archivos de sonido simples.
 * <p>
 * Esta clase encapsula la lógica para manejar un objeto {@link Clip}, permitiendo
 * cargar un sonido desde el classpath, reproducirlo y detenerlo. Es ideal para
 * efectos de sonido en interfaces de usuario.
 * <p>
 * <b>Nota sobre gestión de recursos:</b> En una aplicación más compleja, sería
 * recomendable implementar la interfaz {@link java.io.Closeable} o {@link AutoCloseable}
 * para liberar explícitamente los recursos del sistema asociados al {@code Clip}
 * cuando ya no sea necesario (mediante un método {@code close()}).
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class Sonido {

    /**
     * El objeto Clip que contiene los datos del audio cargado y controla su reproducción.
     */
    private Clip clip;

    /**
     * Carga un archivo de sonido desde una ruta especificada en el classpath.
     * <p>
     * El recurso se busca relativo a la ubicación de las clases compiladas.
     * Por ejemplo, si el sonido está en {@code src/main/resources/sounds/click.wav},
     * la ruta a proporcionar debería ser {@code "/sounds/click.wav"}.
     *
     * @param ruta La ruta del recurso al archivo de sonido (ej. "/sounds/error.wav").
     */
    public void cargarSonido(String ruta) {
        try {
            URL url = getClass().getResource(ruta);
            if (url == null) {
                // Es preferible usar System.err para mensajes de error.
                System.err.println("No se encontró el archivo de sonido en la ruta: " + ruta);
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            // En una aplicación real, se recomienda usar un sistema de logging en lugar de printStackTrace.
            e.printStackTrace();
        }

    }

    /**
     * Reproduce el sonido cargado desde el principio.
     * <p>
     * Si no se ha cargado ningún sonido (el clip es nulo), este método no hace nada.
     * Cada vez que se llama, el sonido comienza desde el inicio.
     */
    public void reproducir() {
        if (clip != null) {
            clip.setFramePosition(0); // Reinicia el sonido al principio
            clip.start();
        }
    }

    /**
     * Detiene la reproducción del sonido si está en curso.
     * <p>
     * Si el sonido no se está reproduciendo o no se ha cargado, este método no hace nada.
     */
    public void detener() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}