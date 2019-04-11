<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1" %>
<%@page import="java.time.LocalDateTime" %>
<%@page import="java.time.format.DateTimeFormatter" %>
<%@page import="fr.isima.cours.jee.server.business.Board" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Reversi</title>
    </head>
    <script
      src="https://code.jquery.com/jquery-3.3.1.min.js"
      integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
      crossorigin="anonymous"></script>
    <body>
        <h1> Simple Reversi </h1>
        <div id="player">
        <style>
            tr {
                height: 40px
            }
            td {
                width: 40px;
            }
            table {
                background-color: #86e540;
            }
        </style>
        <%
            boolean turn = false;
            Board b = (Board) request.getAttribute("board");
            String color = (String) request.getAttribute("color");
            if("black".equals(color)){
                %><style> body { color: white; background-color: black; } </style><%
            }

            if(b.hasEnded())
                out.println(b.getWinner() + " wins! " + b.getScore());
            else {
                if(b.getPlayer().getName().equals(color)){
                    turn = true;
                    %> <p> Your turn ! </p> <%
                } else {
                    %> <p> Your opponent is thinking... </p> <%
                }
            }
        %>
        <br>
        <p> you are <%=color%> </p>
        <br>
        <form action="${pageContext.request.contextPath}/play" method="post">
        <table border="1">
        <%
            String name;
            String value;

            for(int i =0; i < 8; i++){
                out.println("<tr>");
                for(int j =0; j < 8; j++){
                    name = Integer.toString(i) + "_" + Integer.toString(j);
                    value = b.get(i,j).getImagePath();

                    %>
                    <td>
                        <input type="image" name="<%=name%>" src="<%=value%>" alt="fuck.">
                    </td>
                    <%
                }
                out.println("</tr>");
            }
        %>
        </table>
        <% if(turn) { %>
            <input type="submit" name="skip" value="Skip" />
        <% } %>
        <input type="submit" name="reset" value="Reset" />
        <input type="hidden" name="color" value="<%=color%>")>
        </form>
        </div>
        <% if(!b.hasEnded()) { %>
            <script type="text/javascript">
                setInterval(function(){
                $('#player').load("http://localhost/play #player", {color: "<%=color%>"}, function( response, status, xhr ) {
                                                                     console.log( xhr.status + " " + xhr.statusText );
                                                                 });
                }, 1000);
            </script>
           <% } %>

    </body>
</html>