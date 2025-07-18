package ec.edu.ups.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase de utilidad para formatear valores numéricos y fechas en formatos
 * localizados (específicos de una región o idioma).
 * <p>
 * Esta clase no puede ser instanciada ni extendida.
 *
 * @author Einar Kaalhus
 * @version 1.1
 */
public final class FormateadorUtils {

    /**
     * Constructor privado para prevenir la instanciación de esta clase de utilidad.
     */
    private FormateadorUtils() {
        // Esta clase no debe ser instanciada.
    }

    /**
     * Formatea una cantidad numérica como una cadena de texto de moneda, utilizando
     * la configuración regional (país e idioma) especificada.
     *
     * @param cantidad La cantidad de dinero a formatear (ej. 1234.56).
     * @param locale   El {@link Locale} que define el formato de la moneda (ej. Locale.US para $, Locale.GERMANY para €).
     * @return Una cadena de texto que representa la cantidad en el formato de moneda
     *         correspondiente (ej. "$1,234.56").
     */
    public static String formatearMoneda(double cantidad, Locale locale) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(locale);
        return formatoMoneda.format(cantidad);
    }

    /**
     * Formatea un objeto {@link Date} como una cadena de texto de fecha, utilizando
     * un formato de longitud media y la configuración regional especificada.
     *
     * @param fecha  El objeto {@link Date} a formatear.
     * @param locale El {@link Locale} que define el formato de la fecha (ej. "MMM d, yyyy" para US, "d MMM yyyy" para UK).
     * @return Una cadena de texto que representa la fecha en el formato localizado.
     */
    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }
}