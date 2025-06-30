package ec.edu.ups.util;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
public class Sonido {
    private Clip clip;

    public void cargarSonido(String ruta){
        try {
            URL url = getClass().getResource(ruta);
            if (url == null) {
                System.out.println("No se encontró el archivo de sonido");
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reproducir(){
        if(clip != null){
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void detener(){
        if(clip != null && clip.isRunning()){
            clip.stop();
        }
    }
}
