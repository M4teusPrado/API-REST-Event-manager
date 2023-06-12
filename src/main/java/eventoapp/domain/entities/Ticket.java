package eventoapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eventoapp.domain.entities.enums.TicketType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Setter
@Getter
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private TicketType type;
    private Instant date;
    private Double price;

    @JsonIgnore()
    @ManyToOne()
    private Attendee attendee;

    @ManyToOne()
    private Event event;

    public Ticket() {
    }

    public Ticket(TicketType type, Instant date, Double price, Event event, Attendee attendee) {
        this.type = type;
        this.date = date;
        this.price = price;
        this.event = event;
        this.attendee = attendee;
    }

    public Ticket(Event event, Attendee attendee, TicketType ticketType) {
        this.attendee = attendee;
        this.date = Instant.now();
        this.event = event;
        this.type = ticketType;

        if (ticketType.equals(TicketType.PAGO)) {
            this.price = event.getPriceTickets();
        } else {
            this.price = 0.0;
        }
    }
}
