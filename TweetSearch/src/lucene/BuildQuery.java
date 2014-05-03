package lucene;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;

import common.QueryBean;

public class BuildQuery {
	


	public Query buildLuceneQuery(QueryBean queryBean, StandardAnalyzer analyzer)
	{
				
		    Query query = null;
		    
		    System.out.println("QueryBean : "+queryBean);
		    	
		    BooleanQuery allBooleanQuery = new BooleanQuery();
		    if(queryBean.getAllWords() != null){
			    String[] allTerms = queryBean.getAllWords().split(" ");
			    for (String terms : allTerms){
			    	allBooleanQuery.add(new BooleanClause((
			    			 new TermQuery(new Term("text", terms))),Occur.MUST));
		        }    
		    }
		    
		    if(queryBean.getWithoutWord() != null){
			    String[] notTerms = queryBean.getWithoutWord().split(" ");
			    for (String terms : notTerms){
			    	allBooleanQuery.add(new BooleanClause((
			    			 new TermQuery(new Term("text", terms))),Occur.MUST_NOT));
		        }    
		    }
		    
		    PhraseQuery approxPhraseQuery = new PhraseQuery();
		    if(queryBean.getApproxPhrase() != null){
		    	String[] allTerms = queryBean.getApproxPhrase().split(" ");
		    	for (String terms : allTerms){
		    		approxPhraseQuery.add(new Term("text", terms));
		        }
		    	approxPhraseQuery.setSlop(5);
		    }
		    
		    PhraseQuery exactPhraseQuery = new PhraseQuery();
		    if(queryBean.getExactPhrase() != null){
		    	String[] exactTerms = queryBean.getExactPhrase().split(" ");
		    	for (String terms : exactTerms){
		    		exactPhraseQuery.add(new Term("text", terms));
		        }
		    	exactPhraseQuery.setSlop(0);
		    }
		    //System.out.println(exactPhraseQuery.toString());
		    
		    	    
		    BooleanQuery mainQuery = new BooleanQuery();
		    if (queryBean.getAllWords() != null)
		    	mainQuery.add(allBooleanQuery, Occur.MUST);
		    if (queryBean.getApproxPhrase() != null)
		    	mainQuery.add(approxPhraseQuery, Occur.MUST);
		    if (queryBean.getExactPhrase() != null)
		    	mainQuery.add(exactPhraseQuery, Occur.MUST);
		    
		    
		    if (queryBean.getScreenName() != null)
		    	mainQuery.add(new BooleanClause((
	    			 new TermQuery(new Term("screenName", queryBean.getScreenName()))),Occur.MUST));
		    
		    if (queryBean.getUserName() != null)
		    	mainQuery.add(new BooleanClause((
	    			 new TermQuery(new Term("userName", queryBean.getUserName()))),Occur.MUST));
		    
		    if (queryBean.getUserLocation() != null)
		    	mainQuery.add(new BooleanClause((
	    			 new TermQuery(new Term("userLocation", queryBean.getUserLocation()))),Occur.MUST));
		    
		    if (queryBean.getHashtag() != null){
		    	
		    	mainQuery.add(new BooleanClause((
	    			 new TermQuery(new Term("hashtag", queryBean.getHashtag()))),Occur.MUST));
		    }
		    
		    System.out.println("Build Query : Query = "+mainQuery.toString());
		    System.out.println("");
		    		    
			try {
				 query = new QueryParser(Version.LUCENE_46, "text", analyzer).parse(mainQuery.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    return query;
	}
}
