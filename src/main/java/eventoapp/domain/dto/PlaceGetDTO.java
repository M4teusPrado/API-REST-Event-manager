package eventoapp.domain.dto;

import java.util.List;

import eventoapp.domain.entities.Event;
import eventoapp.domain.entities.Place;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceGetDTO {

    private Long        id;
    
    private String      name; 
    private String      address;
    private List<Event> events;

    public PlaceGetDTO() {}

	public PlaceGetDTO(Place place) {
        this.id         = place.getId();
        this.name       = place.getName();
        this.address    = place.getAddress();
        this.events     = place.getEvents();
	}
}
