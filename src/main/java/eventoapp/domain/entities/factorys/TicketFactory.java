package eventoapp.domain.entities.factorys;

import eventoapp.domain.entities.Attendee;
import eventoapp.domain.entities.Event;
import eventoapp.domain.entities.Ticket;
import eventoapp.domain.entities.enums.TicketType;

public class TicketFactory {

    public static Ticket createTicket(Event event, Attendee attendee, TicketType typeTicket) {
        return new Ticket(event, attendee, typeTicket);
    }
}