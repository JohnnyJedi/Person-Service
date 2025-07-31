package telran.java58.person.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ChildDto extends PersonDto{
    private String kindergarten;

    public ChildDto(int id, String name, LocalDate birthDate, AddressDto address, String kindergarten) {
        super(id, name, birthDate, address);
        this.kindergarten = kindergarten;
    }
}
