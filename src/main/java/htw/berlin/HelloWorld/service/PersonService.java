package htw.berlin.HelloWorld.service;

import htw.berlin.HelloWorld.api.Person;
import htw.berlin.HelloWorld.api.PersonManipulationRequest;
import htw.berlin.HelloWorld.persistence.PersonEntity;
import htw.berlin.HelloWorld.persistence.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService (PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        List<PersonEntity> person = personRepository.findAll();

        return person.stream()
                .map(this::transformEntity
                )
                .collect(Collectors.toList());
    }

    public Person findById(Long id) {
        var personEntity = personRepository.findById(id);
        return personEntity.isPresent() ? transformEntity(personEntity.get()) : null;
    }

    public Person create(PersonManipulationRequest request) {
        //request ohne id, da DB die id selbst vergibt über ein INSERT
        var personEntity = new PersonEntity(request.getFirstName(), request.getLastName(), request.getAddress());
        personEntity = personRepository.save(personEntity);
        return transformEntity(personEntity);
    }

    public Person update(Long id, PersonManipulationRequest request) {
        var personEntityOptional = personRepository.findById(id);
        //check ob es Entity vorher schon gab, falls nicht return null
        if (personEntityOptional.isEmpty()) {
            return null;
        }

        //personEntity aus dem Optional herausholen
        var personEntity = personEntityOptional.get();
        //personEntity aktualisieren
        personEntity.setFirstName(request.getFirstName());
        personEntity.setLastName(request.getLastName());
        personEntity.setAddress(request.getAddress());

        //das zuvor vorgenommene update der personEntity muss über ein save der DB mitgeteilt werden
        personEntity = personRepository.save(personEntity);
        return transformEntity(personEntity);
    }

    public boolean deleteById (Long id) {
        //Überprüfen, ob Datensatz in DB existiert
        if (!personRepository.existsById(id)) {
            return false;
        }

        personRepository.deleteById(id);
        return true;
    }

    private Person transformEntity (PersonEntity personEntity){
        return new Person(
                personEntity.getId(),
                personEntity.getFirstName(),
                personEntity.getLastName(),
                personEntity.getAddress()
        );
    }
}
