package eventoapp.eventoapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Eventos")
public class EventController {

    @GetMapping  
    public String getEvents() {
        return "Hello World";
    }
}
