package eventoapp.dto;

import java.util.List;

import eventoapp.models.Attendee;
import eventoapp.models.Event;
import eventoapp.models.Place;
import eventoapp.models.Ticket;
import eventoapp.models.enums.TicketType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketGetDTO {
    
    private String      name;
    private TicketType  ticketType;

    public TicketGetDTO() {}

	public TicketGetDTO(Ticket ticket) {
        this.name           = ticket.getAttendee().getName();
        this.ticketType     = ticket.getType();
	}
}
