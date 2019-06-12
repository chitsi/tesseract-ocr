/**
 * 
 */
package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import file.FileUtil;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import util.PropertiesMgr;
import util.StringUtil;

/**
 * @author Sanjaya
 *
 */
public class CMDMain {
	private final static StringUtil su = new StringUtil();
	private static PropertiesMgr pm = null;
	static {
		try {
			pm = PropertiesMgr.getInstance();
			System.setProperty("file.encoding", pm.get(null, "outputFileEncoding"));
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	public static void main(final String[] args) throws FileNotFoundException, TesseractException, IOException {
		final String imagesFolder = su.concat(".", File.separator, "workspace", File.separator, "images");
		// Read list of files.
		final File[] files = FileUtil.readNonDirectoriesByDate(imagesFolder);
		// perform OCR on all files.
		ocr(files);
	}

	private static void ocr(final File[] files) throws TesseractException, FileNotFoundException, IOException {
		final Tesseract tesseract = new Tesseract();
		tesseract.setLanguage(pm.get(null, "ocrLanguage"));
		tesseract.setDatapath(pm.get(null, "tesseractDir"));
		int count = 0;
		File outputDir = new File(su.concat(".", File.separator, "workspace", File.separator, "output"));
		if (!outputDir.exists() || !outputDir.isDirectory()) {
			// create .\workspace\output if not present.
			outputDir.mkdir();
		}
		String name;
		try (FileOutputStream fos = new FileOutputStream(
				su.concat(".", File.separator, "workspace", File.separator, "output", File.separator, "output.txt"));) {
			for (final File file : files) {
				if (!file.isDirectory()) {
					name = file.getName();
					System.out.println(name);
					final String text = tesseract.doOCR(file);
					fos.write(("=====" + name + "=====\n" + text).getBytes());
					count++;
					System.out.println("Done with [" + count + "/" + files.length + "]");
				}
			}
		}
	}
}
