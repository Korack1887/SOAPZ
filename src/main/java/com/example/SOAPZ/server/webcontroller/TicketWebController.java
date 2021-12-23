package com.example.SOAPZ.server.webcontroller;

import com.example.SOAPZ.entity.Session;
import com.example.SOAPZ.entity.Ticket;
import com.example.SOAPZ.repository.SessionRepo;
import com.example.SOAPZ.repository.TicketRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class TicketWebController {
    TicketRepo repo;
    SessionRepo sessionRepo;

    public TicketWebController(TicketRepo repo, SessionRepo sessionRepo) {
        this.repo = repo;
        this.sessionRepo = sessionRepo;
    }

    @GetMapping(value = "/allTickets")
    public String allTickets(Model model) {
        model.addAttribute("allTickets", repo.findAll());
        return "ticket/allTickets";
    }

    @GetMapping(value = "/ticket/{id}")
    public String getTicketById(@PathVariable Long id, Model model) {
        if (!repo.existsById(id)) {
            return "redirect:/allTickets";
        }
        model.addAttribute("ticket", repo.getById(id));
        return "ticket/getTicketById";
    }

    @GetMapping("/createTicket")
    public String createTicket(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("sessionList", sessionRepo.findAll());
        return "ticket/createTicket";
    }

    @PostMapping(value = "/createTicket")
    public String createTicket(@ModelAttribute Ticket ticket, Model model, @RequestParam("sessionId") Long sessionId) {
        model.addAttribute("ticket", ticket);
        Session session = sessionRepo.getById(sessionId);
        ticket.setSession(session);
        repo.save(ticket);
        return "redirect:/allTickets";
    }

    @GetMapping("/updateTicket/{id}")
    public String updateTicket(@PathVariable Long id, Model model) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        if (repo.existsById(id)) {
            ticket = repo.getById(id);
        }
        model.addAttribute("sessionList", sessionRepo.findAll());
        model.addAttribute("ticket", ticket);
        return "ticket/updateTicket";
    }

    @PostMapping(value = "/updateTicket/{id}")
    public String updateTicket(@ModelAttribute Ticket newTicket, @PathVariable Long id, @RequestParam("sessionId") Long sessionId) {
        AtomicReference<String> toReturn = new AtomicReference<>("");
        Session session = sessionRepo.getById(sessionId);
        newTicket.setSession(session);
        repo.findById(id)
                .map(ticket -> {
                    ticket.setPrice(newTicket.getPrice());
                    ticket.setSession(newTicket.getSession());
                    ticket.setIdSeat(newTicket.getIdSeat());
                    ticket.setIdColumn(newTicket.getIdColumn());
                    ticket.setStatus(newTicket.getStatus());
                    toReturn.set("redirect:/ticket/" + id);
                    return repo.save(ticket);
                })
                .orElseGet(() -> {
                    newTicket.setId(id);
                    toReturn.set("redirect:/allTickets");
                    return repo.save(newTicket);
                });
        return toReturn.get();
    }

    @GetMapping("/deleteTicket/{id}")
    public String deleteTicket(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return "redirect:/allTickets";
        }
        repo.deleteById(id);
        return "redirect:/allTickets";
    }
}
