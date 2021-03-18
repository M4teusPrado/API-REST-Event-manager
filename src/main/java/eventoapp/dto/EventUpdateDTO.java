package eventoapp.dto;


import java.time.LocalDate;

import eventoapp.models.Event;


public class EventUpdateDTO {

    private String place;
    private LocalDate startDate;
    private LocalDate endDate;

   
    public EventUpdateDTO(Event event) {
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
}
