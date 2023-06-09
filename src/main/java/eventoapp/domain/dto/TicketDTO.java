package eventoapp.domain.dto;

import eventoapp.domain.entities.enums.TicketType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDTO {
    private Long idAttendee;
    private TicketType typeTicket;

    public TicketDTO() {
    }
}
