package eventoapp.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import eventoapp.dto.AttendeeDTO;
import eventoapp.dto.AttendeeGetDTO;
import eventoapp.models.Attendee;


public interface AttendeeService {

    public Page<AttendeeGetDTO> getAttendees(PageRequest pageRequest);

    public AttendeeGetDTO getAttendeeById(Long id);

    public List<AttendeeGetDTO> toDTOList(List<Attendee> Attendees);

    public void attendeeDTOtoAttendee(Attendee attendee, AttendeeDTO attendeeDTO);

    public void deleteAttendee(Long id);

    public Attendee insertAttendee(AttendeeDTO AttendeeDTO);

    public AttendeeDTO updateEvent(Long id, AttendeeDTO AttendeeDTO);
}