package core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class MavenUtils {
    public static String getEmail() {
        return getParameter("email");
    }

    public static String getPass() {
        return getParameter("pass");
    }

//    public static String getUrl() {
//        return getParameter("url");
//    }

    public static String getDriverName() {
        return getParameter("driver");
    }

    private static String getParameter(String propName) {
        String system = System.getProperty(propName);
        if (system == null || system.isEmpty()) {
            system = getProperties().getProperty(propName);
            try {
                System.setProperty(propName, system);
            } catch (NullPointerException e) {
            }
        }
        return system;
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        String file;
        try {
            file = MavenUtils.class.getClassLoader().getResource("env.properties").getFile();
            properties.load(new FileInputStream(new File(file)));
        } catch (Exception e) {
            throw new RuntimeException("Cannot find properties file: env.properties", e);
        }
        return properties;
    }
}
