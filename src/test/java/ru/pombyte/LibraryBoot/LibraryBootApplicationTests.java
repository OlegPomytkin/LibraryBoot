package ru.pombyte.LibraryBoot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import ru.pombyte.LibraryBoot.controllers.PeopleController;
import ru.pombyte.LibraryBoot.models.Person;
import ru.pombyte.LibraryBoot.repositories.BooksRepository;
import ru.pombyte.LibraryBoot.repositories.PeopleRepository;
import ru.pombyte.LibraryBoot.services.PeopleService;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
		value = "SpringBootTest.WebEnvironment.MOCK",
		classes = LibraryBootApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")
public class LibraryBootApplicationTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private PeopleRepository repository;
	@MockBean
	private BooksRepository booksRepository;

	@TestConfiguration
	class EmployeeServiceImplTestContextConfiguration {

		@Bean
		public PeopleService peopleService() {
			return new PeopleService(repository, booksRepository);
		}
	}
	@Autowired
	private PeopleService peopleService;

@Autowired
	public LibraryBootApplicationTests() {

	}

	private Person test;

	@BeforeEach
	public void setup(){
		test = new Person();
		test.setName("Oleg");
		test.setYear(2000);
		test.setId(5);

		Mockito.when(repository.findById(test.getId()))
				.thenReturn(Optional.of(test));

	}

	@Test
	public void whenValidName_thenEmployeeShouldBeFound() {
		int id = 5;
		String name = "Ваганова Екатерина Сергеевна";
		Person found = peopleService.findOne(8);
		System.out.println(repository.findById(5));


		Assertions.assertEquals(found.getName(),name,"Тест имени провален");
	}

	@Test
	public void contextLoads() {


		Assertions.assertTrue(!repository.findAll().isEmpty());


	}

	@Nested
	@RunWith(SpringRunner.class)
	@DataJpaTest
	public class EmployeeRepositoryIntegrationTest {

		@Autowired
		private TestEntityManager entityManager;

		@Autowired
		private PeopleRepository peopleRepository;

		@Test
		public void whenFindByName_thenReturnEmployee() {
			// given
			Person alex = new Person();
			alex.setId(1);
			alex.setName("Alex");
			alex.setYear(1977);
			entityManager.persist(alex);
			entityManager.flush();

			// when
			Person found = peopleRepository.findByName(alex.getName()).get();

			// then
			Assertions.assertTrue(found.getName().equals(alex.getName()));
		}



	}


}
