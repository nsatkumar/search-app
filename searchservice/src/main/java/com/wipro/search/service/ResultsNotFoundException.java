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
 * The Class ResultsNotFoundException.
 */
public class ResultsNotFoundException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6749179769121131403L;

	/** The error message. */
	private String errorMessage;

	/**
	 * Instantiates a new results not found exception.
	 *
	 * @param errorMessage
	 *            the error message
	 */
	public ResultsNotFoundException(String errorMessage) {
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
