<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>FORM</title>

    </head>
    <body>
    <center><br><br><br><br>
    <b> HADOOP </b> <br>
        <form action="GetQueryServletHadoop" method="post" >
        
               <br> Query on the Tweet text : <br>
               <table>
                <tr>
                    <td>Query String :</td>
                    <td><input type="text" name="allwords" maxlength="20"></td>
                </tr>
                
                
                 <tr>
                    <td></td>
                    <td><input type="submit" value="Submit"></td>
                </tr>
            </table>
          
        </form>
         <a href="Main.jsp">Back to Home Page</a>
           </center>
    </body>
</html>

