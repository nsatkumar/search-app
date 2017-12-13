/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 */
package com.wipro.search.web.response;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class SearchResponse.
 */
@Component
public class SearchResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5682836676057561216L;

	/** The count. */
	private int count;

	/** The file list. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> fileList;

	/** The message. */
	private String message;

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count
	 *            the new count
	 */
	public void setCount(int count) {
		this.count = count;
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

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
