package de.uni_potsdam.hpi.wfapp2011.proposals.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

public class ZipFileGenerator {

	//TODO duplicated entries
	/**
	 * Creates a zipped file from a given list of files.
	 */
	public File createZipfile(List<File> src, File dest) throws ZipException, IOException {
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dest));
			zipDir(src, zos);
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dest;
	}

	public void zipDir(List<File> source, ZipOutputStream zos) {
		try {
			byte[] readBuffer = new byte[8192];
			int bytesIn = 0;
			// read files from source
			for (File file: source) {
				if (file.isFile()) {
					FileInputStream fis = new FileInputStream(file);
					ZipEntry anEntry = new ZipEntry(file.getName());
					zos.putNextEntry(anEntry);

					// write content to ZipOutputStream
					while ((bytesIn = fis.read(readBuffer)) != -1) {
						zos.write(readBuffer, 0, bytesIn);
					}
					fis.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a zipped file from files in a given source directory.
	 */
	public File createZipfile(File src, File dest) throws ZipException,IOException {
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dest));
			zipDir(src, zos);
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dest;
	}

	public void zipDir(File source, ZipOutputStream zos) {
		try {
			String[] dirList = source.list();
			byte[] readBuffer = new byte[8192];
			int bytesIn = 0;

			for (int i = 0; i < dirList.length; i++) {
				File file = new File(source, dirList[i]);
				// traverse directories recursively
				if (file.isDirectory()) {
					zipDir(file, zos);
					continue;
				}

				FileInputStream fis = new FileInputStream(file);
				ZipEntry anEntry = new ZipEntry(file.getPath());
				zos.putNextEntry(anEntry);

				// write content to ZipOutputStream
				while ((bytesIn = fis.read(readBuffer)) != -1) {
					zos.write(readBuffer, 0, bytesIn);
				}
				fis.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
