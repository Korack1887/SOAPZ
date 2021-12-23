package com.example.SOAPZ.server.controllers;

import com.example.SOAPZ.entity.Session;
import com.example.SOAPZ.exception.SessionNotFoundException;
import com.example.SOAPZ.repository.SessionRepo;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
    class SessionController {

        private final SessionRepo repository;

        SessionController(SessionRepo repository) {
            this.repository = repository;
        }


        // Aggregate root
        // tag::get-aggregate-root[]
        @GetMapping("/sessions")
        CollectionModel<EntityModel<Session>> all() {

            List<EntityModel<Session>> sessions = repository.findAll().stream()
                    .map(session -> EntityModel.of(session,
                            linkTo(methodOn(SessionController.class).one(session.getId())).withSelfRel(),
                            linkTo(methodOn(SessionController.class).all()).withRel("sessions")))
                    .collect(Collectors.toList());

            return CollectionModel.of(sessions, linkTo(methodOn(SessionController.class).all()).withSelfRel());
        }
        // end::get-aggregate-root[]

        @PostMapping("/sessions")
        Session newSession(@RequestBody Session newSession) {
            return repository.save(newSession);
        }

        // Single item

        @GetMapping("/sessions/{id}")
        EntityModel<Session> one(@PathVariable Long id) {
            Session session = repository.findById(id)
                    .orElseThrow(() -> new SessionNotFoundException(id));

            return EntityModel.of(session,
                    linkTo(methodOn(SessionController.class).one(id)).withSelfRel(),
                    linkTo(methodOn(SessionController.class).all()).withRel("sessions"));
        }

        @PutMapping("/sessions/{id}")
        Session replaceSession(@RequestBody Session newSession, @PathVariable Long id) {

            return repository.findById(id)
                    .map(session -> {
                        session.setDate(newSession.getDate());
                        session.setTime(newSession.getTime());
                        session.setHall(newSession.getHall());
                        session.setFilm(newSession.getFilm());
                        return repository.save(session);
                    })
                    .orElseGet(() -> {
                        newSession.setId(id);
                        return repository.save(newSession);
                    });
        }

        @DeleteMapping("/sessions/{id}")
        void deleteSession(@PathVariable Long id) {
            repository.deleteById(id);
        }
}
