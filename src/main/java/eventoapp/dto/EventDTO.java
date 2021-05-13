package eventoapp.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import eventoapp.models.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO {
    
    private String name;
    private String description;
    private String place;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String emailContact;

    public EventDTO(Event event) {
        this.name = event.getName();
        this.description = event.getDescription();
        //this.place = event.getPlace();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
        this.emailContact = event.getEmailContact();
    }
}
