package ru.pombyte.LibraryBoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.pombyte.LibraryBoot.controllers.PeopleController;
import ru.pombyte.LibraryBoot.models.Person;
import ru.pombyte.LibraryBoot.services.PeopleService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(PeopleController.class)
public class PeopleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PeopleService service;

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
            throws Exception {

        Person alex = new Person();
        alex.setId(1);
        alex.setName("Alex");
        alex.setYear(2005);

        List<Person> allEmployees = Arrays.asList(alex);


        mvc.perform(get("/people").contentType(MediaType.ALL)).andExpect(status().isOk());


    }

}
