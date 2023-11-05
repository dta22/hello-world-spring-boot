package htw.berlin.HelloWorld.service;

import htw.berlin.HelloWorld.api.Person;
import htw.berlin.HelloWorld.api.PersonCreateRequest;
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

    public List<Person> findAll(){
        List<PersonEntity> person = personRepository.findAll();

        return person.stream()
                .map(this::transformEntity
                )
                .collect(Collectors.toList());
    }

    public Person create(PersonCreateRequest request){
        var personEntity = new PersonEntity(request.getFirstName(), request.getLastName(), request.getAddress());
        personEntity = personRepository.save(personEntity);
        return transformEntity(personEntity);
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
