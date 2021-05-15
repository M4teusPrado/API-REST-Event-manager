package eventoapp.dto;

import eventoapp.models.Place;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDTO {
    
    private String name; 
    private String address;

    public PlaceDTO() {}

    public PlaceDTO(Place place) {
        this.name = place.getName();
        this.address = place.getAddress();
    }

}
