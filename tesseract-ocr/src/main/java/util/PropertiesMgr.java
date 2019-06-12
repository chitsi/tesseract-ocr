/**
 * 
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Sanjaya
 *
 */
public class PropertiesMgr {
	public static final String DEFAULT_BUNDLE = "input-do-not-del";
	private static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();
	final StringUtil su = new StringUtil();
	private static PropertiesMgr PROPERTIES_MGR = null;

	/**
	 * @throws IOException
	 */
	private PropertiesMgr() throws IOException {
		// TODO enhance this code to read all properties file in properties folder.
		// \input\properties\input-do-not-del.properties is expected for sure.
		final Properties defaultProperties = new Properties();

		String propFile = su.concat(".", File.separator, "input", File.separator, "properties", File.separator,
				"input-do-not-del.properties");
		try (FileInputStream inStream = new FileInputStream(propFile);) {
			defaultProperties.load(inStream);
		}
		propertiesMap.put("input-do-not-del", defaultProperties);
		PROPERTIES_MGR = this;
	}

	/**
	 * @return
	 * @throws IOException - If default properties file is not found.
	 */
	public static PropertiesMgr getInstance() throws IOException {
		if (PROPERTIES_MGR == null) {
			PROPERTIES_MGR = new PropertiesMgr();
		}
		return PROPERTIES_MGR;
	}

	public String get(final String bundle, final String key) {
		final Properties properties = propertiesMap.get(bundle);
		return properties != null ? properties.getProperty(key) : null;
	}
}
