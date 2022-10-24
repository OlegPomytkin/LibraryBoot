package ru.pombyte.LibraryBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pombyte.LibraryBoot.models.Book;
import ru.pombyte.LibraryBoot.models.Person;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book,Integer> {
    List<Book> findByOwner(Person owner);
    List<Book> findByNameStartingWith(String param);

}
