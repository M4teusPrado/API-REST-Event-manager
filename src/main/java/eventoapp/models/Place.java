package eventoapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Place implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY) 
    private Long id;                
    private String name; 
    private String address;

    @JsonIgnore
    @ManyToMany()
    @JoinTable(
        name="PLACE_EVENT",
        joinColumns =  @JoinColumn(name="PLACE_ID"),
        inverseJoinColumns = @JoinColumn(name="EVENT_ID")
    )
    @Setter(AccessLevel.NONE)
    private List<Event> events = new ArrayList<>();

    public void addEvent(Event event) {
        this.events.add(event); 
    }

    public Place() { }

    public Place(String name, String address) {
        this.name = name;
        this.address = address;
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
        Place other = (Place) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}