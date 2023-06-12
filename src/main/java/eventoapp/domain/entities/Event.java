package eventoapp.domain.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Event implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY) 
    private Long id;
    
    private String      name;
    private String      description;
    private LocalDate   startDate;
    private LocalDate   endDate;
    private LocalTime   startTime;
    private LocalTime   endTime;
    private String      emailContact;
    private Long        amountFreeTickets = 0L;
    private Long        amountPayedTickets = 0L;
    private Long        amountFreeTicketsSold   = 0L;
    private Long        amountPayedTicketsSold  = 0L;
    private Double      priceTickets;

    @ManyToMany()
    @JoinTable(
        name="PLACE_EVENT",
        joinColumns =  @JoinColumn(name="EVENT_ID"),
        inverseJoinColumns = @JoinColumn(name="PLACE_ID")
    )
    @Setter(AccessLevel.NONE)
    private List<Place> places = new ArrayList<Place>();
 

    @ManyToOne()
    @JoinColumn(name="ADMIN_USER_ID")
    private Admin admin;

    @JsonIgnore
    @OneToMany()
    @JoinColumn(name ="EVENT_ID")
    private List<Ticket> tickets = new ArrayList<>();

    public void initializerVariable(){
        this.amountFreeTicketsSold = 0L;
        this.amountPayedTicketsSold = 0L;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public void addPlace(Place place) {
        this.places.add(place);
    }

    public List<Place> getPlaces( ) {
        return places;
    }

    public Boolean getPlace(Long id) {
        for (Place p : places) {
            if(p.getId() == id) {
                return true;
            }
        }
        return false; 
    }

    public void removeTicket(Long id) {
        this.tickets.removeIf( t -> t.getId() == id);
    }
}