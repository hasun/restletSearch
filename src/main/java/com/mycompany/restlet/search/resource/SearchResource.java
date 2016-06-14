package com.mycompany.restlet.search.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.mycompany.restlet.search.model.SearchResultVo;

public class SearchResource extends ServerResource {

  @SuppressWarnings("unused")
@Get
  public String getResource() throws IOException, ParseException {
	  
	  String index = "C:/Users/SC-007/workspace/restlet/index";
	  String field = "contents";
	  String queries = getQuery().getValues("queries");
	  int repeat = 0;
      boolean raw = false;
      String queryString = getQuery().getValues("query");
      int hitsPerPage = 10;
      
      field = getQuery().getValues("field");
      hitsPerPage = Integer.parseInt(getQuery().getValues("paging"));
  
      IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
      IndexSearcher searcher = new IndexSearcher(reader);
      Analyzer analyzer = new StandardAnalyzer();

      BufferedReader in = null;
      QueryParser parser = new QueryParser(field, analyzer);
      
      if (queries == null && queryString == null) {
    	  
      }    
      
      Query query = parser.parse(queryString);
      System.out.println("Searching for: " + query.toString(field));
          
      String resultStr = doPagingSearch(in, searcher, query, hitsPerPage);

    	  
      reader.close();
      return resultStr;
  }
  
  
  /**
   * This demonstrates a typical paging search scenario, where the search engine presents 
   * pages of size n to the user. The user can then go to the next page if interested in
   * the next hits.
   * 
   * When the query is executed for the first time, then only enough results are collected
   * to fill 5 result pages. If the user wants to page beyond this limit, then the query
   * is executed another time and all hits are collected.
   * 
   */
	public String  doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query, 
                                     int hitsPerPage) throws IOException {
 
		StringBuilder builder = new StringBuilder();
		SearchResultVo result = new SearchResultVo();
		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;
		
		int numTotalHits = results.totalHits;
		result.setTotalCount(numTotalHits);
		builder.append(numTotalHits + " total matching documents").append("\n");
		
		int start = 0;
		int end = Math.min(numTotalHits, hitsPerPage);
		
		if (end > hits.length) {
			builder.append("Only results 1 - " + hits.length +" of " + numTotalHits + " total matching documents collected.");
			hits = searcher.search(query, numTotalHits).scoreDocs;
		}
		end = Math.min(hits.length, start + hitsPerPage);
  
		for (int i = start; i < end; i++) {
			builder.append("doc="+hits[i].doc+" score="+hits[i].score).append("\n");
			Document doc = searcher.doc(hits[i].doc);
			String path = doc.get("path");
			
			if (path != null) {
				builder.append((i+1) + ". " + path).append("\n");
				String title = doc.get("title");
				if (title != null) {
					builder.append("   Title: " + doc.get("title")).append("\n");
				}
				
				String contents = doc.get("contents");
				if (contents != null) {
					builder.append("   Contents: " + doc.get("contents")).append("\n");
				}
			}
		}
		end = Math.min(numTotalHits, start + hitsPerPage);
		return builder.toString();
	}
}
