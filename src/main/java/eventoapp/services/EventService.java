package eventoapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventoapp.dto.EventDTO;
import eventoapp.models.Event;
import eventoapp.repositories.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    
    public List<EventDTO> getEvents() {
        List<Event> events = eventRepository.findAll();
        return toDTOList(events);
    }

    private List<EventDTO> toDTOList(List<Event> events) {

        List<EventDTO> eventsDTO = new ArrayList<>();

        for(Event e : events) {
            EventDTO eventDTO = new EventDTO(
                                            e.getName(),
                                            e.getPlace(),
                                            e.getDescription(),
                                            e.getStartDate(),
                                            e.getEndDate(),
                                            e.getEmailContact() 
                                            );
            eventsDTO.add(eventDTO);
        }

        return eventsDTO;
    }
}
