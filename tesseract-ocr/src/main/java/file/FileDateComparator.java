/**
 * 
 */
package file;

import java.io.File;
import java.util.Comparator;

/**
 * @author Sanjaya
 *
 */
public class FileDateComparator implements Comparator<File> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(File o1, File o2) {
		if (o1.lastModified() < o2.lastModified()) {
			return -1;
		} else if (o1.lastModified() > o2.lastModified()) {
			return 1;
		} else {
			return 0;
		}
	}
}
