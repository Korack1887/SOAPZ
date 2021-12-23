package com.example.SOAPZ.server.webcontroller;

import com.example.SOAPZ.entity.Session;
import com.example.SOAPZ.entity.Hall;
import com.example.SOAPZ.repository.SessionRepo;
import com.example.SOAPZ.repository.HallRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicReference;

@Controller
public class HallWebController {
    HallRepo repo;

    public HallWebController(HallRepo repo) {
        this.repo = repo;
    }

    @GetMapping(value = "/allHalls")
    public String allHalls(Model model) {
        model.addAttribute("allHalls", repo.findAll());
        return "hall/allHalls";
    }

    @GetMapping(value = "/hall/{id}")
    public String getHallById(@PathVariable Long id, Model model) {
        if (!repo.existsById(id)) {
            return "redirect:/allHalls";
        }
        model.addAttribute("hall", repo.getById(id));
        return "hall/getHallById";
    }

    @GetMapping("/createHall")
    public String createHall(Model model) {
        model.addAttribute("hall", new Hall());
        return "hall/createHall";
    }

    @PostMapping(value = "/createHall")
    public String createHall(@ModelAttribute Hall hall, Model model) {
        model.addAttribute("hall", hall);
        repo.save(hall);
        return "redirect:/allHalls";
    }

    @GetMapping("/updateHall/{id}")
    public String updateHall(@PathVariable Long id, Model model) {
        Hall hall = new Hall();
        hall.setId(id);
        if (repo.existsById(id)) {
            hall = repo.getById(id);
        }
        model.addAttribute("hall", hall);
        return "hall/updateHall";
    }

    @PostMapping(value = "/updateHall/{id}")
    public String updateHall(@ModelAttribute Hall newHall, @PathVariable Long id) {
        AtomicReference<String> toReturn = new AtomicReference<>("");
        repo.findById(id)
                .map(hall -> {
                    hall.setType(newHall.getType());
                    hall.setColumnQuantity(newHall.getColumnQuantity());
                    hall.setSeatQuantity(newHall.getSeatQuantity());
                    toReturn.set("redirect:/hall/" + id);
                    return repo.save(hall);
                })
                .orElseGet(() -> {
                    newHall.setId(id);
                    toReturn.set("redirect:/allHalls");
                    return repo.save(newHall);
                });
        return toReturn.get();
    }

    @GetMapping("/deleteHall/{id}")
    public String deleteHall(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return "redirect:/allHalls";
        }
        repo.deleteById(id);
        return "redirect:/allHalls";
    }
}
