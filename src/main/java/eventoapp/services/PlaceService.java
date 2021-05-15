package eventoapp.services;

import java.util.List;

import eventoapp.dto.PlaceDTO;
import eventoapp.dto.PlaceGetDTO;
import eventoapp.models.Place;

public interface PlaceService {

    public List<PlaceGetDTO> getPlaces();

    public PlaceGetDTO getPlaceById(Long id);

    public List<PlaceGetDTO> toDTOList(List<Place> places);

    public void deletePlace(Long id);

    public Place insertPlace(PlaceDTO placeDTO);

    public PlaceDTO updateEvent(Long id, PlaceDTO placeDTO);

    public void placeDTOtoPlace(Place place, PlaceDTO placeDTO);
}
