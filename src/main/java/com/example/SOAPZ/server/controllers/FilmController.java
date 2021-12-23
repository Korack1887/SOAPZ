package com.example.SOAPZ.server.controllers;

import com.example.SOAPZ.entity.Film;
import com.example.SOAPZ.exception.FilmNotFoundException;
import com.example.SOAPZ.repository.FilmRepo;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
    class FilmController {

        private final FilmRepo repository;

        FilmController(FilmRepo repository) {
            this.repository = repository;
        }


        // Aggregate root
        // tag::get-aggregate-root[]
        @GetMapping("/films")
        CollectionModel<EntityModel<Film>> all() {

            List<EntityModel<Film>> films = repository.findAll().stream()
                    .map(film -> EntityModel.of(film,
                            linkTo(methodOn(FilmController.class).one(film.getId())).withSelfRel(),
                            linkTo(methodOn(FilmController.class).all()).withRel("films")))
                    .collect(Collectors.toList());

            return CollectionModel.of(films, linkTo(methodOn(FilmController.class).all()).withSelfRel());
        }
        // end::get-aggregate-root[]

        @PostMapping("/films")
        Film newFilm(@RequestBody Film newFilm) {
            return repository.save(newFilm);
        }

        // Single item

        @GetMapping("/films/{id}")
        EntityModel<Film> one(@PathVariable Long id) {
            Film film = repository.findById(id)
                    .orElseThrow(() -> new FilmNotFoundException(id));

            return EntityModel.of(film,
                    linkTo(methodOn(FilmController.class).one(id)).withSelfRel(),
                    linkTo(methodOn(FilmController.class).all()).withRel("films"));
        }

        @PutMapping("/films/{id}")
        Film replaceFilm(@RequestBody Film newFilm, @PathVariable Long id) {

            return repository.findById(id)
                    .map(film -> {
                        film.setTitle(newFilm.getTitle());
                        film.setDirector(newFilm.getDirector());
                        film.setDuration(newFilm.getDuration());
                        film.setDescription(newFilm.getDescription());
                        film.setGenre(newFilm.getGenre());
                        film.setLanguage(newFilm.getLanguage());
                        return repository.save(film);
                    })
                    .orElseGet(() -> {
                        newFilm.setId(id);
                        return repository.save(newFilm);
                    });
        }

        @DeleteMapping("/films/{id}")
        void deleteFilm(@PathVariable Long id) {
            repository.deleteById(id);
        }
}
