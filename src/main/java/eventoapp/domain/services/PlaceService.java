package eventoapp.domain.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import eventoapp.domain.dto.PlaceDTO;
import eventoapp.domain.dto.PlaceGetDTO;
import eventoapp.domain.entities.Place;

public interface PlaceService {

    public Page<PlaceGetDTO> getPlaces(PageRequest pageRequest);

    public PlaceGetDTO getPlaceById(Long id);

    public List<PlaceGetDTO> toDTOList(List<Place> places);

    public void deletePlace(Long id);

    public Place insertPlace(PlaceDTO placeDTO);

    public PlaceDTO updateEvent(Long id, PlaceDTO placeDTO);

    public void placeDTOtoPlace(Place place, PlaceDTO placeDTO);
}
