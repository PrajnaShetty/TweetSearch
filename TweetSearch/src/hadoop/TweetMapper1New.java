package hadoop;
 
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
 
public class TweetMapper1New extends Mapper<LongWritable, Text, Text, Text> {

	  private Text word = new Text();
	  private Text count = new Text();
	  
	  private static final Pattern PUNCTUATIONS = Pattern.compile("[\\Q][(){},.;!|?<>%\\E]");

	  
	  private static Set<String> stopwordsSet;
	  
	  static {
	      stopwordsSet = new HashSet<String>();
	      stopwordsSet.add("I"); stopwordsSet.add("a"); stopwordsSet.add("about");
	      stopwordsSet.add("an"); stopwordsSet.add("are"); stopwordsSet.add("as");
	      stopwordsSet.add("at"); stopwordsSet.add("be"); stopwordsSet.add("by");
	      stopwordsSet.add("com"); stopwordsSet.add("de"); stopwordsSet.add("en");
	      stopwordsSet.add("for"); stopwordsSet.add("from"); stopwordsSet.add("how");
	      stopwordsSet.add("in"); stopwordsSet.add("is"); stopwordsSet.add("it");
	      stopwordsSet.add("la"); stopwordsSet.add("of"); stopwordsSet.add("on");
	      stopwordsSet.add("or"); stopwordsSet.add("that"); stopwordsSet.add("the");
	      stopwordsSet.add("this"); stopwordsSet.add("to"); stopwordsSet.add("was");
	      stopwordsSet.add("what"); stopwordsSet.add("when"); stopwordsSet.add("where"); 
	      stopwordsSet.add("who"); stopwordsSet.add("will"); stopwordsSet.add("with");
	      stopwordsSet.add("and"); stopwordsSet.add("the"); stopwordsSet.add("www");
	      stopwordsSet.add("have"); stopwordsSet.add("my");stopwordsSet.add("your");
	  }

	  @Override
	  protected void map(LongWritable key, Text value, Context context)
	      throws IOException, InterruptedException {
		    
			  String tweetID = null, tweetText = null;
			  JSONParser parser = new JSONParser();
			   try 
			   {
				         JSONObject jsonObj = (JSONObject) parser.parse(value.toString());
						 if ((jsonObj.get("id")) != null && jsonObj.get("text") != null ) 
				    	   {
				    	       String language = (String)jsonObj.get("lang");
				    	       if (language.equalsIgnoreCase("en")) // filtering out other language tweets
				    	       {
				    	    	   tweetID = (String) jsonObj.get("id").toString();
				    	    	   tweetText = (String)jsonObj.get("text");
				    	        }
				            }
				          
				  } catch (ParseException e) {
				       
				  } catch (NumberFormatException e) {
				          e.printStackTrace();
				  }
			   
				  if ((tweetText != null) && (!tweetText.contains("RT")))
				  {
					  //removing punctuation marks
					  tweetText = PUNCTUATIONS.matcher(tweetText).replaceAll("");	  
					  String[] split = tweetText.split("\\s+");		  
					  for(String s : split)
					  {
						  //filtering out stop words
						  if (s != null && !stopwordsSet.contains(s.toLowerCase()) && s.matches("^[a-z#].*"))
						  {
							  //converting all upper case characters to lower case							  
							  int termFrequency = 0;
							  // boost hashtags
							  if (s.contains("#"))  
								  termFrequency +=5;
							  s = s.toLowerCase().replaceAll("#", "");
							  for(int i = 0; i < split.length ; i++)
							  {
								  //calculating frequency of words in tweets & then removing duplicate words
								 if( s.equalsIgnoreCase(split[i]))
								 {
									 termFrequency++;
								 	 split[i]=null;
								 }
							  }
							  
							  if (s.length() > 0)
							  {
								  word.set(s);
								  count.set(tweetID + "=" +termFrequency+ "/" +split.length);
							      context.write(word, count);
							  }
						  }
					      
					  }
				  
				  }
		  }
}
