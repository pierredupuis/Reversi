package fr.isima.cours.jee.server.controllers;

import fr.isima.cours.jee.server.business.Board;
import fr.isima.cours.jee.server.business.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet({"/place"})
public class GameServlet extends HttpServlet {

    Board board = new Board();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("board", board);

        this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/home.jsp" ).forward( request, response );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("skip") != null)
            board.skipTurn();
        else if(request.getParameter("reset") != null)
            board.reset();

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                if (request.getParameter(i + "_" + j + ".x") != null) {
                    board.play(i, j);
                }
            }
        }
        request.setAttribute("board", board);
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
    }

}

