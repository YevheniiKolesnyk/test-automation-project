package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private PropertyLoader() {
        throw new IllegalStateException("Utility class");
    }

    public static String loadProperty(String propFile, String name) {
        Properties props = getPropertiesFile(propFile);

        if (props != null) {
            return props.getProperty(name);
        }
        return null;
    }

    private static Properties getPropertiesFile(String fileName) {
        Properties props = new Properties();
        try (InputStream is = PropertyLoader.class.getResourceAsStream(fileName)) {
            if (is == null) {
                return null;
            }
            props.load(is);
            return props;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}