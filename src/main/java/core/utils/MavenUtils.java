package core.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

@Slf4j
public class MavenUtils {

    public static String version;
    private static String propFileName;

    public static void setVersion() {
        version = System.getProperty("version");
        if (version == null) {
            propFileName = "prod.properties";
        } else if (version.equals("dev")) {
            propFileName = "dev.properties";
        } else if (version.equals("prod")) {
            propFileName = "prod.properties";
        }
    }

    public static String getEmail() {
        setVersion();
        return getParameter("email", propFileName);
    }

    public static String getPass() {
        setVersion();
        return getParameter("pass", propFileName);
    }

    public static String getDriverName() {
        setVersion();
        return getParameter("driver", propFileName);
    }

    private static String getParameter(String propName, String envName) {
        String system;
            system = getProperties(envName).getProperty(propName);
            try {
                System.setProperty(propName, system);
            } catch (NullPointerException e) {
                log.error(e.getMessage());
            }
        return system;
    }

    private static Properties getProperties(String envName) {
        Properties properties = new Properties();
        String file;
        try {
            file = MavenUtils.class.getClassLoader().getResource(envName).getFile();
            properties.load(new FileInputStream(new File(file)));
        } catch (NullPointerException ex) {
            log.error(ex.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Cannot find properties file: env.properties", e);
        }
        return properties;
    }
}


//    private static String getParameter(String propName, String envName) {
//        String system = System.getProperty(propName);
//        if (system == null || system.isEmpty()) {
//            system = getProperties(envName).getProperty(propName);
//            try {
//                System.setProperty(propName, system);
//            } catch (NullPointerException e) {
//                log.error(e.getMessage());
//            }
//        }
//        return system;
//    }