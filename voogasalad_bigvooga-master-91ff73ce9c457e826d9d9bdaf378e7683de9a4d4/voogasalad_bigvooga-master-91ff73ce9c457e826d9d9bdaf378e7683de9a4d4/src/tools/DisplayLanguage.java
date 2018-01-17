package tools;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * This class mediates the change of display language in the GUI by providing numerous static classes that create binded labels and buttons,
 * along with the internal methods that facilitate the change of language among these GUI objects that display text.
 * 
 * @author Samarth Desai
 *
 */
public final class DisplayLanguage {
    
	public static final Locale SPANISH = new Locale("es");
	
	/** The current selected Locale. */
    private static final ObjectProperty<Locale> locale;
    static {
        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }
    /**
     * Populates and gets the list of the supported Locales.
     * 
     * @return the list of Locale objects that have been accounted for.
     */
    public static List<Locale> getSupportedLocales() {
        return new ArrayList<>(Arrays.asList(Locale.ENGLISH, new Locale("es")));
    }
    /**
     * Gets the default locale. This is the systems default if contained in the supported locales, but is english otherwise.
     *
     * @return the default locale as long as it is supported
     */
    public static Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.ENGLISH;
    }
    
    /**
     * Gets the current locale.
     * 
     * @return The current locale
     */
    public static Locale getLocale() {
        return locale.get();
    }
    
    /**
     * Sets the new locale after a language button is clicked.
     * 
     * @param locale - The locale that is being set
     */
    public static void setLocale(Locale locale) {
        localeProperty().set(locale);
        Locale.setDefault(locale);
    }
    
    /**
     * Allows dynamic changing of the local using the ObjectProperty.
     * 
     * @return the changed locale
     */
    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }
    
    /**
     * Gets the string with the given key from the resource bundle for the current locale and uses it as first argument
     * to MessageFormat.format, passing in the optional args and returning the result.
     *
     * @param key - The message key
     * @param args - Optional arguments for the message
     * @return The localized formatted string
     */
    public static String get(final String key, final Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle("languages/messages_" + getLocale());
        return MessageFormat.format(bundle.getString(key), args);
    }
    
    /**
     * Creates a String binding to a localized String for the given message bundle key.
     *
     * @param key - The language string
     * @return the bound language string
     */
    public static StringBinding createStringBinding(final String key, Object... args) {
        return Bindings.createStringBinding(() -> get(key, args), locale);
    }
    
    /**
     * Creates a String Binding to a localized String that is computed by calling the given func.
     *
     * @param func - The function called on every change
     * @return the bound language string
     */
    public static StringBinding createStringBinding(Callable<String> func) {
        return Bindings.createStringBinding(func, locale);
    }
    
    /**
     * Creates a bound Button for the given ResourceBundle key
     *
     * @param key - ResourceBundle key
     * @param args - optional arguments for the Button text
     * @return the created Button
     */
    public static Button buttonForKey (final String key, final Object... args) {
        Button button = new Button();
        button.textProperty().bind(createStringBinding(key, args));
        return button;
    }
    /**
     * Creates a bound Tooltip for the given ResourceBundle key
     *
     * @param key - the ResourceBundle key
     * @param args - optional arguments for the Tooltip text
     * @return the created Tooltip
     */
    public static Tooltip tooltipForKey(final String key, final Object... args) {
        Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(createStringBinding(key, args));
        return tooltip;
    }
}