package com.wipro.search.searchutil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

public class SimpleFileIndexer {

    public static void main(String[] args) throws Exception {        
        File dataDir = new File("C:/sathish/search/data/");
        String suffix = "txt";     
        IndexWriter writer = createWriter();
       
      //Let's clean everything first
        writer.deleteAll();
         
        indexDirectory(writer,dataDir,suffix);
        writer.commit();
        int numIndex = writer.maxDoc();
        writer.close();
            
        System.out.println("Numer of total files indexed:  " + numIndex);        
    }
    
    private static IndexWriter createWriter() throws IOException
    {
    	String indexDir = "C:/sathish/search/index/";
        FSDirectory dir = FSDirectory.open(Paths.get(indexDir));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter writer = new IndexWriter(dir, config);
        return writer;
    }
    
  
    private static void indexDirectory(IndexWriter indexWriter, File dataDir, String suffix) throws IOException {
        File[] files = dataDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                indexDirectory(indexWriter, f, suffix);
            }
            else {
                indexFileWithIndexWriter(indexWriter, f, suffix);
            }
        }

    }
    
    private static void indexFileWithIndexWriter(IndexWriter indexWriter, File f, String suffix) throws IOException {
        if (f.isHidden() || f.isDirectory() || !f.canRead() || !f.exists()) {
            return;
        }
 
        if (suffix!=null && !f.getName().endsWith(suffix)) {
            return;
        }
        
        System.out.println("Indexing file:... " + f.getCanonicalPath());
        
        Document doc = new Document();
        doc.add(new TextField("contents", new FileReader(f)));        
        doc.add(new TextField("filename", f.getCanonicalPath(), TextField.Store.YES));
        
        indexWriter.addDocument(doc);
    }
    
}
