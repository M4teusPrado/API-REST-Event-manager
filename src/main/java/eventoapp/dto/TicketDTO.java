package eventoapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDTO {
    private Long        idAttendee;
    private String      typeTicket;

    public TicketDTO() {}
}
