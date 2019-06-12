/**
 * 
 */
package file;

import java.io.File;

/**
 * @author Sanjaya
 *
 */
public class FileUtil {

	public static File[] readNonDirectories(String inputFolder) {
		return new File(inputFolder).listFiles(new FilesFileFilter());
	}

}
