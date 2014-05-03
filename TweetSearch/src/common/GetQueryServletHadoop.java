package common;

import hadoop.QueryResultsHadoop;
import hadoop.QueryResultsHadoop_temp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lucene.IndexTweetsLucene;

/**
 * Servlet implementation class GetQueryServlet
 */

public class GetQueryServletHadoop extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GetQueryServletHadoop() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("In Hadoop");
		
		QueryBean query = new QueryBean();
		Map<Double, String> resultsHadoop = new TreeMap<Double, String>(Collections.reverseOrder());
		
		
		if (request.getParameter("allwords") != "")
			query.setAllWords(request.getParameter("allwords"));
		
		
		if (request.getParameter("withoutwords") != "")
			query.setWithoutWord(request.getParameter("withoutwords"));
		
		
		if (request.getParameter("hashtags") != "")
			query.setHashtag(request.getParameter("hashtags"));
		
		resultsHadoop = new QueryResultsHadoop_temp().getQueryResponse(query);
		for (Map.Entry<Double, String> entry : resultsHadoop.entrySet())
	    {
	        System.out.println(entry.getKey() + "\t" + entry.getValue());
	    }

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<b>HADOOP</b>");
		writer.println("<html><b>Below is the response to your query!</b></html>");
		writer.println("Found " + resultsHadoop.size() + " hits.<br><br>");
		int i = 0;
		for (Map.Entry<Double, String> entry : resultsHadoop.entrySet()){
			writer.println("<br>" + (i+1)+ ". &nbsp; " + entry.getKey() + "&nbsp;&nbsp;&nbsp;&nbsp;" + entry.getValue());
			i++;
		}
		writer.println("<br><br><a href=\"Main.jsp\">Back to Home Page</a>");
		
		writer.flush();
		
	    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
