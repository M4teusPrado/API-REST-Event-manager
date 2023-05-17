package eventoapp.models.factorys;

import eventoapp.models.Attendee;
import eventoapp.models.Event;
import eventoapp.models.Ticket;
import eventoapp.models.enums.TicketType;

public class TicketFactory {

    public static Ticket createTicket(Event event, Attendee attendee, TicketType typeTicket) {
        return new Ticket(event, attendee, typeTicket);
    }
}