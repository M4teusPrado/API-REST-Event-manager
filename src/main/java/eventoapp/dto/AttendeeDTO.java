package eventoapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.PositiveOrZero;

import eventoapp.models.Attendee;
import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class AttendeeDTO {
    
    @NotBlank(message = "{name.not.blank}")
    @Size(min = 3, max = 20)
    private String name;


    @NotBlank(message = "{email.not.blank}")
    @Email(message = "{email.not.valid}")
    private String email;

    @PositiveOrZero
    private float balance;

    public AttendeeDTO(Attendee attendee) {
        this.name = attendee.getName();
        this.email = attendee.getEmail().getValue();
        this.balance = attendee.getBalance();
    }

    public AttendeeDTO() {}
}