/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 */
package com.wipro.search.service;

/**
 * The Class EmptySearchException.
 */
public class EmptySearchException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4057313501887979999L;

	/** The error message. */
	private String errorMessage;

	/**
	 * Instantiates a new empty search exception.
	 *
	 * @param errorMessage
	 *            the error message
	 */
	public EmptySearchException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 *
	 * @param errorMessage
	 *            the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
