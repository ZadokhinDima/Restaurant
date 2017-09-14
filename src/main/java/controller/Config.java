package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Config {
	
	private String factoryClass;
	
	private Config() {
		load();
	}
	
	private void load() {
		try(InputStream inputStream = this.getClass().getResourceAsStream("/config.properties")){
			Properties properties = new Properties();
			properties.load(inputStream);
			factoryClass = properties.getProperty("dao.factory.class");
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static class Holder{
		private static Config INSTANCE = new Config();
	}
	
	public static Config getInstance() {
		return Holder.INSTANCE;
	}
	
	public String getFactoryClass(){
		return factoryClass;
	}

	public static void main(String[] args) {
		System.out.println("Ok");
	}
	
}
