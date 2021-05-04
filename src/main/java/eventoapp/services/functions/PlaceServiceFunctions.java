package eventoapp.services.functions;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import eventoapp.dto.PlaceDTO;
import eventoapp.models.Place;
import eventoapp.repositories.PlaceRepository;
import eventoapp.services.PlaceService;

@Service
public class PlaceServiceFunctions implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public List<PlaceDTO> getPlaces() {
        List<Place> places = placeRepository.findAll();
        return toDTOList(places);
    }

    @Override
    public PlaceDTO getPlaceById(Long id) {
        Optional<Place> op = placeRepository.findById(id);
        Place place = op.orElseThrow( () -> new ResponseStatusException( 
            HttpStatus.NOT_FOUND, "Local não encontrado"));
        return new PlaceDTO(place);
    }

    @Override
    public List<PlaceDTO> toDTOList(List<Place> places) {
        return null;
    }

    @Override
    public void deletePlace(Long id) {
        getPlaceById(id);
        placeRepository.deleteById(id);
    }

    @Override
    public Place insertPlace(PlaceDTO placeDTO) {
        Place place = new Place();
        placeDTOtoPlace(place, placeDTO);
        return placeRepository.save(place);
    }

    @Override
    public PlaceDTO updateEvent(Long id, PlaceDTO placeDTO) {
        return null;
    }

    @Override
    public void placeDTOtoPlace(Place place, PlaceDTO placeDTO) {
        place.setName(placeDTO.getName());
        place.setAddress(placeDTO.getAddress());
    }
}