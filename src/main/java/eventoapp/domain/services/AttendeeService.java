package eventoapp.domain.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import eventoapp.domain.dto.AttendeeDTO;
import eventoapp.domain.dto.AttendeeGetDTO;
import eventoapp.domain.entities.Attendee;


public interface AttendeeService {

    public Attendee insertAttendee(AttendeeDTO AttendeeDTO);
    
    public Page<AttendeeGetDTO> getAttendees(PageRequest pageRequest);

    public AttendeeGetDTO getAttendeeById(Long id);

    public List<AttendeeGetDTO> toDTOList(List<Attendee> Attendees);

    public void attendeeDTOtoAttendee(Attendee attendee, AttendeeDTO attendeeDTO);

    public void deleteAttendee(Long id);


    public AttendeeDTO updateEvent(Long id, AttendeeDTO AttendeeDTO);
}