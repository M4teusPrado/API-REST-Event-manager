package eventoapp.domain.dto;



import eventoapp.domain.entities.Ticket;
import eventoapp.domain.entities.enums.TicketType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketGetDTO {
    
    private Long        id;

    private String      name;
    private TicketType  ticketType;

    public TicketGetDTO() {}

	public TicketGetDTO(Ticket ticket) {
        this.id             = ticket.getId();
        this.name           = ticket.getAttendee().getName();
        this.ticketType     = ticket.getType();
	}
}
