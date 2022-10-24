package ru.pombyte.LibraryBoot;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pombyte.LibraryBoot.models.Person;
import ru.pombyte.LibraryBoot.repositories.PeopleRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PeopleRepository peopleRepository;

    private Person alex;

    @Before
    public void setUp(){
        alex = new Person();
        alex.setId(1);
        alex.setName("Alex");
        alex.setYear(1977);
        alex = entityManager.merge(alex);
        entityManager.persist(alex);
        entityManager.flush();


    }

    @Test
    public void whenFindByName_thenReturnEmployee() {

        // when
        Person found = peopleRepository.findByName(alex.getName()).get();

        // then
        Assertions.assertTrue(found.getName().equals(alex.getName()),"Имя наоборот");

    }

    @Test
    public void findByIdTest() {

              // when
        Person found = peopleRepository.findById(alex.getId()).orElse(null);

        Assertions.assertNotNull(found);

        // then
        Assertions.assertTrue(found.equals(alex),"Пользователи не совпадают");

    }
}
