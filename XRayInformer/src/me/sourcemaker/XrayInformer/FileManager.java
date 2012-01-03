package me.sourcemaker.XrayInformer;

import java.io.File;

import org.bukkit.util.config.Configuration;

@SuppressWarnings("deprecation")
public class FileManager {

	private static String ordner = "plugins/xrayinformer";
	private static File configFile = new File(ordner + File.separator + "config.yml");
	private static Configuration config;
	
	private Configuration loadConfig() {
		try {
			Configuration config = new Configuration(configFile);
			config.load();
			return config;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void createConfig() {
		new File(ordner).mkdir();
		
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				config = loadConfig();
			
				
				config.setProperty("check_world", "world");
				
				config.setProperty("mat_1_id", 14);
				config.setProperty("mat_1_yellow", 10.0);
				config.setProperty("mat_1_red", 15.0);
				
				config.setProperty("mat_2_id", 21);
				config.setProperty("mat_2_yellow", 1.0);
				config.setProperty("mat_2_red", 2.0);
				
				config.setProperty("mat_3_id", 48);
				config.setProperty("mat_3_yellow", 5.0);
				config.setProperty("mat_3_red", 10.0);
				
				config.setProperty("mat_4_id", 56);
				config.setProperty("mat_4_yellow", 1.0);
				config.setProperty("mat_4_red", 2.0);
				
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		config = loadConfig();
	}
	
	public String readString(String key) {
		String value = config.getString(key,"");
		return value;
	}
	
	public double readDouble(String key) {
		double value = config.getDouble(key, 0.0);
		return value;
	}
	
	public int readInt(String key) {
		Integer value = config.getInt(key, 0);
		return value;
	}
	
	
	
	
}