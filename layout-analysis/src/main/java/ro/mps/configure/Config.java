package ro.mps.configure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Alexandru Burghelea
 * Date: 12/15/12
 * Time: 9:49 AM
 */
public enum Config {

    ENVIRONMENT;

    private final String CONFIG_FILE = "config.properties";
    private final Map<String, String> propeties;
    private final RandomAccessFile bundle;

    private Config() {
        bundle = getConfigBundle();
        propeties = getProperties();
    }

    /**
     * Reads from the bundle all the properties that are found in the config file.
     * Properties must be in the following format: <strong>property_name</strong><em>=</em><strong>property_value</strong>
     *
     * @return Map cu proprietatile
     */
    private Map<String, String> getProperties() {
        Map<String, String> properties = new HashMap<String, String>();
        String line;
        try {
            while ((line = bundle.readLine()) != null) {
                boolean isComment = line.trim().length() < 1 && line.trim().startsWith("#");
                if (!isComment) {
                    String[] entry = line.split("=");
                    if (entry.length == 2) {
                        properties.put(entry[0], entry[1]);
                    }
                }
            }
            return properties;
        } catch (IOException e) {
            throw new ConfigNotPossibleException("Fisierul de configurare nu poate fi citit", e);
        }
    }

    /**
     * Reads and checks the existence of the config file.
     *
     * @return RandomAccessFile of the config
     */
    private RandomAccessFile getConfigBundle() {
        try {
            File configFile = new File(CONFIG_FILE);
            return new RandomAccessFile(configFile, "r");
        } catch (FileNotFoundException e) {
            throw new ConfigNotPossibleException("Fisierul de configurare nu a fost gasit", e);
        }
    }

    /**
     * Gets the size of the properties bundle
     *
     * @return
     */
    public int size() {
        return propeties.size();
    }

    /**
     * Gets a specific property
     *
     * @param key property name
     * @return property value
     */
    public String get(String key) {
        return propeties.get(key);
    }

    public String getExecsFolder() {
        return get("execs");
    }

    public String getSchemaFolder() {
        return get("xsd");
    }

    public String getOutputFolder() {
        return get("output");
    }
}
