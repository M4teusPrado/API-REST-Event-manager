package eventoapp.services.functions;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import eventoapp.models.Event;
import eventoapp.models.Place;
import eventoapp.repositories.AdminRepository;
import eventoapp.repositories.EventRepository;
import eventoapp.repositories.PlaceRepository;
import eventoapp.services.AdminService;
import eventoapp.services.EventService;
import eventoapp.services.PlaceService;


@Service
public class EventServiceFunctions implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AdminService adminService;

    @Autowired 
    private AdminRepository adminRepository;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public Event insertEvent(Event event) {

        verifyDateAndTime(
            event.getStartDate(),
            event.getEndDate(),
            event.getStartTime(),
            event.getEndTime()
        );

        //Verifica se a quantidade de ingresso é >= 0
        verifyAmountOfTickets(
                event.getAmountFreeTickets(), 
                event.getAmountPayedTickets()
        );

         //Verifica se o valor é >= 0
        verifyPriceOfPrice(event.getPriceTickets());

        //Verifica se o admin existe
        adminService.getAdminById(event.getAdmin().getId());
        event.setAdmin(adminRepository.getOne(event.getAdmin().getId()));

        //Valida todos os lugares enviados pelo usuario
        for (Place place : event.getPlaces()) 
            placeService.getPlaceById(place.getId());

        for (Place place : event.getPlaces()) 
            event.addPlace(placeRepository.getOne(place.getId()));
        
        return eventRepository.save(event);
    }

    private void verifyPriceOfPrice(Double priceTickets) {
        if(priceTickets < 0.0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"preço do ingresso invalido");
    }

    private void verifyAmountOfTickets(Long freeTickets, Long payedTickets) {
        if(freeTickets < 0  || payedTickets < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Quantidade de ingresso invalidos");
    }

    @Override
    public void verifyDateAndTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        if(startDate.isAfter(endDate))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Data incoerente");

        if(startDate.isEqual(endDate)) 
            if(startTime.compareTo(endTime) == 1)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Horario incoerente");
    }
}
