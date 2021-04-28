package eventoapp.services.functions;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import eventoapp.dto.AttendeeDTO;
import eventoapp.models.Attendee;
import eventoapp.repositories.AttendeeRepository;
import eventoapp.services.AttendeeService;

@Service
public class AttendeeServiceFunctions implements AttendeeService {

    @Autowired
    private AttendeeRepository AttendeeRepository;

    @Override
    public Page<AttendeeDTO> getAttendees() {
        return null;
    }

    @Override
    public AttendeeDTO getAttendeeById(Long id) {
        
        Optional<Attendee> op = AttendeeRepository.findById(id);
        
        Attendee Attendee = op.orElseThrow( () -> new ResponseStatusException( 
            HttpStatus.NOT_FOUND, "Participante não encontrado"));
        return new AttendeeDTO(Attendee);
    }

    @Override
    public List<AttendeeDTO> toDTOList(List<Attendee> Attendees) {
        return null;
    }

    @Override
    public void deleteAttendee(Long id) {
        getAttendeeById(id);
        AttendeeRepository.deleteById(id);
    }

    @Override
    public Attendee insertAttendee(Attendee Attendee) {
        return AttendeeRepository.save(Attendee);
    }

    @Override
    public AttendeeDTO updateEvent(Long id, AttendeeDTO AttendeeDTO) {

        try {
            Attendee Attendee = AttendeeRepository.getOne(id);

            Attendee.setName(AttendeeDTO.getName());
            Attendee.setEmail(AttendeeDTO.getEmail());
            Attendee.setBalance(AttendeeDTO.getBalance());
    
            Attendee = AttendeeRepository.save(Attendee);
            return new AttendeeDTO(Attendee);
        } 
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendeeistrador não encontrado");
        }
    }

}
