package lucene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javabeans.QueryBean;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class IndexTweetsLucene {

	
  public Map<String, String> getQueryResponse(QueryBean queryBean) throws IOException{
	  
	  Map<String, String> results = new HashMap<String, String>();

	  long startTime = System.currentTimeMillis();
    // 0. Specify the analyzer for tokenizing text.
    //    The same analyzer should be used for indexing and searching
    StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);

    // 1. create the index
    Directory index = new RAMDirectory();
    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46, analyzer);

    IndexWriter w = new IndexWriter(index, config);
    BufferedReader br = new BufferedReader(new InputStreamReader
    		(this.getClass().getClassLoader().getResourceAsStream("sample1.txt")));
//    BufferedReader br = new BufferedReader(new FileInputStream(this.getClass().getClassLoader().getResourceAsStream(arg0)("/myfile.txt")));
//    InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("file.txt")
//    BufferedReader br =	new BufferedReader(input);	
    String line = null;
    while ((line = br.readLine()) != null) {
    	 addDoc(w, line);	   
    }
    br.close();
    w.close();

    long endTime = System.currentTimeMillis();
    System.out.println("Total Time taken for index creation:" + (endTime-startTime));
    
    
    Query query = null;
    query = new BuildQuery().buildLuceneQuery(queryBean, analyzer);

    // 3. search
    int hitsPerPage = 10;
    IndexReader reader = DirectoryReader.open(index);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
    searcher.search(query, collector);
    ScoreDoc[] hits = collector.topDocs().scoreDocs;
    
    // 4. display results
    //System.out.println("Found " + hits.length + " hits.");
    for(int i=0;i<hits.length;++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      String resultText = d.get("text");
      //System.out.println((i + 1) + ". " + d.get("id") + "\t" + resultText);
      results.put(d.get("id"), resultText);
      
    }

   
    reader.close();
    return results;
  }
  


  private static void addDoc(IndexWriter w, String tweet) throws IOException {
     // System.out.println("\nTweet - " +tweet);
	  JSONParser parser = new JSONParser();
      String text = null, userName = null, screenName = null,
    		  userLocation = null, language = null;
      Long id = null;
      List<String> hashtagList = new ArrayList<String>();
     
      try {
		JSONObject jsonObject = (JSONObject)parser.parse(tweet);
		text = (String)jsonObject.get("text");
		
		if (text != null)
		{
			text = text.replace('\n', ' ');
		}	
		id = (Long)jsonObject.get("id");
		language = (String)jsonObject.get("lang");
		
		JSONObject user = (JSONObject)jsonObject.get("user");
		if (user != null)
		{
			userName = (String)user.get("name");
			screenName = (String)user.get("screen_name");
			userLocation = (String)user.get("location");
		}
		
		
		JSONObject entities = (JSONObject)jsonObject.get("entities");
		if (entities != null)
		{
			JSONArray hashTagArray = (JSONArray)entities.get("hashtags");
			
			if (hashTagArray != null)
			{
				for (int i = 0 ; i < hashTagArray.size(); i++)
				{
					//System.out.println("hashtags Array = "+hashTagArray.get(i) );
					JSONObject hashtagObject = (JSONObject)hashTagArray.get(i) ;
					hashtagList.add((String)hashtagObject.get("text"));
					System.out.println("HAshtagList - " + hashtagList);
					
				}
			}
		}
		
      } catch (org.json.simple.parser.ParseException e) {
		e.printStackTrace();
     }
      Document doc = new Document();
      if ((language != null) &&language.equalsIgnoreCase("en"))  // filtering out non english tweets
      {
    	  
	      if (text != null && !text.startsWith("RT", 0))  // filtering out retweet feeds and delete tweet feeds
	      {
	    	  for (String ht : hashtagList )
	    	  {
	    		  System.out.println("HT - "+ht);
	    		  TextField hashtag = new TextField("hashtag", ht, Field.Store.YES);
	    		  hashtag.setBoost(2);
	    		  doc.add(hashtag);	    		  
	    	  }
	    	  doc.add(new TextField("text", text, Field.Store.YES));
	    	  doc.add(new LongField("id", id, Field.Store.YES));
	    	  doc.add(new TextField("userName", userName , Field.Store.YES));
	    	  doc.add(new TextField("screenName", screenName , Field.Store.YES));
	    	  doc.add(new TextField("userLocation", userLocation , Field.Store.YES));
	      }
      }
      w.addDocument(doc);
  }



	
}