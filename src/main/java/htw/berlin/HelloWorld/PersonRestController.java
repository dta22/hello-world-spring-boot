package htw.berlin.HelloWorld;

import htw.berlin.HelloWorld.api.Person;
import htw.berlin.HelloWorld.api.PersonCreateRequest;
import htw.berlin.HelloWorld.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/*
@RestController
public class PersonRestController {

    private List<Person> persons;

    public PersonRestController(List<Person> persons) {
        this.persons = new ArrayList<>();
        this.persons.add(new Person(1, "Max", "Mustermann", "Treskowallee"));
        this.persons.add(new Person(2, "Maxima", "Meier", "Wilhelminenhof"));

    }

    @GetMapping(path = "/api/v1/persons")
    public ResponseEntity<List<Person>> fetchPerson(){
        return ResponseEntity.ok(persons);
    }

}

 */



@RestController
public class PersonRestController {

    private final PersonService personService;

    public PersonRestController(PersonService personService) {
        this.personService = personService;
    }



    /*
    public PersonRestController(List<Person> persons) {
        this.persons = new ArrayList<>();
        this.persons.add(new Person(1, "Max", "Mustermann", "Treskowallee"));
        this. persons.add(new Person(2, "Maxima", "Meier", "Wilhelminenhof"));

    }
     */


    @GetMapping(path = "/api/v1/persons")
    public ResponseEntity<List<Person>> fetchPerson() {
        return ResponseEntity.ok(personService.findAll());
    }

    @PostMapping(path = "/api/v1/persons")
    public ResponseEntity<Void> createPerson(@RequestBody PersonCreateRequest request) throws URISyntaxException {
        var person  = personService.create(request);
        URI uri = new URI("/api/v1/persons/" + person.getId());
        return ResponseEntity.created(uri).build();
    }
}


