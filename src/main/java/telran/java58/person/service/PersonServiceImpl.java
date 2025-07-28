package telran.java58.person.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telran.java58.person.dao.PersonRepository;
import telran.java58.person.dto.*;
import telran.java58.person.dto.exception.PersonExistException;
import telran.java58.person.dto.exception.PersonNotFoundException;
import telran.java58.person.model.Address;
import telran.java58.person.model.Child;
import telran.java58.person.model.Employee;
import telran.java58.person.model.Person;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;


    public PersonDto determinatePersonInstance(Person person) {
        if (person instanceof Child) {
            return modelMapper.map(person, ChildDto.class);
        }
        if (person instanceof Employee) {
            return modelMapper.map(person, EmployeeDto.class);
        } else {
            return modelMapper.map(person, PersonDto.class);
        }
    }

    @Override
    @Transactional
    public void addPerson(PersonDto personDto) {
        if (personRepository.existsById(personDto.getId())) {
            throw new PersonExistException();
        }
        if (personDto instanceof EmployeeDto) {
            personRepository.save(modelMapper.map(personDto, Employee.class));
        } else if (personDto instanceof ChildDto) {
            personRepository.save(modelMapper.map(personDto, Child.class));
        } else {
            personRepository.save(modelMapper.map(personDto, Person.class));
        }
    }

    @Override
    public PersonDto getPerson(int id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        return determinatePersonInstance(person);
    }

    @Override
    @Transactional
    public PersonDto deletePerson(int id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        personRepository.delete(person);
        return determinatePersonInstance(person);
    }

    @Override
    @Transactional
    public PersonDto updatePersonName(Integer id, String newName) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        person.setName(newName);
//        personRepository.save(person);
        return determinatePersonInstance(person);
    }

    @Override
    @Transactional
    public PersonDto updatePersonAddress(Integer id, AddressDto newAddress) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        person.setAddress(modelMapper.map(newAddress, Address.class));
        return determinatePersonInstance(person);
    }

    @Override
//    @Transactional(readOnly = true)
    public PersonDto[] findPersonsByNames(String name) {
        Person[] persons = personRepository.findAllByName(name);
        return Arrays.stream(persons).map(this::determinatePersonInstance).toArray(PersonDto[]::new);
    }

    @Override
    public PersonDto[] findPersonsByCity(String city) {
        List<Person> persons = personRepository.searchAllByAddress_City(city);
        return persons.stream().map(this::determinatePersonInstance).toArray(PersonDto[]::new);
    }

    @Override
    public PersonDto[] findPersonsBetweenAges(Integer minAge, Integer maxAge) {
        LocalDate minDate = LocalDate.now().minusYears(minAge);
        LocalDate maxDate = LocalDate.now().minusYears(maxAge);
        Person[] persons = personRepository.findAllByBirthDateBetween(maxDate, minDate);
        return Arrays.stream(persons).map(this::determinatePersonInstance).toArray(PersonDto[]::new);
    }

    @Override
    public Iterable<CityPopulationDto> getCitiesPopulation() {
        return personRepository.showCityPopulation();
    }

    @Override
    public List<ChildDto> getAllChildren() {
        List<Child> children = personRepository.findAllChildren();
        return children.stream().map(child -> modelMapper.map(child, ChildDto.class)).toList();
    }

    @Override
    public List<EmployeeDto> getEmployeesBySalary(int salaryFrom, int salaryTo) {
        List<Employee> employeesBySalary = personRepository.findAllEmployeesBySalary(salaryFrom, salaryTo);
        return employeesBySalary.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class)).toList();
    }

    @Override
    public void run(String... args) throws Exception {
        if (personRepository.count() == 0) {
            Person person = new Person(1000, "John", LocalDate.of(1985, 3, 11), new Address("Tel Aviv", "Ben Gvirol", 81));
            Child child = new Child(2000, "Peter", LocalDate.of(2019, 7, 5), new Address("Ashkelon", "Bar Kohva", 21), "Shalom");
            Employee employee = new Employee(3000, "Mary", LocalDate.of(1995, 11, 23), new Address("Rehovot", "Ben Herzl", 7), "Microsoft", 20000);
            personRepository.saveAll(List.of(person, child, employee));
        }
    }
}
