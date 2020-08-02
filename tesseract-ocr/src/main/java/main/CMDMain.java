/**
 * 
 */
package main;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;

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

	public static void main(String[] args) throws FileNotFoundException, TesseractException, IOException {
		main1(args);
//		convertJPGtoMovie("C:\\Users\\Sanjaya\\Documents\\siva\\tesseract\\tesseract-ocr\\TextToImage");
	}

	public static void main1(final String[] args) throws FileNotFoundException, TesseractException, IOException {
//		final String imagesFolder = su.concat(".", File.separator, "workspace", File.separator, "images");
//		final String imagesFolder = su.concat("C:\\Users\\Sanjaya\\Pictures\\Greenshot");
		final String imagesFolder = pm.get(null, "imagesFolder");
		// Check and download training data if not already present.
		downloadTrainingData();
		// Read list of files.
		final File[] files = FileUtil.readNonDirectoriesByDate(imagesFolder);
		// perform OCR on all files.
		ocr(files);
	}

	public static void convertJPGtoMovie(String vidPath) {
		OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(vidPath+"\\1.mp4", 640, 720);
		try {
			recorder.setFrameRate(1);
			recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
			recorder.setVideoBitrate(9000);
			recorder.setFormat("mp4");
			recorder.setVideoQuality(0); // maximum quality
			recorder.start();
			BufferedImage img;
			IplImage origImg;
			for (int i = 1; i < 167; i++) {
//				img = ImageIO.read(new File(vidPath + "-" + i));
//				origImg = IplImage.createFrom(img);
				recorder.record(grabberConverter.convert(cvLoadImage(vidPath + "Mukka-" + i + ".png")));
			}
			recorder.stop();
		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
			e.printStackTrace();
		}
	}

	private static void downloadTrainingData() throws IOException {
		String tesseractDir = pm.get(null, "tesseractDir");
		String ocrLanguage = pm.get(null, "ocrLanguage");
		String trainedData = su.concat(ocrLanguage, ".traineddata");
		String remoteLocation = su.concat(pm.get(null, "tesserractTrainedDataDownloadLocation"), "/", trainedData);
		String fileToBeChecked = su.concat(tesseractDir, File.separator, trainedData);
		if (!new File(fileToBeChecked).exists()) {
			// Trained data not found, download it now.
			System.out.println(su.concat("Training data not found for [", fileToBeChecked + "], downloading from [",
					remoteLocation, "]"));
			URL url = new URL(remoteLocation);
			URLConnection urlConn = url.openConnection();
			byte[] buffer = new byte[4096]; // declare 4KB buffer
			int len;

			try (FileOutputStream fos = new FileOutputStream(fileToBeChecked);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					InputStream inputStream = urlConn.getInputStream();) {
				while ((len = inputStream.read(buffer)) > 0) {
					bos.write(buffer, 0, len);
				}
				bos.flush();
			}
			System.out.println("Download complete!");
		}
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
				su.concat(".", File.separator, "workspace", File.separator, "output", File.separator, "output.txt"),true);) {
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
			fos.flush();
		}
	}
}
