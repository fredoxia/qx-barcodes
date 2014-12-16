package com.onlineMIS.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FileCompressionUtil {

	private static final String PATH_SEP = "\\";
	public static final int BUFFER = 2048;

	private FileCompressionUtil() {
	}

	/**
	 * Zip files in path.
	 *
	 * @param zipFileName
	 *            the zip file name
	 * @param filePath
	 *            the file path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void zipFilesInPath(final String zipFileName,
			final String filePath) throws IOException {
		final FileOutputStream dest = new FileOutputStream(zipFileName);
		final ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(dest));
		try {

			byte[] data = new byte[BUFFER];
			final File folder = new File(filePath);
			final List<String> files = Arrays.asList(folder.list());
			for (String file : files) {
				final FileInputStream fi = new FileInputStream(filePath
						+ PATH_SEP + file);
				final BufferedInputStream origin = new BufferedInputStream(fi,
						BUFFER);
				out.putNextEntry(new ZipEntry(file));
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
				fi.close();
			}
		} finally {
			out.close();
			dest.close();
		}
	}
	
	public static void zipWorkbooks(final String zipFilePath,
			final Map<String, HSSFWorkbook> workbookMap) throws Exception {
		final FileOutputStream dest = new FileOutputStream(zipFilePath);
		final ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(dest));
		try {
			Iterator<String> workbookNames = workbookMap.keySet().iterator();
			
			byte[] data = new byte[BUFFER];
			while (workbookNames.hasNext()){
				String workbookName = workbookNames.next();
				HSSFWorkbook wb = workbookMap.get(workbookName);
				
				ByteArrayOutputStream os = new ByteArrayOutputStream();   
				wb.write(os);   

			    byte[] content = os.toByteArray();   
			    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);   
			    
				out.putNextEntry(new ZipEntry(workbookName));
				int count;
				while ((count = byteArrayInputStream.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
			}
		} catch (Exception e){
			loggerLocal.error(e);
			throw e;
		} finally {
			out.close();
			dest.close();
		}
	}

	/**
	 * Zip with checksum. CRC32
	 *
	 * @param zipFileName
	 *            the zip file name
	 * @param folderPath
	 *            the folder path
	 * @return the checksum
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static long zipFilesInPathWithChecksum(final String zipFileName,
			final String folderPath) throws IOException {

		final FileOutputStream dest = new FileOutputStream(zipFileName);
		final CheckedOutputStream checkStream = new CheckedOutputStream(dest,
				new CRC32());
		final ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(checkStream));
		try {
			byte[] data = new byte[BUFFER];
			final File folder = new File(folderPath);
			final List<String> files = Arrays.asList(folder.list());
			for (String file : files) {
				final FileInputStream fi = new FileInputStream(folderPath
						+ PATH_SEP + file);
				final BufferedInputStream origin = new BufferedInputStream(fi,
						BUFFER);
				out.putNextEntry(new ZipEntry(file));
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}

		} finally {
			out.close();
			checkStream.close();
			dest.flush();
			dest.close();
		}

		return checkStream.getChecksum().getValue();
	}

	/**
	 * Unzip files to path.
	 *
	 * @param zipFileName
	 *            the zip file name
	 * @param fileExtractPath
	 *            the file extract path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void unzipFilesToPath(final String zipFileName,
			final String fileExtractPath) throws IOException {

		final FileInputStream fis = new FileInputStream(zipFileName);
		final ZipInputStream zis = new ZipInputStream(new BufferedInputStream(
				fis));
		try {
			ZipEntry entry;

			while ((entry = zis.getNextEntry()) != null) {
				int count;
				byte[] data = new byte[BUFFER];
				final FileOutputStream fos = new FileOutputStream(
						fileExtractPath + PATH_SEP + entry.getName());
				final BufferedOutputStream dest = new BufferedOutputStream(fos,
						BUFFER);
				while ((count = zis.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
			}
		} finally {
			fis.close();
			zis.close();
		}

	}

	/**
	 * Unzip files to path with checksum. CRC32
	 *
	 * @param zipFileName
	 *            the zip file name
	 * @param fileExtractPath
	 *            the file extract path
	 * @param checksum
	 *            the checksum
	 * @return true, if checksum matches;
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static boolean unzipFilesToPathWithChecksum(
			final String zipFileName, final String fileExtractPath,
			final long checksum) throws IOException {

		boolean checksumMatches = false;
		final FileInputStream fis = new FileInputStream(zipFileName);
		final CheckedInputStream checkStream = new CheckedInputStream(fis,
				new CRC32());
		final ZipInputStream zis = new ZipInputStream(new BufferedInputStream(
				checkStream));

		try {

			ZipEntry entry = null;
			while ((entry = zis.getNextEntry()) != null) {
				int count;
				byte[] data = new byte[BUFFER];
				final FileOutputStream fos = new FileOutputStream(
						fileExtractPath + PATH_SEP + entry.getName());
				final BufferedOutputStream dest = new BufferedOutputStream(fos,
						BUFFER);
				while ((count = zis.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
			}

		} finally {
			zis.close();
			fis.close();
			checkStream.close();
		}

		if (checkStream.getChecksum().getValue() == checksum) {
			checksumMatches = true;
		}

		return checksumMatches;
	}

}
