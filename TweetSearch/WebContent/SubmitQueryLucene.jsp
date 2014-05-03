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
    <b> LUCENE </b>
        <form action="GetQueryServletLucene" method="post" >
            <table>
            	 <tr>
                    <td>With the user location:</td>
                    <td><input type="text" name="userlocation" maxlength="20"></td>
                </tr>
                <tr>
                    <td>With the screen name:</td>
                    <td><input type="text" name="screenname" maxlength="20"></td>
                </tr>
                <tr>
                    <td>With the user name:</td>
                    <td><input type="text" name="username" maxlength="20"></td>
                </tr>
               </table>
               <br> Query on the Tweet text : <br>
               <table>
                <tr>
                    <td>With all of the words: </td>
                    <td><input type="text" name="allwords" maxlength="20"></td>
                </tr>
                <tr>
                    <td>Without the words:</td>
                    <td><input type="text" name="withoutwords" maxlength="20"></td>
                </tr>
                <tr>
                    <td>With the exact phrase:</td>
                    <td><input type="text" name="exactphrase" maxlength="20"></td>
                </tr>
                <tr>
                    <td>With the approximate phrase:</td>
                    <td><input type="text" name="approxphrase" maxlength="20"></td>
                </tr>
                
                <tr>
                    <td>With hashtags (separate using ,):</td>
                    <td><input type="text" name="hashtags" maxlength="20"></td>
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

