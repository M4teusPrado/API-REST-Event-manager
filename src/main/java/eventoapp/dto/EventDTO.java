package eventoapp.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import eventoapp.models.Event;
import eventoapp.models.Place;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO {
    
    private String name;
    private String description;
    private List<Place> places = new ArrayList<>();
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String emailContact;
    private Long amountFreeTickets;
    private Long amountPayedTickets;
    private Double priceTickets;

    public EventDTO(Event event) {
        this.name = event.getName();
        this.description = event.getDescription();
        this.places = event.getPlaces();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
        this.emailContact = event.getEmailContact();
        this.amountFreeTickets = event.getAmountFreeTickets();
        this.amountPayedTickets = event.getAmountPayedTickets();
        this.priceTickets = event.getPriceTickets();
    }
}
