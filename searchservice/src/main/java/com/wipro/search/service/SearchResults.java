/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 */
package com.wipro.search.service;

import java.util.List;

/**
 * The Class SearchResults.
 */
public class SearchResults {

	/** The results count. */
	private int resultsCount;

	/** The file list. */
	private List<String> fileList;

	/**
	 * Gets the results count.
	 *
	 * @return the results count
	 */
	public int getResultsCount() {
		return resultsCount;
	}

	/**
	 * Sets the results count.
	 *
	 * @param resultsCount
	 *            the new results count
	 */
	public void setResultsCount(int resultsCount) {
		this.resultsCount = resultsCount;
	}

	/**
	 * Gets the file list.
	 *
	 * @return the file list
	 */
	public List<String> getFileList() {
		return fileList;
	}

	/**
	 * Sets the file list.
	 *
	 * @param fileList
	 *            the new file list
	 */
	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}

}
