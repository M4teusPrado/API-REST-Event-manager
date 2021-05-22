package eventoapp.services.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import eventoapp.dto.PlaceDTO;
import eventoapp.dto.PlaceGetDTO;
import eventoapp.models.Place;
import eventoapp.repositories.PlaceRepository;
import eventoapp.services.PlaceService;

@Service
public class PlaceServiceFunctions implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public Page<PlaceGetDTO> getPlaces(PageRequest pageRequest) {
        Page<Place> places = placeRepository.findPlacePageable(pageRequest);
        return places.map(place -> new PlaceGetDTO(place));
    }

    @Override
    public PlaceGetDTO getPlaceById(Long id) {
        Optional<Place> op = placeRepository.findById(id);
        Place place = op.orElseThrow( () -> new ResponseStatusException( 
            HttpStatus.NOT_FOUND, "Local não encontrado"));
        return new PlaceGetDTO(place);
    }

    @Override
    public List<PlaceGetDTO> toDTOList(List<Place> places) {
        List<PlaceGetDTO> placesDTO = new ArrayList<PlaceGetDTO>();

        for (Place place : places) {
            PlaceGetDTO placeDTO = new PlaceGetDTO(place);
            placesDTO.add(placeDTO);
        }
        return placesDTO;
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
        try {
            Place place = placeRepository.getOne(id);
            placeDTOtoPlace(place, placeDTO);
            place = placeRepository.save(place);
            return new  PlaceDTO(place);
        } 
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante não encontrado");
        }
    }

    @Override
    public void placeDTOtoPlace(Place place, PlaceDTO placeDTO) {
        place.setName(placeDTO.getName());
        place.setAddress(placeDTO.getAddress());
    }
}