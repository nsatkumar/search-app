/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 */
package com.wipro.search.service.searchservice;

import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wipro.search.service.EmptySearchException;
import com.wipro.search.service.ResultsNotFoundException;
import com.wipro.search.service.SearchResults;
import com.wipro.search.service.SearchService;
import com.wipro.search.service.config.AppConfigTest;

/**
 * The Class SearchServiceTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfigTest.class })
public class SearchServiceTest {

	/** The directory. */
	@Mock
	Directory directory;

	/** The searcher. */
	@Mock
	IndexSearcher searcher;

	/** The reader. */
	@Mock
	IndexReader reader;

	/** The search service. */
	@InjectMocks
	@Autowired
	private SearchService searchService;

	/**
	 * Inits the.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
		searchService.setDir(directory);
		searchService.setReader(reader);
		searchService.setSearcher(searcher);

	}

	/**
	 * Test service search success.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testServiceSearchSuccess() throws Exception {
		TopDocs topDocs = new TopDocs(2, new ScoreDoc[] { new ScoreDoc(1, 1.0f), new ScoreDoc(2, 1.0f) }, 10);
		IndexSearcher indexSearcher = searchService.getSearcher();
		when(indexSearcher.search(Matchers.any(Query.class), Matchers.anyInt())).thenReturn(topDocs);
		Document d1 = new Document();
		d1.add(new StringField("id", "1", Field.Store.YES));
		d1.add(new StringField("filename", "C:\\sathish\\search\\data\\child\\childdummy.txt", Field.Store.YES));
		Document d2 = new Document();
		d2.add(new StringField("id", "2", Field.Store.YES));
		d2.add(new StringField("filename", "C:\\sathish\\search\\data\\dummy2.txt", Field.Store.YES));
		when(searchService.getSearcher().doc(1)).thenReturn(d2);
		when(searchService.getSearcher().doc(2)).thenReturn(d1);
		SearchResults searchResults = searchService.search("Debet");
		Assert.assertNotNull(searchResults);
		Assert.assertEquals(2, searchResults.getResultsCount());
		List<String> fileList = searchResults.getFileList();
		Assert.assertNotNull(fileList);
		Assert.assertEquals(2, fileList.size());
		Assert.assertEquals("C:\\sathish\\search\\data\\dummy2.txt", fileList.get(0));
		Assert.assertEquals("C:\\sathish\\search\\data\\child\\childdummy.txt", fileList.get(1));
	}

	/**
	 * Test service search no results.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test(expected = ResultsNotFoundException.class)
	public void testServiceSearchNoResults() throws Exception {
		TopDocs topDocs = new TopDocs(0, null, 10);
		IndexSearcher indexSearcher = searchService.getSearcher();
		when(indexSearcher.search(Matchers.any(Query.class), Matchers.anyInt())).thenReturn(topDocs);
		searchService.search("abc");
	}

	/**
	 * Test service search empty search exception.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test(expected = EmptySearchException.class)
	public void testServiceSearchEmptySearchException() throws Exception {
		searchService.search("");
	}

}
