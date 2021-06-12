package eventoapp.dto;

import java.util.List;

public class EventTicketListDTO {
    private List<TicketGetDTO>  tickets;
    private EventTicketDTO      eventTicketDTO;

    public EventTicketListDTO() {
    }

    public EventTicketListDTO(List<TicketGetDTO>  tickets, EventTicketDTO eventTicketDTO) {
        this.tickets        = tickets;
        this.eventTicketDTO = eventTicketDTO;
    }
}
