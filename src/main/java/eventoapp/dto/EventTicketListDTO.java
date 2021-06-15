package eventoapp.dto;

import java.util.List;

public class EventTicketListDTO {
    private EventTicketDTO      eventTicketDTO;
    private List<TicketGetDTO>  tickets;

    public EventTicketListDTO() {
    }

    public EventTicketDTO getEventTicketDTO() {
        return eventTicketDTO;
    }

    public void setEventTicketDTO(EventTicketDTO eventTicketDTO) {
        this.eventTicketDTO = eventTicketDTO;
    }

    public EventTicketListDTO(List<TicketGetDTO>  tickets, EventTicketDTO eventTicketDTO) {
        this.tickets        = tickets;
        this.eventTicketDTO = eventTicketDTO;
    }

    public List<TicketGetDTO> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketGetDTO> tickets) {
        this.tickets = tickets;
    }
}
