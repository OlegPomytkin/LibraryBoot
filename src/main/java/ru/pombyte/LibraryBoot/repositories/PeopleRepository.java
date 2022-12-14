package ru.pombyte.LibraryBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pombyte.LibraryBoot.models.Person;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
    Optional<Person> findByName(String param);

}
