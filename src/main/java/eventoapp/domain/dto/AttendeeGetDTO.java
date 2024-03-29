package eventoapp.domain.dto;

import java.util.List;

import eventoapp.domain.entities.Attendee;
import eventoapp.domain.entities.Ticket;
import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class AttendeeGetDTO {
    
    private Long            id;
    private String          name;
    private String          email;
    private float           balance;
    private List<Ticket>    tickets;
    
    public AttendeeGetDTO(Attendee attendee) {
        this.id         = attendee.getId();
        this.name       = attendee.getName();
        this.email      = attendee.getEmail().getEmail();
        this.balance    = attendee.getBalance();
        this.tickets    = attendee.getTickets();
    }

    public AttendeeGetDTO() {}
}