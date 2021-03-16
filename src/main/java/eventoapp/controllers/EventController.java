package eventoapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
