// package eventoapp.controllers;

// import java.net.URI;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// import eventoapp.dto.AttendeeDTO;
// import eventoapp.models.Attendee;
// import eventoapp.services.AttendeeService;

// @RestController
// @RequestMapping("/attendees")
// public class AttendeeController {

//     @Autowired
//     private AttendeeService attendeeService;

//     @GetMapping()
//     public List<AttendeeDTO> getAttendees() {
//         return attendeeService.getAttendees();
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<AttendeeDTO> getAttendeeById(@PathVariable Long id ) {
//         return ResponseEntity.ok(attendeeService.getAttendeeById(id));
//     }

//     @PostMapping()
//     public ResponseEntity<Attendee> insertAttendee(@RequestBody AttendeeDTO AttendeeDTO)
//     {
//         Attendee aux = attendeeService.insertAttendee(AttendeeDTO);
//         URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(aux.getId()).toUri();
//         return ResponseEntity.created(uri).body(aux);
//     }

//     @DeleteMapping("{id}")
//     public ResponseEntity<Void> deleteAttendee(@PathVariable Long id) {
//         attendeeService.deleteAttendee(id);
//         return ResponseEntity.noContent().build();
//     }
    
//     @PutMapping("{id}") 
//     public ResponseEntity<AttendeeDTO> updateAttendee(@PathVariable Long id, @RequestBody AttendeeDTO AttendeeDTO)
//     {
//         AttendeeDTO dto = attendeeService.updateEvent(id, AttendeeDTO);
// 		return ResponseEntity.ok().body(dto);
//     }

// }
