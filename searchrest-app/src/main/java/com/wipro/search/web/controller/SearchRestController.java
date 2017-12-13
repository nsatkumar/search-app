/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 */
package com.wipro.search.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.search.facade.ErrorCodes;
import com.wipro.search.facade.SearchFacade;
import com.wipro.search.facade.dto.SearchError;
import com.wipro.search.facade.dto.SearchResultsDTO;
import com.wipro.search.web.request.SearchRequest;
import com.wipro.search.web.response.SearchResponse;

/**
 * The Class SearchRestController.
 */
@RestController
public class SearchRestController {

	/** The Constant SUCCESS_MSG_CODE. */
	public static final String SUCCESS_MSG_CODE = "suc_001";

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SearchRestController.class);

	/** The search facade. */
	@Autowired
	SearchFacade searchFacade;

	/** The search response. */
	@Autowired
	SearchResponse searchResponse;

	/** The message source. */
	@Autowired
	MessageSource messageSource;

	/**
	 * Search.
	 *
	 * @param searchRequest
	 *            the search request
	 * @return the response entity
	 */
	@RequestMapping(value = "/search/", method = RequestMethod.POST)
	public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest searchRequest) {// REST Endpoint.
		SearchResultsDTO searchResultsDTO = null;
		try {
			logger.debug("inside controller");
			searchResultsDTO = searchFacade.search(searchRequest.getQuery());
			if (searchResultsDTO != null) {
				List<SearchError> searchErrorList = searchResultsDTO.getErrorList();
				List<String> fileList = searchResultsDTO.getFileList();
				searchResponse.setCount(searchResultsDTO.getResultsCount());
				if (fileList != null && fileList.size() > 0) {
					List<String> resultFileList = new ArrayList<String>();
					for (String string : fileList) {
						resultFileList.add(string);
					}
					searchResponse.setFileList(resultFileList);
				}
				if (searchErrorList != null) {
					for (SearchError searchError : searchErrorList) {
						if (searchError != null
								&& searchError.getErrorCode() == ErrorCodes.ERR_EMPTY_SEARCH_QUERY_CODE) {
							searchResponse.setFileList(null);
							searchResponse.setMessage(messageSource.getMessage(ErrorCodes.ERR_EMPTY_SEARCH_QUERY_CODE,
									null, Locale.ENGLISH));
							return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.BAD_REQUEST);
						} else if (searchError != null && searchError.getErrorCode() == ErrorCodes.ERR_SYSTEM_CODE) {
							searchResponse.setFileList(null);
							searchResponse.setMessage(messageSource.getMessage(ErrorCodes.ERR_SYSTEM_CODE, null, null));
							return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.INTERNAL_SERVER_ERROR);
						} else if (searchError != null
								&& searchError.getErrorCode() == ErrorCodes.ERR_NO_RESULTS_CODE) {
							searchResponse.setFileList(null);
							searchResponse
									.setMessage(messageSource.getMessage(ErrorCodes.ERR_NO_RESULTS_CODE, null, null));
							return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.NOT_FOUND);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error message", e);
			searchResponse.setCount(0);
			searchResponse.setFileList(null);
			searchResponse.setMessage(messageSource.getMessage(ErrorCodes.ERR_SYSTEM_CODE, null, null));
			return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		searchResponse.setMessage(messageSource.getMessage(SUCCESS_MSG_CODE, null, null));
		return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.OK);
	}
}
