package eventoapp.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import eventoapp.dto.EventDTO;
import eventoapp.dto.EventTicketListDTO;
import eventoapp.dto.EventUpdateDTO;
import eventoapp.dto.TicketDTO;
import eventoapp.models.Event;
import eventoapp.models.Ticket;

public interface EventService {

    public Event insertEvent(Event event);
    
    public Page<EventDTO> getEvents(PageRequest pageRequest, String name, String description, String startDate);
    
    public void verifyDateAndTime(LocalDate startDate, LocalDate endDate, LocalTime startTime,LocalTime endTime);

    public EventDTO getEventById(Long id);

    public void deleteEvent(Long id);

    public EventDTO updateEvent(Long id, EventUpdateDTO eventUpdateDTO);

    public EventTicketListDTO getEventTicketDTO(Long id);

    public EventDTO connectPlaceInEvent(Long idEvent, Long idPlace);

    public Ticket validateTicketAttendee(Long idEvent, TicketDTO ticketDTO);

    public List<Event> getEventsByPlace(Long id);

    public void devolutionTicket(Long idEvent, TicketDTO ticketDTO);
    
    // public List<EventDTO> toDTOList(List<Event> events);
}