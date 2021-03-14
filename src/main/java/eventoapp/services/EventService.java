package eventoapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public EventDTO getEventById(Long id) {
        Optional<Event> op = eventRepository.findById(id);
        Event event = op.orElseThrow( () -> new ResponseStatusException( 
                                                HttpStatus.NOT_FOUND, "Evento não encontrado"));
        return new EventDTO(event);
    }

    public List<EventDTO> toDTOList(List<Event> events) {

        List<EventDTO> eventsDTO = new ArrayList<>();

        for(Event e : events) {
            EventDTO eventDTO = new EventDTO(e);
            eventsDTO.add(eventDTO);
        }

        return eventsDTO;
    }
}
