package eventoapp.dto;

import eventoapp.models.Attendee;
import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class AttendeeDTO {
    
    private String name;
    private String email;
    private float balance;

    public AttendeeDTO(Attendee attendee) {
        this.name = attendee.getName();
        this.email = attendee.getEmail();
        this.balance = attendee.getBalance();
    }

    public AttendeeDTO() {}
}