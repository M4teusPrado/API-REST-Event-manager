package eventoapp.services.functions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import eventoapp.dto.EventDTO;
import eventoapp.dto.EventTicketDTO;
import eventoapp.dto.EventUpdateDTO;
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
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceService placeService;

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
        verifyPrice(event.getPriceTickets());

        //Verifica se o admin existe
        adminService.getAdminById(event.getAdmin().getId());
        event.setAdmin(adminRepository.getOne(event.getAdmin().getId()));    

        return eventRepository.save(event);
    }

    private void verifyPrice(Double priceTickets) {
        if(priceTickets < 0.0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"preço do ingresso invalido");
    }

    private void verifyAmountOfTickets(Long freeTickets, Long payedTickets) {
        if(freeTickets < 0 || payedTickets < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Quantidade de ingresso invalidos");
    }


    @Override
    public void verifyDateAndTime( LocalDate startDate, LocalDate endDate,LocalTime startTime, LocalTime endTime){
        if(startDate.isAfter(endDate))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Data incoerente");

        if(startDate.isEqual(endDate)) 
            if(startTime.compareTo(endTime) == 1)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Horario incoerente");
    }

    @Override
    public EventDTO getEventById(Long id) {
        try {
            Event event = eventRepository.findById(id).get();
            return new EventDTO(event);
        } catch (Exception e) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Evento não encontrado");
        }
    }

    @Override
    public void deleteEvent(Long id) {
        getEventById(id);
        eventRepository.deleteById(id);
    }

    @Override
    public Page<EventDTO> getEvents(
                                    PageRequest pageRequest, 
                                    String name, 
                                    String description, 
                                    String startDate
                                    ){
        
        LocalDate startDateAux = LocalDate.parse(startDate);
        
        Page<Event> events = eventRepository.find(
                                                pageRequest,
                                                name.trim(),
                                                description.trim(),
                                                startDateAux
                                                );
        return events.map( event -> new EventDTO(event));
    }

    @Override
    public EventDTO updateEvent(Long id, EventUpdateDTO eventUpdateDTO) {

        try{
            Event event = eventRepository.getOne(id);
            
            verifyPossibilityOfUpdate(event, LocalDate.now());
            
            verifyDateAndTime(
                eventUpdateDTO.getStartDate(),
                eventUpdateDTO.getEndDate(),
                eventUpdateDTO.getStartTime(),
                eventUpdateDTO.getEndTime()
            );

            verifyPrice(eventUpdateDTO.getPriceTickets());
 
            event.setName(eventUpdateDTO.getName());
            event.setDescription(eventUpdateDTO.getDescription());
            event.setStartDate(eventUpdateDTO.getStartDate());
            event.setEndDate(eventUpdateDTO.getEndDate());
            event.setStartTime(eventUpdateDTO.getEndTime());
            event.setEmailContact(eventUpdateDTO.getEmailContact());
            event.setPriceTickets(eventUpdateDTO.getPriceTickets());

            event = eventRepository.save(event);
            return new EventDTO(event);
          }
          catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado");
          }
    }

    private void verifyPossibilityOfUpdate(Event event, LocalDate today) {   
        if( event.getStartDate().isBefore(today) || 
            (event.getStartDate().equals(today)  && event.getStartTime().equals(LocalTime.now())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possivel atualizar evento");
    }

    public EventTicketDTO getEventTicketDTO(Long id){
        EventDTO        eventAux        =       getEventById(id);
        EventTicketDTO  eventTicketAux  = new   EventTicketDTO(eventAux);

        return eventTicketAux;
    }

    @Override
    public EventDTO connectPlaceInEvent(Long idEvent, Long idPlace) {

        getEventById(idEvent);
        Event event = eventRepository.findById(idEvent).get();

        placeService.getPlaceById(idPlace);
        Place place = placeRepository.findById(idPlace).get();

        verifyDateOfPlace(event, place);
        
        event.addPlace(place);
        event = eventRepository.save(event);

        return new EventDTO(event);
    }

    public void verifyDateOfPlace(Event event, Place place) {
        List<Event> events = getEventsByPlace(place);

        Boolean flag = true;

        if (events.size() == 0)
            return;

        for (Event e : events) {
            if  ((event.getStartDate().isAfter(e.getStartDate())    || (event.getStartDate().isEqual(e.getStartDate())))  &&
                ((event.getStartDate().isBefore(e.getEndDate()))    || (event.getStartDate().isEqual(e.getEndDate())))){
                    flag = false;
                    break;
            }

            if  ((event.getEndDate().isAfter(e.getStartDate())    || (event.getEndDate().isEqual(e.getStartDate())))  &&
                ((event.getEndDate().isBefore(e.getEndDate()))    || (event.getEndDate().isEqual(e.getEndDate())))){
                    flag = false;
                    break;
            }

            if (event.getStartDate().isBefore(e.getStartDate()) && event.getEndDate().isAfter(e.getEndDate())){
                flag = false;
                break;
            }
            // if(
            //     e.getStartDate().isBefore(event.getStartDate())   && 
            //     ( e.getEndDate().isAfter(event.getStartDate())    ||
            //       e.getEndDate().isBefore(event.getStartDate()) 
            //     )
            // ){
            //     flag = false;
            //     break;
            // }

            // if(
            //     ( e.getStartDate().isAfter(event.getStartDate())  ||
            //       e.getStartDate().equals(event.getStartDate()))   &&
            //     e.getEndDate().isBefore(event.getEndDate()) 
            // ){
            //     flag = false;
            //     break;
            // }

            // if(
            //     e.getStartDate().isEqual(event.getStartDate()) && 
            //     e.getEndDate().isEqual(event.getEndDate()) 
            // ){
            //     flag = false;
            //     break;
            // }
            
            // if(
            //     ( e.getStartDate().isBefore(event.getStartDate()) || 
            //       e.getStartDate().equals(event.getStartDate())
            //     )  
            //       && 
            //     ( e.getEndDate().isAfter(event.getEndDate()) || 
            //       e.getEndDate().equals(event.getEndDate())
            //     )
            // ){
            //     flag = false;
            //     break;
            // }

            // if(
            //     ( e.getStartDate().isAfter(event.getStartDate()) ||
            //       e.getStartDate().isEqual(event.getStartDate()) 
            //     ) 
            //     &&
            //     ( e.getEndDate().isBefore(event.getEndDate()) ||
            //       e.getEndDate().isEqual(event.getEndDate())
            //     )
            // ){
            //     flag = false;
            //     break;
            // }
        }

        if(!flag) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horario invalido");
        }
    }

    public List<Event> getEventsByPlace(Place place) {
        List<Event> events      = eventRepository.findAll();
        List<Event> eventsAux   = new ArrayList<>();

        for (Event e : events) 
            if(e.getPlace(place.getId())) 
                eventsAux.add(e);
        return eventsAux;
    }
}
