package eventoapp.dto;

import eventoapp.models.Place;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDTO {
    
    private String name; 
    private String address;

    public PlaceDTO() { }

    public PlaceDTO(String name, String address) {
        this.name = name;
        this.address = address;
    }

	public PlaceDTO(Place place) {
	}
}
