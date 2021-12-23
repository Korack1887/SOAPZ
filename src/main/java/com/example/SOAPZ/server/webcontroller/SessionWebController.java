package com.example.SOAPZ.server.webcontroller;

import com.example.SOAPZ.entity.Film;
import com.example.SOAPZ.entity.Hall;
import com.example.SOAPZ.entity.Session;
import com.example.SOAPZ.entity.Session;
import com.example.SOAPZ.repository.FilmRepo;
import com.example.SOAPZ.repository.HallRepo;
import com.example.SOAPZ.repository.SessionRepo;
import com.example.SOAPZ.repository.SessionRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicReference;

@Controller
public class SessionWebController {
    SessionRepo repo;
    HallRepo hallRepo;
    FilmRepo filmRepo;

    public SessionWebController(SessionRepo repo, HallRepo hallRepo, FilmRepo filmRepo) {
        this.repo = repo;
        this.hallRepo = hallRepo;
        this.filmRepo = filmRepo;
    }

    @GetMapping(value = "/allSessions")
    public String allSessions(Model model) {
        model.addAttribute("allSessions", repo.findAll());
        return "session/allSessions";
    }

    @GetMapping(value = "/session/{id}")
    public String getSessionById(@PathVariable Long id, Model model) {
        if (!repo.existsById(id)) {
            return "redirect:/allSessions";
        }
        model.addAttribute("sess", repo.getById(id));
        return "session/getSessionById";
    }

    @GetMapping("/createSession")
    public String createSession(Model model) {
        model.addAttribute("sess", new Session());
        model.addAttribute("films", filmRepo.findAll());
        model.addAttribute("halls", hallRepo.findAll());
        return "session/createSession";
    }

    @PostMapping(value = "/createSession")
    public String createSession(@ModelAttribute Session session, Model model, @RequestParam("filmId") Long filmId, @RequestParam("hallId") Long hallId) {
        model.addAttribute("film", session);
        model.addAttribute("hall", session);
        Film film = filmRepo.getById(filmId);
        Hall hall = hallRepo.getById(hallId);
        session.setFilm(film);
        session.setHall(hall);
        repo.save(session);
        return "redirect:/allSessions";
    }

    @GetMapping("/updateSession/{id}")
    public String updateSession(@PathVariable Long id, Model model) {
        Session session = new Session();
        session.setId(id);
        if (repo.existsById(id)) {
            session = repo.getById(id);
        }
        model.addAttribute("films", filmRepo.findAll());
        model.addAttribute("halls", hallRepo.findAll());
        model.addAttribute("sess", session);
        return "session/updateSession";
    }

    @PostMapping(value = "/updateSession/{id}")
    public String updateSession(@ModelAttribute Session newSession, @PathVariable Long id, @RequestParam("filmId") Long filmId, @RequestParam("hallId") Long hallId) {
        AtomicReference<String> toReturn = new AtomicReference<>("");
        Film film = filmRepo.getById(filmId);
        Hall hall = hallRepo.getById(hallId);
        newSession.setFilm(film);
        newSession.setHall(hall);
        repo.findById(id)
                .map(session -> {
                    session.setFilm(newSession.getFilm());
                    session.setDate(newSession.getDate());
                    session.setTime(newSession.getTime());
                    session.setHall(newSession.getHall());
                    toReturn.set("redirect:/session/" + id);
                    return repo.save(session);
                })
                .orElseGet(() -> {
                    newSession.setId(id);
                    toReturn.set("redirect:/allSessions");
                    return repo.save(newSession);
                });
        return toReturn.get();
    }

    @GetMapping("/deleteSession/{id}")
    public String deleteSession(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return "redirect:/allSessions";
        }
        repo.deleteById(id);
        return "redirect:/allSessions";
    }
}
