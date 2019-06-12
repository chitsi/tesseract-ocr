/**
 * 
 */
package file;

import java.io.File;
import java.io.FileFilter;

/**
 * Intended to be used as a filter to get list of files excluding directories.
 * 
 * @author Sanjaya
 *
 */
public class FilesFileFilter implements FileFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File pathname) {
		return pathname != null && pathname.exists() && !pathname.isDirectory();
	}

}
