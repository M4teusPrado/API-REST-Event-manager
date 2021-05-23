package eventoapp.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name="USER_ID")
public class Admin extends BaseUser {
    
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "admin")
    @Setter(AccessLevel.NONE)
    private List<Event> events = new ArrayList<>();

    public void addEvent(Event event) {
        this.events.add(event); 
    }

    public Admin() { }

    public Admin(Long id, String name, String email, String phoneNumber) {
        super(id, name, email);
        this.phoneNumber = phoneNumber;
    }
}