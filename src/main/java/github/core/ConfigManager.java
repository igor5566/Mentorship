package github.core;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class ConfigManager {

    public static String email;
    public static String pass;
    public static String driverName;
    public static int timeWait;
    public static int timesWait;
    public static String userID;
    private static String propFileName;

    static {
        setVersion();
        setTimeWait();
        setTimesWait();
        setDriverName();
        setEmail();
        setPass();
        setUserID();
    }

    private static void setVersion() {
        String version = System.getProperty("version");
        try {
            if (version.equals("dev")) {
                log.info("Running tests on dev environment.");
                propFileName = "dev.properties";
            } else if (version.equals("prod")) {
                log.info("Running tests on prod environment.");
                propFileName = "prod.properties";
            } else {
                log.info("Unable to set version. Prod version has been chosen by default.");
                propFileName = "prod.properties";
            }
        } catch (NullPointerException ex) {
            log.info("You didn't set any version. Prod version has been chosen by default.");
            propFileName = "prod.properties";
        }
    }

    private static void setEmail() {
         email = getParameter("email", propFileName);
    }

    private static void setPass() {
        pass = getParameter("pass", propFileName);
    }

    private static void setUserID() {
        userID = getParameter("userID", propFileName);
    }

    private static void setDriverName() {
        driverName = getParameter("driver", propFileName);
    }

    private static void setTimeWait() {
        try {
            timeWait = Integer.parseInt(getParameter("time", propFileName));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private static void setTimesWait() {
        try {
            timesWait = Integer.parseInt(getParameter("times", propFileName));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }


    public static String getParameter(String propName, String envName) {
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
            file = Objects.requireNonNull(ConfigManager.class.getClassLoader().getResource(envName)).getFile();
            properties.load(new FileInputStream(new File(file)));
        } catch (NullPointerException ex) {
            log.error(ex.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Cannot find properties file ", e);
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