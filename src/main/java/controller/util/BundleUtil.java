package controller.util;


import java.util.Locale;
import java.util.ResourceBundle;

public class BundleUtil {

    public static String getString(String key, Locale locale){
        if(locale == null){
            locale = Locale.getDefault();
        }
        ResourceBundle bundle = ResourceBundle.getBundle("constants", locale);
        return bundle.getString(key);
    }

}
