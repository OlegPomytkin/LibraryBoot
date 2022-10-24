package ru.pombyte.LibraryBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pombyte.LibraryBoot.models.Book;
import ru.pombyte.LibraryBoot.models.Person;
import ru.pombyte.LibraryBoot.repositories.BooksRepository;
import ru.pombyte.LibraryBoot.repositories.PeopleRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {

        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<Person> findAll() {

        return peopleRepository.findAll(PageRequest.of(0, 4, Sort.by("name"))).getContent();
    }

    public Person findOne(int id) {

        Optional<Person> foundPerson = peopleRepository.findById(id);

        return foundPerson.orElse(null);
    }

    public List<Book> showBooks(int id) {

        Person targetPerson = findOne(id);
        Date today = new Date();
        List<Book> books = booksRepository.findByOwner(targetPerson);

        for (Book book : books) {
            if ((book.getDateOfBorrow() != null) && (today.getTime() - book.getDateOfBorrow().getTime()) / 86400000 > 10)
                book.setTooLate(true);
        }

        return books;
    }

    @Transactional
    public void save(Person person) {

        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {

        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {

        peopleRepository.deleteById(id);
    }
}
