/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 */
package com.wipro.search.facade.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class SearchResultsDTO.
 */
public class SearchResultsDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2548150861585773136L;

	/** The results count. */
	private int resultsCount;

	/** The file list. */
	private List<String> fileList;

	/** The error list. */
	private List<SearchError> errorList;

	/**
	 * Gets the error list.
	 *
	 * @return the error list
	 */
	public List<SearchError> getErrorList() {
		return errorList;
	}

	/**
	 * Sets the error list.
	 *
	 * @param errorList
	 *            the new error list
	 */
	public void setErrorList(List<SearchError> errorList) {
		this.errorList = errorList;
	}

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
