package ec.edu.ups.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

/**
 * Gestiona la carga y recuperación de mensajes internacionalizados (i18n)
 * utilizando archivos de propiedades (ResourceBundle).
 * <p>
 * Esta clase centraliza el acceso a las traducciones, permitiendo a la aplicación
 * cambiar de idioma dinámicamente. Carga los mensajes desde un archivo base
 * llamado "mensajes" (ej. mensajes_es_EC.properties, mensajes_en_US.properties).
 * <p>
 * <b>Sugerencia de Diseño:</b> Para una aplicación, es común implementar esta clase
 * como un Singleton para asegurar una única instancia y un punto de acceso global
 * a los mensajes.
 *
 * @author Einar Kaalhus
 * @version 1.2
 */
public final class MensajeInternacionalizacionHandler {

    /**
     * El ResourceBundle que contiene los mensajes para el locale actual.
     */
    private ResourceBundle bundle;
    /**
     * El Locale que representa la configuración regional (idioma y país) actual.
     */
    private Locale locale;

    /**
     * Construye un nuevo manejador de internacionalización para un idioma y país específicos.
     *
     * @param lenguaje El código de idioma ISO 639-1 (ej. "es" para español, "en" para inglés).
     * @param pais     El código de país ISO 3166-1 alfa-2 (ej. "EC" para Ecuador, "US" para Estados Unidos).
     */
    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        // Llama al método setLenguaje para evitar la duplicación de código.
        setLenguaje(lenguaje, pais);
    }

    /**
     * Obtiene el mensaje traducido asociado a una clave específica.
     *
     * @param key La clave del mensaje a recuperar del archivo de propiedades.
     * @return El mensaje traducido como una cadena de texto. Si la clave no se encuentra,
     *         devuelve la clave rodeada de '???' para facilitar la depuración.
     */
    public String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            // En caso de que la clave no exista, se devuelve un valor predeterminado
            // para facilitar la depuración en la interfaz de usuario sin romper la aplicación.
            return "???" + key + "???";
        }
    }

    /**
     * Cambia el idioma y país del manejador, recargando el ResourceBundle correspondiente.
     * Este método permite cambiar el idioma de la aplicación en tiempo de ejecución.
     *
     * @param lenguaje El nuevo código de idioma (ej. "es", "en").
     * @param pais     El nuevo código de país (ej. "EC", "US").
     */
    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        // El primer argumento es el "base name" de los archivos de propiedades.
        // Por ejemplo: mensajes.properties, mensajes_es.properties, mensajes_en_US.properties
        this.bundle = ResourceBundle.getBundle("mensajes", this.locale);
    }

    /**
     * Obtiene el Locale actualmente configurado en el manejador.
     *
     * @return el objeto {@link Locale} actual.
     */
    public Locale getLocale() {
        return locale;
    }
}