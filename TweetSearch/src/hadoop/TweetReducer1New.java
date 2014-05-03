package hadoop;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.json.simple.JSONObject;
 


/**
 * @author PrajnaShetty
 */
public class TweetReducer1New extends Reducer<Text, Text, Text, Text> {
	
	private static final DecimalFormat DF = new DecimalFormat("###.########");
	
	private Text keyValue = new Text();
	private Text tfidfValue = new Text();
	
	private MultipleOutputs mos;
	public void setup(Context context) 
	{
	     mos = new MultipleOutputs(context);
	}
	

public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
	
	 //System.out.println("In TweetREDUCER");
		int totalNumberOfTweets = 1000;
		int totalNumberOfTweetsContainingKey = 0;
		Map<String, String> tempFrequencies = new HashMap<String, String>();
		List<String> tweetList = new ArrayList<String>();
		
		for (Text s : values){
	        String[] tweetIdAndFrequencies = s.toString().split("=");
	        totalNumberOfTweetsContainingKey++;
	      //  System.out.println("Values Length= " + tweetIdAndFrequencies.length);
	      //  System.out.println("0-"+tweetIdAndFrequencies[0]+ " 1-"+ tweetIdAndFrequencies[1]);
	        if (tweetIdAndFrequencies.length == 3){
	        	tempFrequencies.put(tweetIdAndFrequencies[0], tweetIdAndFrequencies[1]);
	        	tweetList.add(tweetIdAndFrequencies[2]);
	        }
	    }
	int i = 0;
	for (String tweetId : tempFrequencies.keySet()) {
         String[] wordFrequencyAndTotalWords = tempFrequencies.get(tweetId).split("/");

         //term frequency = (number of times term appears in document)/(total number of terms in doc)
         double tf = Double.valueOf(Double.valueOf(wordFrequencyAndTotalWords[0])
                 / Double.valueOf(wordFrequencyAndTotalWords[1]));

         //inverse document frequency = (total number of docs)/(number of docs the term appears)
         double idf = (double) totalNumberOfTweets / (double) totalNumberOfTweetsContainingKey;

         //given that log(10) = 0, just consider the term frequency in documents
         double tfIdf = totalNumberOfTweets == totalNumberOfTweetsContainingKey ?
                 tf : tf * Math.log10(idf);
         
//        System.out.println("Key = "+new Text(key) + "  Value = "+ 
//        		new Text("[" + tweetId + " , " + DF.format(tfIdf) + "]"));
        
//         keyValue.set(new Text(key));
//        tfidfValue.set( new Text("[" + tweetId + " , " + DF.format(tfIdf) + "]"));
//        
         keyValue.set(new Text(key));
         
        JSONObject valueJSON = new JSONObject();
        valueJSON.put("tweetId", tweetId);
        valueJSON.put("tf-idf", DF.format(tfIdf));
        valueJSON.put("tweetText", tweetList.get(i++));
        
        JSONObject obj = new JSONObject();
        obj.put("key", keyValue);
        obj.put("value",valueJSON );
    	
    	
        
    	tfidfValue.set(obj.toJSONString());
    	System.out.println("Key = "+keyValue.toString()+" Value = "+tfidfValue.toString());

        //context.write(keyValue, tfidfValue);
    	mos.write(keyValue, tfidfValue, generateFileName(key));
     }
  	

}

	private String generateFileName(Text k)
	{
	  	   return "outputFile" + k.toString().charAt(0);
	}    
}