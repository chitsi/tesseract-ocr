/**
 * 
 */
package util;

/**
 * @author Sanjaya
 *
 */
public class StringUtil {
	public String concat(String... strings) {
		String returnValue = null;
		if (strings.length > 0) {
			final StringBuilder sb = new StringBuilder("");
			for (String string : strings) {
				sb.append(string);
			}
			returnValue = sb.toString();
		}
		return returnValue;
	}
}
