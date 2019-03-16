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
            Board b = (Board) request.getAttribute("board");
            if(b.hasEnded())
                out.println(b.getWinner() + " wins! " + b.getScore());
            else
                out.println(b.getPlayer().getName());
        %>
        </div>
        <form action="${pageContext.request.contextPath}/place" method="post">
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
        <input type="submit" name="skip" value="Skip" />
        <input type="submit" name="reset" value="Reset" />
        </form>
        <!--<script type="text/javascript">
            $(function(){
                refresh();
            });
            function refresh(){
                setTimeout(refreshOnce,1000);
            }
            function refreshOnce(){
                $.ajax({
                    url:'${pageContext.request.contextPath}/place'
                });
                refresh();
            };

        </script>-->


    </body>
</html>