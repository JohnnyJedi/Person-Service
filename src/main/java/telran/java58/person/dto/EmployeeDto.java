package telran.java58.person.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class EmployeeDto extends PersonDto{
    private String company;
    private int salary;

    public EmployeeDto(Integer id, String name, LocalDate birthDate, AddressDto address, String company, int salary) {
        super(id, name, birthDate, address);
        this.company = company;
        this.salary = salary;
    }
}
