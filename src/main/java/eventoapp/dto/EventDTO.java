package eventoapp.dto;

import java.time.LocalDate;

import eventoapp.models.Event;

public class EventDTO {
    
    private String name;
    private String description;
    private String place;
    private LocalDate startDate;
    private LocalDate endDate;
    private String emailContact;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public String getEmailContact() {
        return emailContact;
    }
    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public EventDTO(Event event) {
        this.name = event.getName();
        this.description = event.getDescription();
        this.place = event.getPlace();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.emailContact = event.getEmailContact();
    }
}
