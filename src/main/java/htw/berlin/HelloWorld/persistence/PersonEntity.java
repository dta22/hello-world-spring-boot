package htw.berlin.HelloWorld.persistence;

//import javax.persistence.*;
import jakarta.persistence.*;

//import javax.persistence.Entity;

@Entity(name = "persons")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column (name = "id")
    private Long id;

    @Column (name = "firstName", nullable = false)
    private String firstName;

    @Column (name = "lastName", nullable = false)
    private String lastName;

    @Column (name = "address")
    private String address;

    public PersonEntity(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    protected PersonEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
