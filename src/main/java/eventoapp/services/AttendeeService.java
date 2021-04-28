package eventoapp.services;

import java.util.List;

import org.springframework.data.domain.Page;

import eventoapp.dto.AttendeeDTO;
import eventoapp.models.Attendee;


public interface AttendeeService {
       
    public Page<AttendeeDTO> getAttendees();

    public AttendeeDTO getAttendeeById(Long id);

    public List<AttendeeDTO> toDTOList(List<Attendee> Attendees);

    public void deleteAttendee(Long id);

    public Attendee insertAttendee(Attendee Attendee);

    public AttendeeDTO updateEvent(Long id, AttendeeDTO AttendeeDTO);
}