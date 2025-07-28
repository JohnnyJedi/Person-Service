package telran.java58.person.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import telran.java58.person.dto.AddressDto;
import telran.java58.person.dto.CityPopulationDto;
import telran.java58.person.model.Address;
import telran.java58.person.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person[] findAllByName(String name);

    @Query("select p from Person p where p.address.city=?1")
    List<Person> searchAllByAddress_City(String addressCity);

    Stream<Person> searchStreamsAllByAddress_City(String addressCity);


    Person[] findAllByBirthDateBetween(LocalDate birthDateAfter, LocalDate birthDateBefore);

    @Query("select new telran.java58.person.dto.CityPopulationDto(p.address.city, count (p) ) " +
            "from Person p group by p.address.city order by count(p)")
    Iterable<CityPopulationDto> showCityPopulation();
}
