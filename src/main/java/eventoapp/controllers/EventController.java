package eventoapp.controllers;


import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import eventoapp.dto.EventDTO;
import eventoapp.models.Event;
import eventoapp.services.EventService;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping  
    public ResponseEntity<Page<EventDTO>> getEvents(
        @RequestParam(value = "page",           defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage",   defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction",      defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy",        defaultValue = "id") String orderBy,
        @RequestParam(value = "name",           defaultValue = "") String name,
        @RequestParam(value = "description",    defaultValue = "") String description,
        @RequestParam(value = "startDate",      defaultValue = "0001-01-01") String startDate
    ){

        PageRequest pageRequest = PageRequest.of(
                                                page, 
                                                linesPerPage,
                                                Direction.valueOf(direction),
                                                orderBy
                                                );
        
        Page<EventDTO> list =  eventService.getEvents(
                                                pageRequest,
                                                name,
                                                description,
                                                startDate
                                                );
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        EventDTO eventDTO =  eventService.getEventById(id);
        return ResponseEntity.ok(eventDTO);
    }

    @PostMapping()
    public ResponseEntity<Event> insertEvent(@RequestBody Event event)
    {
        Event aux = eventService.insertEvent(event);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(aux.getId()).toUri();
        return ResponseEntity.created(uri).body(aux);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    

}