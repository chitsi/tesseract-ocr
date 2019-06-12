/**
 * 
 */
package file;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Sanjaya
 *
 */
public class FileUtil {

	public static File[] readNonDirectories(String inputFolder) {
		return new File(inputFolder).listFiles(new FilesFileFilter());
	}

	/**
	 * Reads all files in the said directory, reorders them by the last modified
	 * date ascending order.
	 * 
	 * @param inputFolder
	 * @return
	 */
	public static File[] readNonDirectoriesByDate(String inputFolder) {
		final File[] listFiles = new File(inputFolder).listFiles(new FilesFileFilter());
		Map<File, File> map = new TreeMap<File, File>(new FileDateComparator());
		for (File file : listFiles) {
			map.put(file, file);
		}
		int index = 0;
		for (Entry<File, File> entry : map.entrySet()) {
			listFiles[index++] = entry.getValue();
		}
		return listFiles;
	}

}
