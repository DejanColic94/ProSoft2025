/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Dejan Colic
 */
public class DatabaseUtil {

    private Properties properties;
    private static DatabaseUtil instance;

    private DatabaseUtil() throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream("dbconfig.properties");
        properties = new Properties();
        properties.load(fis);
    }

    public static DatabaseUtil getInstance() throws IOException {
        if (instance == null) {
            instance = new DatabaseUtil();
        }
        return instance;
    }

    public String getURL() {
        return properties.getProperty("url");
    }

    public String getUser() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }

}
