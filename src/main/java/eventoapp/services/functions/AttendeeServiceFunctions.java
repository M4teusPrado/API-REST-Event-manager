// package eventoapp.services.functions;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import javax.persistence.EntityNotFoundException;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Service;
// import org.springframework.web.server.ResponseStatusException;

// import eventoapp.dto.AttendeeDTO;
// import eventoapp.models.Attendee;
// import eventoapp.repositories.AttendeeRepository;
// import eventoapp.services.AttendeeService;

// @Service
// public class AttendeeServiceFunctions implements AttendeeService {

//     @Autowired
//     private AttendeeRepository attendeeRepository;

//     @Override
//     public List<AttendeeDTO> getAttendees() {
//         List<Attendee> attendees = attendeeRepository.findAll();
//         return toDTOList(attendees);
//     }

//     @Override
//     public AttendeeDTO getAttendeeById(Long id) {
        
//         Optional<Attendee> op = attendeeRepository.findById(id);
        
//         Attendee attendee = op.orElseThrow( () -> new ResponseStatusException( 
//             HttpStatus.NOT_FOUND, "Participante não encontrado"));
//         return new AttendeeDTO(attendee);
//     }

//     @Override
//     public void deleteAttendee(Long id) {
//         getAttendeeById(id);
//         attendeeRepository.deleteById(id);
//     }

//     @Override
//     public Attendee insertAttendee(AttendeeDTO attendeeDTO) {
//         Attendee attendee = new Attendee();
//         attendeeDTOtoAttendee(attendee, attendeeDTO);
//         return attendeeRepository.save(attendee);
//     }

//     @Override
//     public AttendeeDTO updateEvent(Long id, AttendeeDTO attendeeDTO) {

//         try {
//             Attendee attendee = attendeeRepository.getOne(id);
//             attendeeDTOtoAttendee(attendee, attendeeDTO);
//             attendee = attendeeRepository.save(attendee);
//             return new AttendeeDTO(attendee);
//         } 
//         catch(EntityNotFoundException ex){
//             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante não encontrado");
//         }
//     }

    
//     @Override
//     public void attendeeDTOtoAttendee(Attendee attendee, AttendeeDTO attendeeDTO) {
//         attendee.setName(attendeeDTO.getName());
//         attendee.setEmail(attendeeDTO.getEmail());
//         attendee.setBalance(attendeeDTO.getBalance());
//     }


//     @Override
//     public List<AttendeeDTO> toDTOList(List<Attendee> attendees) {
//         List<AttendeeDTO> attendeesDTO = new ArrayList<AttendeeDTO>();

//         for (Attendee attendee : attendees) {
//             AttendeeDTO attendeeDTO = new AttendeeDTO(attendee);
//             attendeesDTO.add(attendeeDTO);
//         }
//         return attendeesDTO;
//     }

// }
