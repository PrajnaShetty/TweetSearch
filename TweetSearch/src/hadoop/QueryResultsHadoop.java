package hadoop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.lucene.index.IndexWriter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import common.QueryBean;

public class QueryResultsHadoop {
	
//	public static void main (String args[]) throws IOException{
//		QueryBean query = null;
//		Map<String, String> temp = null;
//		QueryResultsHadoop results = new QueryResultsHadoop();
//		temp = results.getQueryResponse(query);
//	}
	
	
	
	public Map<Double, String> getQueryResponse(QueryBean queryBean) throws IOException{
				
	    String line, tweetLine, tweetId, tfIdf = null;
	    Long id = null;
	    Map<Double, String> newMap = new TreeMap<Double, String>(Collections.reverseOrder());
	    Map<Double, String> results = new TreeMap<Double, String>(Collections.reverseOrder());
	    
	    System.out.println("Query HAdoop ="+queryBean.getAllWords().toString());
	    
	    JSONParser parser = new JSONParser(); 
	    
	    String split[] =  queryBean.getAllWords().split("\\s+");
	    
	    for (String queryTerm : split)
	    {
	    	System.out.println("QueryTerm = "+queryTerm);
	    	String filePath = "/Users/prajnashetty/output1024/outputFile_"+queryTerm.charAt(0)+"-r-00000";
	    	
	    	BufferedReader br = new BufferedReader(new FileReader(filePath));
			    while ((line = br.readLine()) != null)
			    {
			    	String keyAndValue[] = line.split("\t");
			    	//System.out.println(keyAndValue[0] + "=" + keyAndValue[1]);
			    	if (keyAndValue[0].contains(queryTerm.toLowerCase()) )
			    	{
			    		
			    		//System.out.println("Line = "+keyAndValue[0]);
			   	    	try {
			   	    		
					    			JSONObject jsonObject = (JSONObject)parser.parse(keyAndValue[1]);
					    			JSONObject value = (JSONObject)jsonObject.get("value");
					    			tweetId = (String)value.get("tweetId");
					    			tfIdf = (String)value.get("tf-idf");
					    			Double tfIdfInteger =  Double.parseDouble(tfIdf);
					    			newMap.put(tfIdfInteger, tweetId);
					    			
					    				
					    	}
			   	    	    catch (org.json.simple.parser.ParseException e)
					    	{
					    		e.printStackTrace();
					        
					        }
			    	}
			    	 	   
			    }
			    br.close();
	    }
	    
	    for (Map.Entry<Double, String> entry : newMap.entrySet())
	    {
	        //System.out.println("New Map = "+entry.getKey() + "\t" + entry.getValue());
	    }
	    
	    //Retrieving tweet text from crawler files
	    for (Map.Entry<Double, String> entry : newMap.entrySet())
	    {
		    Long tweetIdLong = Long.parseLong(entry.getValue());
		    Long hash = (Long) (tweetIdLong%100);
			String tweetTextFile = "/Users/prajnashetty/crawler1024_new/fname"+hash+".txt";
			
			System.out.println("Hi Filename = "+tweetTextFile);
			
			BufferedReader br1 = new BufferedReader(new FileReader(tweetTextFile));
		    while ((line = br1.readLine()) != null)
		    {
		    	
		   	    	try {
				    			JSONObject jsonObjectTweet = (JSONObject)parser.parse(line);
				    			id = (Long)jsonObjectTweet.get("id");
				    			if (id == tweetIdLong){
				    				String tweetText = (String)jsonObjectTweet.get("text");
				    				results.put(entry.getKey(), tweetText);
				    			}
				    	}
		   	    	    catch (org.json.simple.parser.ParseException e)
				    	{
				    		e.printStackTrace();
				        
				        }
		    	
		    	 	   
		    }
		    br1.close();	
	    }
	    for (Map.Entry<Double, String> entry : results.entrySet())
	    {
	        System.out.println(entry.getKey() + "\t" + entry.getValue());
	    }
	    
		return results;
	}

}