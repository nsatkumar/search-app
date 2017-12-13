/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 * 
 */
package com.wipro.search.facade;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wipro.search.facade.dto.SearchError;
import com.wipro.search.facade.dto.SearchResultsDTO;
import com.wipro.search.service.EmptySearchException;
import com.wipro.search.service.ResultsNotFoundException;
import com.wipro.search.service.SearchResults;
import com.wipro.search.service.SearchService;

/**
 * The Class SearchFacade.
 * 
 * 
 * This class acts as a business facade to orchestrate the call from the web
 * search rest controller to the Search service.
 * 
 * It allows for loose coupling between the controller and service and abstracts
 * the service interface details
 * 
 */
@Component
public class SearchFacade {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SearchFacade.class);

	/** The search service. */
	@Autowired
	SearchService searchService;

	/**
	 * Search.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return the search results DTO
	 */
	public SearchResultsDTO search(String searchQuery) {
		logger.debug("search() method of SearchFacade invoked");
		SearchResultsDTO resultsDTO = new SearchResultsDTO();
		List<SearchError> errorList = null;
		try {
			SearchResults results = searchService.search(searchQuery);
			resultsDTO.setFileList(results.getFileList());
			resultsDTO.setResultsCount(results.getResultsCount());
		} catch (EmptySearchException emptySearchEx) {
			errorList = new ArrayList<SearchError>();
			SearchError error = new SearchError();
			error.setErrorCode(ErrorCodes.ERR_EMPTY_SEARCH_QUERY_CODE);
			errorList.add(error);
			logger.error("Search String is Empty", emptySearchEx);
		} catch (ResultsNotFoundException resultNotFoundEx) {
			errorList = new ArrayList<SearchError>();
			SearchError error = new SearchError();
			error.setErrorCode(ErrorCodes.ERR_NO_RESULTS_CODE);
			errorList.add(error);
			logger.error("No Results Found", resultNotFoundEx);
		} catch (Exception ex) {
			errorList = new ArrayList<SearchError>();
			SearchError error = new SearchError();
			error.setErrorCode(ErrorCodes.ERR_SYSTEM_CODE);
			errorList.add(error);
			logger.error("Exception is ", ex);
		}
		resultsDTO.setErrorList(errorList);
		return resultsDTO;
	}

}
