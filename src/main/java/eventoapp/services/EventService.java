package eventoapp.services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import eventoapp.dto.EventDTO;
import eventoapp.dto.EventTicketDTO;
import eventoapp.dto.EventUpdateDTO;
import eventoapp.models.Event;

public interface EventService {

    public Event insertEvent(Event event);
    
    public Page<EventDTO> getEvents(PageRequest pageRequest, String name, String description, String startDate);
    
    public void verifyDateAndTime(LocalDate startDate, LocalDate endDate, LocalTime startTime,LocalTime endTime);

    public EventDTO getEventById(Long id);

    public void deleteEvent(Long id);


    public EventDTO updateEvent(Long id, EventUpdateDTO eventUpdateDTO);

    public EventTicketDTO getEventTicketDTO(Long id);

    public EventDTO connectPlaceInEvent(Long idEvent, Long idPlace);

    // public List<EventDTO> toDTOList(List<Event> events);
}