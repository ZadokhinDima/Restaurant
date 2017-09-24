package controller.util;

import controller.Config;

import java.util.ResourceBundle;

public class BundleUtil {

    public static String getString(String key){
        ResourceBundle bundle = ResourceBundle.getBundle("constants", Config.getInstance().getLocale());
        return bundle.getString(key);
    }
}
