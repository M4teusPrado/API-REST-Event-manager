package eventoapp.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter()
@Getter
@PrimaryKeyJoinColumn(name="USER_ID")
public class Attendee extends BaseUser {

    private float balance;

    @OneToMany()
    @JoinColumn(name = "ATTENDEE_USER_ID")
    @Setter(AccessLevel.NONE)
    private List<Ticket> tickets = new ArrayList<>();

    public Attendee() { }

    public Attendee(Long id, String name, String email, float balance) {
        super(id, name, email);
        this.balance = balance;
    }

    public void addTicktes(Ticket e) {
        this.tickets.add(e);
    }

    public void removeTickte(Long id) {
        this.tickets.removeIf( t -> t.getId() == id);
    }
}
