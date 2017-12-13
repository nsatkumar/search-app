/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 */
package com.wipro.search.web.request;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * The Class SearchRequest.
 */
@Component
public class SearchRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7193062862841064380L;

	/** The query. */
	private String query;

	/**
	 * Gets the query.
	 *
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Sets the query.
	 *
	 * @param query
	 *            the new query
	 */
	public void setQuery(String query) {
		this.query = query;
	}

}
