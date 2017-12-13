/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 */
package com.wipro.search.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The Class ConfigUtil.
 */
public class ConfigUtil {

	/** The prop. */
	private static Properties prop;

	static {
		InputStream is = null;
		try {
			prop = new Properties();
			is = ConfigUtil.class.getResourceAsStream("searchservice.properties");
			prop.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the property value.
	 *
	 * @param key
	 *            the key
	 * @return the property value
	 */
	public static String getPropertyValue(String key) {
		return prop.getProperty(key);
	}

	/**
	 * The main method.
	 *
	 * @param a
	 *            the arguments
	 */
	public static void main(String a[]) {

		System.out.println("indexdir " + getPropertyValue("indexdir"));
		System.out.println("hits " + getPropertyValue("hits"));

	}

}
