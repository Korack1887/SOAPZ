package com.example.SOAPZ.server.webcontroller;

import com.example.SOAPZ.entity.Film;
import com.example.SOAPZ.entity.Genre;
import com.example.SOAPZ.repository.FilmRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicReference;


@Controller
public class FilmWebController {
    FilmRepo repo;

    public FilmWebController(FilmRepo repo) {
        this.repo = repo;
    }

    @GetMapping(value = "/allFilms")
    public String allFilms(Model model) {
        model.addAttribute("allFilms", repo.findAll());
        return "film/allFilms";
    }

    @GetMapping(value = "/film/{id}")
    public String getFilmById(@PathVariable Long id, Model model){
        if(!repo.existsById(id)){
            return "redirect:/allFilms";
        }
        model.addAttribute("film", repo.getById(id));
        return "film/getFilmById";
    }

    @GetMapping("/createFilm")
    public String createFilm(Model model) {
        model.addAttribute("film", new Film());
        model.addAttribute("genres", Genre.getNames(Genre.class));
        return "film/createFilm";
    }

    @PostMapping(value = "/createFilm")
    public String createFilm(@ModelAttribute Film film, Model model) {
        model.addAttribute("film", film);
        repo.save(film);
        return "redirect:/allFilms";
    }

    @GetMapping("/updateFilm/{id}")
    public String updateFilm(@PathVariable Long id, Model model){
        Film film = new Film();
        film.setId(id);
        if (repo.existsById(id)){
            film = repo.getById(id);
        }
        model.addAttribute("film", film);
        return "film/updateFilm";
    }

    @PostMapping(value = "/updateFilm/{id}")
    public String updateFilm(@ModelAttribute Film newFilm, @PathVariable Long id) {
        AtomicReference<String> toReturn = new AtomicReference<>("");
        repo.findById(id)
                .map(film -> {
                    film.setTitle(newFilm.getTitle());
                    film.setDirector(newFilm.getDirector());
                    film.setDuration(newFilm.getDuration());
                    film.setDescription(newFilm.getDescription());
                    film.setGenre(newFilm.getGenre());
                    film.setLanguage(newFilm.getLanguage());
                    toReturn.set("redirect:/film/" + id);
                    return repo.save(film);
                })
                .orElseGet(() -> {
                    newFilm.setId(id);
                    toReturn.set("redirect:/allFilms");
                    return repo.save(newFilm);
                });
        return toReturn.get();
    }

    @GetMapping("/deleteFilm/{id}")
    public String deleteFilm(@PathVariable Long id){
        if(!repo.existsById(id)){
            return "redirect:/allFilms";
        }
        repo.deleteById(id);
        return "redirect:/allFilms";
    }

}
