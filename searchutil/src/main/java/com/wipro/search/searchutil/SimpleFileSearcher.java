package com.wipro.search.searchutil;

import java.io.IOException;
import java.nio.file.Paths;

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

public class SimpleFileSearcher {

    public static void main(String[] args) throws Exception {        
        
       SimpleFileSearcher searcher = new SimpleFileSearcher();
        IndexSearcher indexSearcher = searcher.createSearcher();        
        QueryParser qp = new QueryParser("contents", new StandardAnalyzer());
        Query idQuery = qp.parse("Debet");
        TopDocs topDocs = indexSearcher.search(idQuery, 10);
        if(topDocs != null)
        {
        	int hits = topDocs.scoreDocs.length;
        	System.out.println("hits"+hits);
	        for (ScoreDoc sd : topDocs.scoreDocs)
	        {
	            Document d = indexSearcher.doc(sd.doc);
	            System.out.println(d.get("filename"));

	        }
        }
    }
    
    private IndexSearcher createSearcher() throws IOException {
    	String indexDir ="C:/sathish/search/index/";
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }
	

}
