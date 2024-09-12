/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author 56035
 */
public class ConfigManager {
    
    private static final String FILE = "./config/config.properties";

    private final Properties prop;

    private final String url;

    private ConfigManager() {
        this.prop = new Properties();
        this.url = getClass().getClassLoader().getResource(FILE).getFile();
    }


    public static ConfigManager getInstance() {
        return ConfigManagerHolder.INSTANCE;
    }

    public void load() throws IOException {
    try (InputStream input = new FileInputStream(url)) {
        prop.load(input);
    } catch (IOException ex) {
        throw new IOException("Chargement configuration impossible ",ex);
    }
    }
    
    private static class ConfigManagerHolder {

        private static final ConfigManager INSTANCE = new ConfigManager();
    }

    public String getProperties(String name) {
        return prop.getProperty(name);
    }



}
