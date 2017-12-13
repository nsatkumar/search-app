/*
 * @author nsatkumar
 * 
 * @version 1.0
 * 
 * @date 12 Dec, 2017
 * 
 */
package com.wipro.search.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The Class SearchService.
 */
@Service("searchService")
public class SearchService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

	/** The Constant INDEX_DIR. */
	public static final String INDEX_DIR = "indexdir";

	/** The dir. */
	private Directory dir;

	/** The reader. */
	private IndexReader reader;

	/** The searcher. */
	private IndexSearcher searcher;

	/**
	 * Gets the dir.
	 *
	 * @return the dir
	 */
	public Directory getDir() {
		return dir;
	}

	/**
	 * Sets the dir.
	 *
	 * @param dir
	 *            the new dir
	 */
	public void setDir(Directory dir) {
		this.dir = dir;
	}

	/**
	 * Gets the reader.
	 *
	 * @return the reader
	 */
	public IndexReader getReader() {
		return reader;
	}

	/**
	 * Sets the reader.
	 *
	 * @param reader
	 *            the new reader
	 */
	public void setReader(IndexReader reader) {
		this.reader = reader;
	}

	/**
	 * Gets the searcher.
	 *
	 * @return the searcher
	 */
	public IndexSearcher getSearcher() {
		return searcher;
	}

	/**
	 * Sets the searcher.
	 *
	 * @param searcher
	 *            the new searcher
	 */
	public void setSearcher(IndexSearcher searcher) {
		this.searcher = searcher;
	}

	/**
	 * Search.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return the search results
	 * @throws EmptySearchException
	 *             the empty search exception
	 * @throws ResultsNotFoundException
	 *             the results not found exception
	 * @throws Exception
	 *             the exception
	 */
	public SearchResults search(String searchQuery) throws EmptySearchException, ResultsNotFoundException, Exception {
		SearchResults results;
		try {
			logger.debug("searchQuery" + searchQuery);
			if (searchQuery == null || searchQuery.trim().isEmpty()) {
				throw new EmptySearchException("Search Query is not available");
			}
			StringTokenizer strToken = new StringTokenizer(searchQuery, " ");
			StringBuffer sbuf = new StringBuffer();
			while (strToken.hasMoreTokens()) {
				sbuf.append("+").append(strToken.nextToken()).append(" ");
			}
			searchQuery = sbuf.toString();
			logger.debug("After processing searchQuery" + searchQuery);
			String indexDir = ConfigUtil.getPropertyValue(INDEX_DIR);
			IndexSearcher indexSearcher = createSearcher(indexDir);
			results = searchIndex(indexSearcher, searchQuery);
		} catch (EmptySearchException e) {
			logger.error("search EmptySearchException ", e);
			throw e;
		} catch (ResultsNotFoundException e) {
			logger.error("search ResultsNotFoundException ", e);
			throw e;
		} catch (Exception e) {
			logger.error("search Exception ", e);
			throw e;
		}

		return results;
	}

	/**
	 * Creates the searcher.
	 *
	 * @param indexDir
	 *            the index dir
	 * @return the index searcher
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private IndexSearcher createSearcher(String indexDir) throws IOException {
		if (dir == null) {
			dir = FSDirectory.open(Paths.get(indexDir));
		}
		if (reader == null) {
			reader = DirectoryReader.open(dir);
		}
		if (searcher == null) {
			searcher = new IndexSearcher(reader);
		}
		return searcher;
	}

	/**
	 * Search index.
	 *
	 * @param indexSearcher
	 *            the index searcher
	 * @param queryStr
	 *            the query str
	 * @return the search results
	 * @throws Exception
	 *             the exception
	 */
	private SearchResults searchIndex(IndexSearcher indexSearcher, String queryStr) throws Exception {
		SearchResults results;
		try {
			results = new SearchResults();
			List<String> fileList = new ArrayList<String>();
			QueryParser qp = new QueryParser("contents", new StandardAnalyzer());
			String escapedQueryString = QueryParser.escape(queryStr);
			Query idQuery = qp.parse(escapedQueryString);
			TopDocs topDocs = indexSearcher.search(idQuery, 10);
			if (topDocs != null) {

				ScoreDoc[] hits = topDocs.scoreDocs;
				if (hits == null || hits.length == 0) {
					throw new ResultsNotFoundException("No Search Results");
				} else {
					results.setResultsCount(hits.length);
				}

				for (ScoreDoc sd : hits) {
					int docId = sd.doc;
					Document d = indexSearcher.doc(docId);
					logger.debug(d.get("filename"));
					fileList.add(d.get("filename"));
				}
				results.setFileList(fileList);
				logger.debug("Found " + hits.length);
			}

		} catch (ResultsNotFoundException e) {
			logger.error("searchIndex ResultsNotFoundException " + e);
			throw e;
		} catch (Exception e) {
			logger.error("searchIndex Exception " + e);
			e.printStackTrace();
			throw new Exception("Internal System Error");
		}
		return results;
	}

}
