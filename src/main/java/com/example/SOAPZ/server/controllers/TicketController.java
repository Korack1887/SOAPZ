package com.example.SOAPZ.server.controllers;

import com.example.SOAPZ.entity.Ticket;
import com.example.SOAPZ.exception.TicketNotFoundException;
import com.example.SOAPZ.repository.TicketRepo;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
    class TicketController {

        private final TicketRepo repository;

        TicketController(TicketRepo repository) {
            this.repository = repository;
        }


        // Aggregate root
        // tag::get-aggregate-root[]
        @GetMapping("/tickets")
        CollectionModel<EntityModel<Ticket>> all() {

            List<EntityModel<Ticket>> tickets = repository.findAll().stream()
                    .map(ticket -> EntityModel.of(ticket,
                            linkTo(methodOn(TicketController.class).one(ticket.getId())).withSelfRel(),
                            linkTo(methodOn(TicketController.class).all()).withRel("tickets")))
                    .collect(Collectors.toList());

            return CollectionModel.of(tickets, linkTo(methodOn(TicketController.class).all()).withSelfRel());
        }
        // end::get-aggregate-root[]

        @PostMapping("/tickets")
        Ticket newTicket(@RequestBody Ticket newTicket) {
            return repository.save(newTicket);
        }

        // Single item

        @GetMapping("/tickets/{id}")
        EntityModel<Ticket> one(@PathVariable Long id) {
            Ticket ticket = repository.findById(id)
                    .orElseThrow(() -> new TicketNotFoundException(id));

            return EntityModel.of(ticket,
                    linkTo(methodOn(TicketController.class).one(id)).withSelfRel(),
                    linkTo(methodOn(TicketController.class).all()).withRel("tickets"));
        }

        @PutMapping("/tickets/{id}")
        Ticket replaceTicket(@RequestBody Ticket newTicket, @PathVariable Long id) {

            return repository.findById(id)
                    .map(ticket -> {
                        ticket.setPrice(newTicket.getPrice());
                        ticket.setSession(newTicket.getSession());
                        ticket.setIdSeat(newTicket.getIdSeat());
                        ticket.setIdColumn(newTicket.getIdColumn());
                        ticket.setStatus(newTicket.getStatus());
                        return repository.save(ticket);
                    })
                    .orElseGet(() -> {
                        newTicket.setId(id);
                        return repository.save(newTicket);
                    });
        }

        @DeleteMapping("/tickets/{id}")
        void deleteTicket(@PathVariable Long id) {
            repository.deleteById(id);
        }
}
