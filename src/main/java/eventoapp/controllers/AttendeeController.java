package eventoapp.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import eventoapp.domain.dto.AttendeeDTO;
import eventoapp.domain.dto.AttendeeGetDTO;
import eventoapp.domain.entities.Attendee;
import eventoapp.domain.services.AttendeeService;

@RestController
@RequestMapping("/attendees")
public class AttendeeController {

    @Autowired
    private AttendeeService attendeeService;

    @GetMapping()
    public ResponseEntity<Page<AttendeeGetDTO>> getAttendees(
        @RequestParam(value = "page",           defaultValue = "0")                 Integer page,
        @RequestParam(value = "linesPerPage",   defaultValue = "6")                 Integer linesPerPage,
        @RequestParam(value = "direction",      defaultValue = "ASC")               String  direction,
        @RequestParam(value = "orderBy",        defaultValue = "id")                String  orderBy
    ){
        PageRequest pageRequest = PageRequest.of(
                                            page, 
                                            linesPerPage, 
                                            Direction.valueOf(direction), 
                                            orderBy);
        Page<AttendeeGetDTO> listDTO = attendeeService.getAttendees(pageRequest);
        return ResponseEntity.ok(listDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendeeGetDTO> getAttendeeById(@PathVariable Long id ) {
        return ResponseEntity.ok().body(attendeeService.getAttendeeById(id));
    }

    @PostMapping()
    public ResponseEntity<Attendee> insertAttendee(@RequestBody @Valid AttendeeDTO AttendeeDTO)
    {
        Attendee aux = attendeeService.insertAttendee(AttendeeDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(aux.getId()).toUri();
        return ResponseEntity.created(uri).body(aux);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAttendee(@PathVariable Long id) {
        attendeeService.deleteAttendee(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("{id}") 
    public ResponseEntity<AttendeeDTO> updateAttendee(@PathVariable Long id, @RequestBody AttendeeDTO AttendeeDTO)
    {
        AttendeeDTO dto = attendeeService.updateEvent(id, AttendeeDTO);
		return ResponseEntity.ok().body(dto);
    }

}
