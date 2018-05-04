package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class PropertyReader {
	public PropertyReader(String fileName) {
		this.fileName = fileName;
	}

	private String fileName;
	String path = getFilePath();

	public String readApplicationFile(String key) {
		String value = "";
		try {
			Properties prop = new Properties();
			File f = new File(
					System.getProperty("user.dir") + "\\src\\test\\java\\com\\config\\application.properties");
			if (f.exists()) {
				prop.load(new FileInputStream(f));
				value = prop.getProperty(key);
			}
		} catch (Exception e) {
			System.out.println("Failed to read from application.properties file.");
		}
		return value;
	}

	public String readApplicationData(String key) throws Exception {
		String value = "";
		try {
			Properties prop = new Properties();
			File f = new File(path + "//src//test//resources//testdata//" + fileName + ".properties");
			if (f.exists()) {
				prop.load(new FileInputStream(f));
				value = prop.getProperty(key);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Failed to read from application.properties file.");
			throw new FileNotFoundException("File not found " + fileName);
		}
		return value;
	}

	public static String getFilePath() {
		String filepath = "";
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		filepath = absolutePathOfFirstFile.replaceAll("\\\\+", "/");
		return filepath;
	}
}