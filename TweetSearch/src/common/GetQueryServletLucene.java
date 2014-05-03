package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lucene.IndexTweetsLucene;

/**
 * Servlet implementation class GetQueryServlet
 */

public class GetQueryServletLucene extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GetQueryServletLucene() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("In Servlet - Lucene");
		
		QueryBean query = new QueryBean();
		Map<String, String> resultsLucene = new HashMap<String, String>();
		
		if (request.getParameter("userlocation") != "")
			query.setUserLocation(request.getParameter("userlocation"));
		
		if (request.getParameter("screenname") != "")
			query.setScreenName(request.getParameter("screenname"));
		
		if (request.getParameter("username") != "")
			query.setUserName(request.getParameter("username"));
		
		
		if (request.getParameter("allwords") != "")
			query.setAllWords(request.getParameter("allwords"));
		
		if (request.getParameter("exactphrase") != "")
			query.setExactPhrase(request.getParameter("exactphrase"));
		
		if (request.getParameter("withoutwords") != "")
			query.setWithoutWord(request.getParameter("withoutwords"));
		
		if (request.getParameter("approxphrase") != "")
			query.setApproxPhrase(request.getParameter("approxphrase"));
		
		if (request.getParameter("hashtags") != "")
			query.setHashtag(request.getParameter("hashtags"));
		
         resultsLucene = new IndexTweetsLucene().getQueryResponse(query);
		
        response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.println("<b>LUCENE</b>");
		writer.println("<html><b>Below is the response to your query!</b></html>");
		writer.println("Found " + resultsLucene.size() + " hits.<br><br>");
		int i = 0;
		for (String result:resultsLucene.values()){
			writer.println("<br><br>" + (i+1)+ ".  " + result);
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
