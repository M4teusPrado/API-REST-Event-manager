package eventoapp.services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import eventoapp.dto.EventDTO;
import eventoapp.models.Event;

public interface EventService {
    
    // public Page<EventDTO> getEvents(
    //     PageRequest pageRequest, 
    //     String name, String place, String description, String startDate );

   // public EventDTO getEventById(Long id);

    // public List<EventDTO> toDTOList(List<Event> events);

    // public void deleteEvent(Long id);

    public Event insertEvent(Event event);

    public void verifyDateAndTime(LocalDate startDate, LocalDate endDate, LocalTime startTime,LocalTime endTime);

    public EventDTO getEventById(Long id);

    public void deleteEvent(Long id);

    public Page<EventDTO> getEvents(PageRequest pageRequest, String name, String description, String startDate);

    // public EventDTO updateEvent(Long id, EventUpdateDTO eventUpdateDTO);
}