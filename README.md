# tesseract-ocr
This is a wrapper around the original tesseract OCR project, to add further functionality. This is what this does.
1) Reads all images in a preconfigured folder.
2) Performs OCR on each image (in case training data is not found, system attempts to download training data and then use it. Should the auto downloaded file be corrupt, place manually downloaded training data in the required location on the local system).
3) Dumps OCR of all the images in a file.
