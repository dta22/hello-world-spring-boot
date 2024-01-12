package htw.berlin.HelloWorld.web;

import htw.berlin.HelloWorld.PersonRestController;
import htw.berlin.HelloWorld.service.PersonService;
import htw.berlin.HelloWorld.api.Person;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonRestController.class)
class PersonRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    @DisplayName("should return found persons from person service")
    void should_return_found_person_from_person_service() throws Exception {
        // given
        var persons = List.of(
                /*
                new Person(1L, "John", "Doe", "Treskowallee", Collections.emptyList()),
                new Person(2L, "Maria", "Thompson",  "Wilhelminenhof", Collections.emptyList()
                );
                 */
                new Person(1L, "John", "Doe", "Treskowallee"),
                new Person(2L, "Maria", "Thompson",  "Wilhelminenhof")
                );
        doReturn(persons).when(personService).findAll();
        // when
        mockMvc.perform(get("/api/v1/persons"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].address").value("Treskowallee"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Maria"))
                .andExpect(jsonPath("$[1].lastName").value("Thompson"))
                //.andExpect(jsonPath("$[0].address").value("Wilhelminenhof"));
                .andExpect(jsonPath("$[0].address").value("Treskowallee"));
    }

    @Test
    @DisplayName("should return 404 if person is not found")
    void should_return_404_if_person_is_not_found() throws Exception {
        // given
        doReturn(null).when(personService).findById(anyLong());

        // when
        mockMvc.perform(get("/api/v1/persons/123"))
                // then
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return 201 http status and Location header when creating a person")
    void should_return_201_http_status_and_location_header_when_creating_a_person() throws Exception {
        // given
        String personToCreateAsJson = "{\"firstName\": \"John\", \"lastName\":\"Doe\", \"address\":\"Treskowallee\"}";
        var person = new Person(123L, null, null, null);
        doReturn(person).when(personService).create(any());

        // when
        mockMvc.perform(
                        post("/api/v1/persons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(personToCreateAsJson)
                )
                // then
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.equalTo(("/api/v1/persons/" + person.getId()))));
//            .andExpect(header().string("Location", Matchers.containsString(Long.toString(person.getId()))));

    }

    @Test
    @DisplayName("should validate create person request")
    void should_validate_create_person_request() throws Exception {
        // given
        String personToCreateAsJson = "{\"firstName\": \"a\", \"lastName\":\"\", \"address\":\"Treskowallee\"}";

        // when
        mockMvc.perform(
                        post("/api/v1/persons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(personToCreateAsJson)
                )
                // then
                .andExpect(status().isBadRequest());
    }
}
