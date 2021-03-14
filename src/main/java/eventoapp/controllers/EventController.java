package eventoapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<EventDTO> getEvents() {
        return eventService.getEvents();
    }
}
