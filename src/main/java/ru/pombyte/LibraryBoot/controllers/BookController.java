package ru.pombyte.LibraryBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pombyte.LibraryBoot.models.Book;
import ru.pombyte.LibraryBoot.models.Person;
import ru.pombyte.LibraryBoot.services.BookService;
import ru.pombyte.LibraryBoot.services.PeopleService;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String indexBook(Model model, @RequestParam(value="page",required = false) Optional<Integer> page,
                            @RequestParam(value = "books_per_page",required = false) Optional <Integer> booksPerPage,
                             @RequestParam(value = "sort_by_year", required = false) Optional<Boolean> sort) throws SQLException {

        if(page.isPresent() && booksPerPage.isPresent())
            model.addAttribute("books", bookService.findAll(page.get(),booksPerPage.get(),sort));
        else
            model.addAttribute("books", bookService.findAll(sort));

        return "books/index";
    }

    @GetMapping("/search")
    public String searchBook(Model model, @RequestParam(value="param",required = false) Optional<String> param){
        if(param.isPresent()) {
            model.addAttribute("books", bookService.findBook(param.get()));

            return "books/search";
        }
        else
            return "books/search";
    }

    @GetMapping("/{id}")
    public String showBook(@ModelAttribute("person") Person person, @PathVariable("id") int id, Model model) throws SQLException {

        Book theBook = bookService.findOne(id);
        model.addAttribute("book", theBook);

        Person bookOwner = theBook.getOwner();
        if (bookOwner != null)
            model.addAttribute("holder", bookService.getOwner(id));
        else
            model.addAttribute("people", peopleService.findAll());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) throws SQLException {

        if (bindingResult.hasErrors())
            return "books/new";

        bookService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id) throws SQLException {

        Book requestingBook = bookService.findOne(id);
        if (requestingBook != null) {
            model.addAttribute("book", bookService.findOne(id));
            return "books/edit";
        }
        else
            return "redirect:/books/new";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) throws SQLException {

        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) throws SQLException {

        bookService.releaseBook(id);

        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/submit")
    public String submitBook(@ModelAttribute("person") Person chosenPerson, @PathVariable("id") int id) throws SQLException {

        bookService.setHolder(id, chosenPerson);

        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) throws SQLException {

        bookService.delete(id);

        return "redirect:/books";
    }

}
