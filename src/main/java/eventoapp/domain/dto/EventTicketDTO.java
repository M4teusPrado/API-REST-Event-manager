package eventoapp.domain.dto;

import eventoapp.domain.entities.Event;
import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class EventTicketDTO {
    private Long totalPayedTickets;
    private Long totalFreeTickets;
    private Long totalPayedTicketsSold;
    private Long totalFreeTicketsSold;

    public EventTicketDTO() {
    }
    
    public EventTicketDTO(Event eventAux) {
        this.totalPayedTickets          = eventAux.getAmountPayedTickets();
        this.totalFreeTickets           = eventAux.getAmountFreeTickets();
        this.totalPayedTicketsSold      = eventAux.getAmountPayedTicketsSold();
        this.totalFreeTicketsSold       = eventAux.getAmountFreeTicketsSold();
    }
}
