package telran.java58.person.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import telran.java58.person.dto.*;
import telran.java58.person.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody PersonDto personDto) {
        personService.addPerson(personDto);
    }

    @GetMapping("/{id}")
    public PersonDto getPerson(@PathVariable int id) {
        return personService.getPerson(id);
    }

    @DeleteMapping("/{id}")
    public PersonDto deletePerson(@PathVariable int id) {
        return personService.deletePerson(id);
    }

    @PatchMapping("/{id}/name/{name}")
    public PersonDto updatePersonName(@PathVariable Integer id, @PathVariable String name) {
        return personService.updatePersonName(id, name);
    }

    @PatchMapping("/{id}/address")
    public PersonDto updatePersonAddress(@PathVariable Integer id, @RequestBody AddressDto newAddress) {
        return personService.updatePersonAddress(id, newAddress);
    }

    @GetMapping("/name/{name}")
    public PersonDto[] findPersonsByNames(@PathVariable String name) {
        return personService.findPersonsByNames(name);
    }

    @GetMapping("/city/{city}")
    public PersonDto[] findPersonsByCity(@PathVariable String city) {
        return personService.findPersonsByCity(city);
    }

    @GetMapping("/ages/{minAge}/{maxAge}")
    public PersonDto[] findPersonsBetweenAges(@PathVariable Integer minAge, @PathVariable Integer maxAge) {
        return personService.findPersonsBetweenAges(minAge, maxAge);
    }

    @GetMapping("/population/city")
    public Iterable<CityPopulationDto> getCitiesPopulation() {
        return personService.getCitiesPopulation();
    }

    @GetMapping("/children")
    public List<ChildDto> getAllChildren() {
        return personService.getAllChildren();
    }

    @GetMapping("/salary/{salaryFrom}/{salaryTo}")
    public List<EmployeeDto> getEmployeesBySalary(@PathVariable int salaryFrom, @PathVariable int salaryTo) {
        return personService.getEmployeesBySalary(salaryFrom, salaryTo);
    }
}
