package eventoapp.models;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import eventoapp.models.enums.TicketType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Ticket implements Serializable {

    @Id
    private Long id;
    
    @Enumerated(value = EnumType.STRING)
    private TicketType type;
    private Instant date;
    private Double price;
    private Attendee attendee;
    private Event event;

    public Ticket() { }

    public Ticket(TicketType type, Instant date, Double price, Event event, Attendee attendee) {
        this.type = type;
        this.date = date;
        this.price = price;
        this.event = event;
        this.attendee = attendee;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ticket other = (Ticket) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
    
}
