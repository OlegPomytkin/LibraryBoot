package ru.pombyte.LibraryBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pombyte.LibraryBoot.models.Person;
import ru.pombyte.LibraryBoot.services.PeopleService;
import javax.validation.Valid;
import java.sql.SQLException;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {

        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model) throws SQLException {

        model.addAttribute("people", peopleService.findAll());

        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) throws SQLException {

        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.showBooks(id));

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) throws SQLException {

        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) throws SQLException {

        Person requestingPerson = peopleService.findOne(id);

        if (requestingPerson != null) {
            model.addAttribute("person", peopleService.findOne(id));
            return "people/edit";
        }
        else
            return "redirect:/people/new";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) throws SQLException {

        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(id, person);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) throws SQLException {

        peopleService.delete(id);

        return "redirect:/people";
    }
}
