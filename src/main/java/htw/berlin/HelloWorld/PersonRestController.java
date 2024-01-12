package htw.berlin.HelloWorld;

import htw.berlin.HelloWorld.api.Person;
import htw.berlin.HelloWorld.api.PersonManipulationRequest;
import htw.berlin.HelloWorld.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
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
    public ResponseEntity<List<Person>> fetchPersons() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping(path = "/api/v1/persons/{id}")
    public ResponseEntity<Person> fetchPersonById(@PathVariable Long id){
        var person = personService.findById(id);
        return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/api/v1/persons")
    public ResponseEntity<Void> createPerson(@Valid @RequestBody PersonManipulationRequest request) throws URISyntaxException {        var person  = personService.create(request);
        URI uri = new URI("/api/v1/persons/" + person.getId());
        return ResponseEntity.created(uri).build();
    }

    //Methode zur Aktualisierung einer Ressource
    @PutMapping(path = "/api/v1/persons/{id}")
        public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody PersonManipulationRequest request){
            var person = personService.update(id, request);
            return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/api/v1/persons/{id}")
        public ResponseEntity<Void> deletePerson (@PathVariable Long id) {
            boolean successful = personService.deleteById(id);
            return successful? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        }

}


