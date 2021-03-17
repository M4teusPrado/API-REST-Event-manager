package eventoapp.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import eventoapp.models.Event;
import eventoapp.dto.EventDTO;
import eventoapp.services.EventService;

@RestController
@RequestMapping("/eventos")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping  
    public ResponseEntity<List<EventDTO>> getEvents() {
        List<EventDTO> list =  eventService.getEvents();
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        EventDTO eventDTO =  eventService.getEventById(id);
        return ResponseEntity.ok(eventDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<Event> insertEvent(@RequestBody Event event)
    {
        eventService.insertEvent(event);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(event.getId()).toUri();
        return ResponseEntity.created(uri).body(event);
    }
}
