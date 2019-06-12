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
import util.StringUtil;

/**
 * @author Sanjaya
 *
 */
public class CMDMain {
	final static StringUtil su = new StringUtil();

	public static void main(String[] args) throws FileNotFoundException, TesseractException, IOException {
		System.setProperty("file.encoding", "UTF-8");
		String inputFolder = su.concat(".", File.separator, "input");
		// Read list of files.
		File[] files = FileUtil.readNonDirectories(inputFolder);
		// perform OCR on all files.
		ocr(files, inputFolder);
	}

	private static void ocr(File[] files, String inputFolder)
			throws TesseractException, FileNotFoundException, IOException {
		Tesseract tesseract = new Tesseract();
		tesseract.setLanguage("tel");
		tesseract.setDatapath("C:\\Users\\Sanjaya\\Documents\\tessdata-tel");
		int count = 0;
		String name;
		try (FileOutputStream fos = new FileOutputStream(su.concat(inputFolder, File.separator, "output.txt"));) {
			for (File file : files) {
				if (!file.isDirectory()) {
					name = file.getName();
					System.out.println(name);
					String text = tesseract.doOCR(file);
					fos.write(("=====" + name + "=====\n" + text).getBytes());
					count++;
					System.out.println("Done with [" + count + "/" + files.length + "]");
				}
			}
		}
	}
}
