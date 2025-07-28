package telran.java58.person.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.java58.person.model.Address;
import telran.java58.person.model.Person;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person[] findAllByName(String name);


    List<Person> searchAllByAddress_City(String addressCity);

    Person[] findAllByBirthDateBetween(LocalDate birthDateAfter, LocalDate birthDateBefore);
}
