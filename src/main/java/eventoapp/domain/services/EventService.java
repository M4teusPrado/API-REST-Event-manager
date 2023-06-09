package eventoapp.domain.services;

import eventoapp.domain.dto.EventDTO;
import eventoapp.domain.dto.EventTicketListDTO;
import eventoapp.domain.dto.EventUpdateDTO;
import eventoapp.domain.dto.TicketDTO;
import eventoapp.domain.entities.Event;
import eventoapp.domain.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface EventService {

    Event insertEvent(Event event);

    Page<EventDTO> getEvents(PageRequest pageRequest, String name, String description, String startDate);

    EventDTO getEventById(Long id);

    void deleteEvent(Long id);

    EventDTO updateEvent(Long id, EventUpdateDTO eventUpdateDTO);

    EventTicketListDTO getEventTicketDTO(Long id);

    EventDTO connectPlaceInEvent(Long idEvent, Long idPlace);

    Ticket validateTicketAttendee(Long idEvent, TicketDTO ticketDTO);

    List<Event> getEventsByPlace(Long id);

    void devolutionTicket(Long idEvent, TicketDTO ticketDTO);

}