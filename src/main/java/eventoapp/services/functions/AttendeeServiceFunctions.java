package eventoapp.services.functions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import eventoapp.dto.AttendeeDTO;
import eventoapp.dto.AttendeeGetDTO;
import eventoapp.models.Attendee;
import eventoapp.repositories.AttendeeRepository;
import eventoapp.services.AttendeeService;

@Service
public class AttendeeServiceFunctions implements AttendeeService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Override
    public Page<AttendeeGetDTO> getAttendees(PageRequest pageRequest) {
        Page<Attendee> attendees = attendeeRepository.find(pageRequest);
        return attendees.map(attendee -> new AttendeeGetDTO(attendee));
    }

    @Override
    public AttendeeGetDTO getAttendeeById(Long id) {
        try {
            Attendee attendee = attendeeRepository.findById(id).get();
            return new AttendeeGetDTO(attendee);
        } catch (Exception e) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Participante não encontrado");
        }   
    }

    @Override
    public void deleteAttendee(Long id) {
        getAttendeeById(id);
        attendeeRepository.deleteById(id);
    }

    @Override
    public Attendee insertAttendee(AttendeeDTO attendeeDTO) {
        Attendee attendee = new Attendee();
        getAttendeeByEmail(attendeeDTO);
        attendeeDTOtoAttendee(attendee, attendeeDTO);
        return attendeeRepository.save(attendee);
    }

    private void getAttendeeByEmail(AttendeeDTO attendeeDTO) {
        List<Attendee> attendees = attendeeRepository.findAttendeeByEmail(attendeeDTO.getEmail());

        if(!attendees.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email ja cadastrado");
    }

    @Override
    public AttendeeDTO updateEvent(Long id, AttendeeDTO attendeeDTO) {

        try {
            getAttendeeByEmail(attendeeDTO);
            Attendee attendee = attendeeRepository.getOne(id);
            attendeeDTOtoAttendee(attendee, attendeeDTO);
            attendee = attendeeRepository.save(attendee);
            return new AttendeeDTO(attendee);
        } 
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante não encontrado");
        }
    }

    
    @Override
    public void attendeeDTOtoAttendee(Attendee attendee, AttendeeDTO attendeeDTO) {
        attendee.setName(attendeeDTO.getName());
        attendee.setEmail(attendeeDTO.getEmail());
        attendee.setBalance(attendeeDTO.getBalance());
    }


    @Override
    public List<AttendeeGetDTO> toDTOList(List<Attendee> attendees) {
        List<AttendeeGetDTO> attendeesDTO = new ArrayList<AttendeeGetDTO>();

        for (Attendee attendee : attendees) {
            AttendeeGetDTO attendeeDTO = new AttendeeGetDTO(attendee);
            attendeesDTO.add(attendeeDTO);
        }
        return attendeesDTO;
    }

}
