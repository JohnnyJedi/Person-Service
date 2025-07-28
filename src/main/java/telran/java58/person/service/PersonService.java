package telran.java58.person.service;

import org.springframework.web.bind.annotation.PathVariable;
import telran.java58.person.dto.*;

import java.util.List;

public interface PersonService {

    void addPerson(PersonDto person);

    PersonDto getPerson(int id);

    PersonDto deletePerson(int id);

    PersonDto updatePersonName(Integer id, String newName);

    PersonDto updatePersonAddress(Integer id, AddressDto newAddress);

    PersonDto[] findPersonsByNames(String name);

    PersonDto[] findPersonsByCity(String city);

    PersonDto[] findPersonsBetweenAges(Integer minAge, Integer maxAge);

    Iterable<CityPopulationDto> getCitiesPopulation();

    List<ChildDto> getAllChildren();

    List<EmployeeDto> getEmployeesBySalary(int salaryFrom, int salaryTo);

}
