/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 */
package com.wipro.search.facade.searchfacade;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wipro.search.facade.ErrorCodes;
import com.wipro.search.facade.SearchFacade;
import com.wipro.search.facade.config.AppTestConfig;
import com.wipro.search.facade.dto.SearchError;
import com.wipro.search.facade.dto.SearchResultsDTO;
import com.wipro.search.service.EmptySearchException;
import com.wipro.search.service.ResultsNotFoundException;
import com.wipro.search.service.SearchResults;
import com.wipro.search.service.SearchService;

/**
 * The Class SearchFacadeTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppTestConfig.class })
public class SearchFacadeTest {

	/** The search service. */
	@Mock
	private SearchService searchService;

	/** The search facade. */
	@InjectMocks
	@Autowired
	private SearchFacade searchFacade;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

	}

	/**
	 * Test facade search success.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testFacadeSearchSuccess() throws Exception {

		SearchResults searchResults = new SearchResults();
		searchResults.setResultsCount(2);
		searchResults.setFileList(Arrays.asList("C:\\sathish\\search\\data\\dummy2.txt",
				"C:\\sathish\\search\\data\\child\\childdummy.txt"));
		when(searchService.search("Debet")).thenReturn(searchResults);
		SearchResultsDTO searchResultsDTO = searchFacade.search("Debet");
		Assert.assertNotNull(searchResultsDTO);

		Assert.assertEquals(2, searchResultsDTO.getResultsCount());
		List<String> fileList = searchResultsDTO.getFileList();
		Assert.assertNotNull(fileList);
		Assert.assertEquals(2, fileList.size());
		Assert.assertNull(searchResultsDTO.getErrorList());
		verify(searchService, times(1)).search("Debet");
		verifyNoMoreInteractions(searchService);

	}

	/**
	 * Test facade search no results.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testFacadeSearchNoResults() throws Exception {

		ResultsNotFoundException resultsNotFoundException = new ResultsNotFoundException("No Search Results");
		when(searchService.search("abc")).thenThrow(resultsNotFoundException);
		SearchResultsDTO searchResultsDTO = searchFacade.search("abc");
		Assert.assertNotNull(searchResultsDTO);

		Assert.assertEquals(0, searchResultsDTO.getResultsCount());
		List<String> fileList = searchResultsDTO.getFileList();
		Assert.assertNull(fileList);
		List<SearchError> errorList = searchResultsDTO.getErrorList();
		Assert.assertNotNull(errorList);
		Assert.assertEquals(1, errorList.size());
		Assert.assertEquals(ErrorCodes.ERR_NO_RESULTS_CODE, errorList.get(0).getErrorCode());
		verify(searchService, times(1)).search("abc");
		verifyNoMoreInteractions(searchService);

	}

	/**
	 * Test facade search bad request.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testFacadeSearchBadRequest() throws Exception {

		EmptySearchException emptySearchException = new EmptySearchException("Search Query is not available");
		when(searchService.search("")).thenThrow(emptySearchException);
		SearchResultsDTO searchResultsDTO = searchFacade.search("");
		Assert.assertNotNull(searchResultsDTO);

		Assert.assertEquals(0, searchResultsDTO.getResultsCount());
		List<String> fileList = searchResultsDTO.getFileList();
		Assert.assertNull(fileList);
		List<SearchError> errorList = searchResultsDTO.getErrorList();
		Assert.assertNotNull(errorList);
		Assert.assertEquals(1, errorList.size());
		Assert.assertEquals(ErrorCodes.ERR_EMPTY_SEARCH_QUERY_CODE, errorList.get(0).getErrorCode());
		verify(searchService, times(1)).search("");
		verifyNoMoreInteractions(searchService);

	}

	/**
	 * Test facade search internal error.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testFacadeSearchInternalError() throws Exception {

		Exception internalServerException = new Exception("Internal System Error");
		when(searchService.search("")).thenThrow(internalServerException);
		SearchResultsDTO searchResultsDTO = searchFacade.search("");
		Assert.assertNotNull(searchResultsDTO);

		Assert.assertEquals(0, searchResultsDTO.getResultsCount());
		List<String> fileList = searchResultsDTO.getFileList();
		Assert.assertNull(fileList);
		List<SearchError> errorList = searchResultsDTO.getErrorList();
		Assert.assertNotNull(errorList);
		Assert.assertEquals(1, errorList.size());
		Assert.assertEquals(ErrorCodes.ERR_SYSTEM_CODE, errorList.get(0).getErrorCode());
		verify(searchService, times(1)).search("");
		verifyNoMoreInteractions(searchService);

	}

};