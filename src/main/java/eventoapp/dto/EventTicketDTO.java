package eventoapp.dto;

import eventoapp.models.Event;
import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class EventTicketDTO {
    private Long totalPayedTickets;
    private Long totalFreeTickets;
    private Long totalPayedTicketsSold;
    private Long totalFreeTicketsSold;

    public EventTicketDTO(EventDTO event) {
        this.totalPayedTickets       = event.getAmountPayedTickets();
        this.totalFreeTickets        = event.getAmountFreeTickets();
        /*this.balance    = attendee.getBalance();
        this.tickets    = attendee.getTickets();*/
    }
}
