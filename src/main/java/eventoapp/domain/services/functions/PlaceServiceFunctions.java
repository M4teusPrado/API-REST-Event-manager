package eventoapp.domain.services.functions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import eventoapp.domain.dto.PlaceDTO;
import eventoapp.domain.dto.PlaceGetDTO;
import eventoapp.domain.entities.Place;
import eventoapp.domain.repositories.PlaceRepository;
import eventoapp.domain.services.EventService;
import eventoapp.domain.services.PlaceService;

@Service
public class PlaceServiceFunctions implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private EventService eventService;

    @Override
    public Page<PlaceGetDTO> getPlaces(PageRequest pageRequest) {
        Page<Place> places = placeRepository.findPlacePageable(pageRequest);
        return places.map(place -> new PlaceGetDTO(place));
    }

    @Override
    public PlaceGetDTO getPlaceById(Long id) {
        try {
            Place place = placeRepository.findById(id).get();
            return new PlaceGetDTO(place);
        } catch (Exception e) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Lugar não encontrado");
        }
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
        if(placeByEvent(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossivel apagar local, pos ja foi associado a um evento");
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

    private Boolean placeByEvent(Long id) {
        return eventService.getEventsByPlace(id).isEmpty();
    }

}