package eventoapp.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import eventoapp.dto.PlaceDTO;
import eventoapp.dto.PlaceGetDTO;
import eventoapp.models.Place;
import eventoapp.services.PlaceService;

@RestController
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping()
    public Page<PlaceGetDTO> getPlaces(
        @RequestParam(value = "page",           defaultValue = "0")                 Integer page,
        @RequestParam(value = "linesPerPage",   defaultValue = "6")                 Integer linesPerPage,
        @RequestParam(value = "direction",      defaultValue = "ASC")               String  direction,
        @RequestParam(value = "orderBy",        defaultValue = "id")                String  orderBy
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        Page<PlaceGetDTO> listDTO = placeService.getPlaces(pageRequest);
        return listDTO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceGetDTO> getPlaceById(@PathVariable Long id ) {
        return ResponseEntity.ok(placeService.getPlaceById(id));
    }

    @PostMapping()
    public ResponseEntity<Place> insertPlace(@RequestBody PlaceDTO placeDTO)
    {
        Place aux = placeService.insertPlace(placeDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(aux.getId()).toUri();
        return ResponseEntity.created(uri).body(aux);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        placeService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("{id}") 
    public ResponseEntity<PlaceDTO> updatePlace(@PathVariable Long id, @RequestBody PlaceDTO placeDTO)
    {
        PlaceDTO dto = placeService.updateEvent(id, placeDTO);
		return ResponseEntity.ok().body(dto);
    }
}
