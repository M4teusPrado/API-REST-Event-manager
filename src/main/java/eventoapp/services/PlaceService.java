package eventoapp.services;

import java.util.List;

import eventoapp.dto.PlaceDTO;
import eventoapp.models.Place;

public interface PlaceService {

    public List<PlaceDTO> getPlaces();

    public PlaceDTO getPlaceById(Long id);

    public List<PlaceDTO> toDTOList(List<Place> places);

    public void deletePlace(Long id);

    public Place insertPlace(PlaceDTO placeDTO);

    public PlaceDTO updateEvent(Long id, PlaceDTO placeDTO);

    public void placeDTOtoPlace(Place place, PlaceDTO placeDTO);
}
