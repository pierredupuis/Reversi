package fr.isima.cours.jee.server.controllers;


import fr.isima.cours.jee.server.business.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/play")
public class PlayController {


    private final Game game;

    @Autowired
    public PlayController(Game game) {
        this.game = game;
    }

    @GetMapping
    public ModelAndView heure() {
        return new ModelAndView("home", "game", game.getTab());
    }
}


