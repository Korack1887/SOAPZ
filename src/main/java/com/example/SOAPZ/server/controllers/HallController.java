package com.example.SOAPZ.server.controllers;

import com.example.SOAPZ.entity.Hall;
import com.example.SOAPZ.exception.HallNotFoundException;
import com.example.SOAPZ.repository.HallRepo;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
    class HallController {

        private final HallRepo repository;

        HallController(HallRepo repository) {
            this.repository = repository;
        }


        // Aggregate root
        // tag::get-aggregate-root[]
        @GetMapping("/halls")
        CollectionModel<EntityModel<Hall>> all() {

            List<EntityModel<Hall>> halls = repository.findAll().stream()
                    .map(hall -> EntityModel.of(hall,
                            linkTo(methodOn(HallController.class).one(hall.getId())).withSelfRel(),
                            linkTo(methodOn(HallController.class).all()).withRel("halls")))
                    .collect(Collectors.toList());

            return CollectionModel.of(halls, linkTo(methodOn(HallController.class).all()).withSelfRel());
        }
        // end::get-aggregate-root[]

        @PostMapping("/halls")
        Hall newHall(@RequestBody Hall newHall) {
            return repository.save(newHall);
        }

        // Single item

        @GetMapping("/halls/{id}")
        EntityModel<Hall> one(@PathVariable Long id) {
            Hall hall = repository.findById(id)
                    .orElseThrow(() -> new HallNotFoundException(id));

            return EntityModel.of(hall,
                    linkTo(methodOn(HallController.class).one(id)).withSelfRel(),
                    linkTo(methodOn(HallController.class).all()).withRel("halls"));
        }

        @PutMapping("/halls/{id}")
        Hall replaceHall(@RequestBody Hall newHall, @PathVariable Long id) {

            return repository.findById(id)
                    .map(hall -> {
                        hall.setType(newHall.getType());
                        hall.setColumnQuantity(newHall.getColumnQuantity());
                        hall.setSeatQuantity(newHall.getSeatQuantity());
                        return repository.save(hall);
                    })
                    .orElseGet(() -> {
                        newHall.setId(id);
                        return repository.save(newHall);
                    });
        }

        @DeleteMapping("/halls/{id}")
        void deleteHall(@PathVariable Long id) {
            repository.deleteById(id);
        }
}
