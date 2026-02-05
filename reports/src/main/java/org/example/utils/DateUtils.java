package org.example.utils;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Utilidad para manejo de fechas
 */
public class DateUtils {

    private static final Locale LOCALE_ES = new Locale("es", "ES");

    /**
     * Obtiene la fecha actual
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * Obtiene el día del mes actual
     */
    public static String getCurrentDay() {
        return String.valueOf(getCurrentDate().getDayOfMonth());
    }

    /**
     * Obtiene el mes actual en español
     */
    public static String getCurrentMonth() {
        return getCurrentDate().getMonth()
                .getDisplayName(TextStyle.FULL, LOCALE_ES);
    }

    /**
     * Obtiene el año actual
     */
    public static String getCurrentYear() {
        return String.valueOf(getCurrentDate().getYear());
    }

    /**
     * Formatea una fecha completa en español
     */
    public static String formatFullDate(String day, String month, String year) {
        return day + " de " + month + " de " + year;
    }

    /**
     * Formatea la fecha actual completa en español
     */
    public static String formatCurrentFullDate() {
        LocalDate now = getCurrentDate();
        return formatFullDate(
                String.valueOf(now.getDayOfMonth()),
                now.getMonth().getDisplayName(TextStyle.FULL, LOCALE_ES),
                String.valueOf(now.getYear())
        );
    }

    private DateUtils() {
        // Clase de utilidad, no instanciar
    }
}