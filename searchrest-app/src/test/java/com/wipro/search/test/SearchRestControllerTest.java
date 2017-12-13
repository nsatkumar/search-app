/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 */
package com.wipro.search.test;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.search.facade.ErrorCodes;
import com.wipro.search.facade.SearchFacade;
import com.wipro.search.facade.dto.SearchError;
import com.wipro.search.facade.dto.SearchResultsDTO;
import com.wipro.search.web.config.SearchRestConfig;
import com.wipro.search.web.controller.SearchRestController;
import com.wipro.search.web.request.SearchRequest;

/**
 * The Class SearchRestControllerTest.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SearchRestConfig.class })
public class SearchRestControllerTest {

	/** The mock mvc. */
	private MockMvc mockMvc;

	/** The search facade. */
	@Mock
	private SearchFacade searchFacade;

	/** The search rest controller. */
	@InjectMocks
	@Autowired
	private SearchRestController searchRestController;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(searchRestController).build();
	}

	/**
	 * Test search oneword success.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void test_search_oneword_success() throws Exception {
		SearchResultsDTO searchResultsDTO = new SearchResultsDTO();
		searchResultsDTO.setResultsCount(2);
		searchResultsDTO.setFileList(Arrays.asList("C:\\sathish\\search\\data\\dummy2.txt",
				"C:\\sathish\\search\\data\\child\\childdummy.txt"));
		searchResultsDTO.setErrorList(new ArrayList<SearchError>());
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setQuery("Debet");
		when(searchFacade.search(searchRequest.getQuery())).thenReturn(searchResultsDTO);

		mockMvc.perform(post("/search/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(searchRequest)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.fileList[0]", is("C:\\sathish\\search\\data\\dummy2.txt")))
				.andExpect(jsonPath("$.fileList[1]", is("C:\\sathish\\search\\data\\child\\childdummy.txt")))
				.andExpect(jsonPath("$.count", is(2))).andExpect(jsonPath("$.message", is("Successful!")));

		verify(searchFacade, times(1)).search(searchRequest.getQuery());
		verifyNoMoreInteractions(searchFacade);

	}

	/**
	 * Test search twoword success.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void test_search_twoword_success() throws Exception {
		SearchResultsDTO searchResultsDTO = new SearchResultsDTO();
		searchResultsDTO.setResultsCount(1);
		searchResultsDTO.setFileList(Arrays.asList("C:\\sathish\\search\\data\\child\\childdummy.txt"));
		searchResultsDTO.setErrorList(new ArrayList<SearchError>());
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setQuery("Pro antiopam");
		when(searchFacade.search(searchRequest.getQuery())).thenReturn(searchResultsDTO);

		mockMvc.perform(post("/search/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(searchRequest)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.fileList[0]", is("C:\\sathish\\search\\data\\child\\childdummy.txt")))
				.andExpect(jsonPath("$.count", is(1))).andExpect(jsonPath("$.message", is("Successful!")));
		verify(searchFacade, times(1)).search(searchRequest.getQuery());
		verifyNoMoreInteractions(searchFacade);

	}

	/**
	 * Test search oneword no results.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void test_search_oneword_no_results() throws Exception {
		SearchResultsDTO searchResultsDTO = new SearchResultsDTO();
		searchResultsDTO.setResultsCount(0);
		SearchError searchError = new SearchError();
		searchError.setErrorCode(ErrorCodes.ERR_NO_RESULTS_CODE);
		List<SearchError> errorList = new ArrayList<SearchError>();
		errorList.add(searchError);
		searchResultsDTO.setErrorList(errorList);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setQuery("abc");
		when(searchFacade.search(searchRequest.getQuery())).thenReturn(searchResultsDTO);

		mockMvc.perform(post("/search/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(searchRequest)))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.fileList").doesNotExist()).andExpect(jsonPath("$.count", is(0)))
				.andExpect(jsonPath("$.message", is("Search didn't fetch any results")));
		verify(searchFacade, times(1)).search(searchRequest.getQuery());
		verifyNoMoreInteractions(searchFacade);

	}

	/**
	 * Test search bad request.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void test_search_bad_request() throws Exception {
		SearchResultsDTO searchResultsDTO = new SearchResultsDTO();
		searchResultsDTO.setResultsCount(0);
		SearchError searchError = new SearchError();
		searchError.setErrorCode(ErrorCodes.ERR_EMPTY_SEARCH_QUERY_CODE);
		List<SearchError> errorList = new ArrayList<SearchError>();
		errorList.add(searchError);
		searchResultsDTO.setErrorList(errorList);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setQuery("  ");
		when(searchFacade.search(searchRequest.getQuery())).thenReturn(searchResultsDTO);

		mockMvc.perform(post("/search/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(searchRequest)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.fileList").doesNotExist()).andExpect(jsonPath("$.count", is(0)))
				.andExpect(jsonPath("$.message", is("Invalid Search Query String")));
		verify(searchFacade, times(1)).search(searchRequest.getQuery());
		verifyNoMoreInteractions(searchFacade);

	}

	/**
	 * Test search internal error.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void test_search_internal_error() throws Exception {
		SearchResultsDTO searchResultsDTO = new SearchResultsDTO();
		searchResultsDTO.setResultsCount(0);
		SearchError searchError = new SearchError();
		searchError.setErrorCode(ErrorCodes.ERR_SYSTEM_CODE);
		List<SearchError> errorList = new ArrayList<SearchError>();
		errorList.add(searchError);
		searchResultsDTO.setErrorList(errorList);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setQuery(null);
		when(searchFacade.search(searchRequest.getQuery())).thenReturn(searchResultsDTO);

		mockMvc.perform(post("/search/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(searchRequest)))
				.andExpect(status().isInternalServerError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.fileList").doesNotExist()).andExpect(jsonPath("$.count", is(0)))
				.andExpect(jsonPath("$.message", is("Internal Server Error, Please try again!")));
		verify(searchFacade, times(1)).search(searchRequest.getQuery());
		verifyNoMoreInteractions(searchFacade);

	}

	/**
	 * As json string.
	 *
	 * @param obj
	 *            the obj
	 * @return the string
	 */
	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
