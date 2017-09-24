package controller;

import com.sun.org.apache.regexp.internal.RE;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;


public class Config {
	
	private String factoryClass;
	private Locale locale;


	private Config() {
		load();
	}
	
	private void load() {
		try(InputStream inputStream = this.getClass().getResourceAsStream("/config.properties")){
			Properties properties = new Properties();
			properties.load(inputStream);
			factoryClass = properties.getProperty("dao.factory.class");
			locale = new Locale(properties.getProperty("locale"));
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static class Holder{
		private static Config INSTANCE = new Config();
	}


    public Locale getLocale(){
	    return locale;
    }

	public static Config getInstance() {
		return Holder.INSTANCE;
	}
	
	public String getFactoryClass(){
		return factoryClass;
	}

	
}
