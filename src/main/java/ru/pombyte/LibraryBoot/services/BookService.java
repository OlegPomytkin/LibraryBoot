package ru.pombyte.LibraryBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pombyte.LibraryBoot.models.Book;
import ru.pombyte.LibraryBoot.models.Person;
import ru.pombyte.LibraryBoot.repositories.BooksRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {

        this.booksRepository = booksRepository;

    }

    public List<Book> findAll(int page, int booksPerPage, Optional<Boolean> sort) {
        if (sort.isPresent())
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> findAll(Optional<Boolean> sort) {

        if (sort.isPresent()) return booksRepository.findAll(Sort.by("year"));
        else return booksRepository.findAll();
    }

    public List<Book> findBook(String param) {

        return booksRepository.findByNameStartingWith(param);
    }

    public Book findOne(int id) {

        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Person getOwner(int id) {

        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void save(Book book) {

        booksRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook) {

        Book oldBook = findOne(id);
        updatedBook.setId(id);
        updatedBook.setOwner(oldBook.getOwner());
        booksRepository.save(updatedBook);
    }
    @Transactional
    public void releaseBook(int id) {

        Book releasedBook = findOne(id);
        releasedBook.setOwner(null);
        releasedBook.setDateOfBorrow(null);

    }
    @Transactional
    public void setHolder(int id, Person chosenPerson) {

        Book updatedBook = findOne(id);
        updatedBook.setOwner(chosenPerson);
        updatedBook.setDateOfBorrow(new Date());
    }
    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

}
