package eventoapp.domain.services.functions;

import eventoapp.domain.dto.*;
import eventoapp.domain.entities.Attendee;
import eventoapp.domain.entities.Event;
import eventoapp.domain.entities.Place;
import eventoapp.domain.entities.Ticket;
import eventoapp.domain.entities.enums.TicketType;
import eventoapp.domain.entities.factorys.TicketFactory;
import eventoapp.domain.repositories.AttendeeRepository;
import eventoapp.domain.repositories.EventRepository;
import eventoapp.domain.repositories.PlaceRepository;
import eventoapp.domain.repositories.TicketRepository;
import eventoapp.domain.services.AdminService;
import eventoapp.domain.services.AttendeeService;
import eventoapp.domain.services.EventService;
import eventoapp.domain.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class EventServiceFunctions implements EventService {

    @Autowired
    private final EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private final AdminService adminService;

    @Autowired
    private AttendeeService attendeeService;

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceService placeService;

    public EventServiceFunctions(EventRepository eventRepository, AdminService adminService) {
        this.eventRepository = eventRepository;
        this.adminService = adminService;
    }

    @Override
    public Event insertEvent(Event event) {

        verifyPossibilityOfInsert(event);
        checkIfEmailAlreadyRegistered(event.getEmailContact());

        validateEventDateTime(
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
        event.setAdmin(adminService.getAdminById(event.getAdmin().getId()));
        return eventRepository.save(event);
    }

    private void verifyPossibilityOfInsert(Event event) {
        if (event.getStartDate().isBefore(LocalDate.now()) ||
                (event.getStartDate().equals(LocalDate.now()) && event.getStartTime().equals(LocalTime.now())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possivel criar evento no passado");
    }

    private void checkIfEmailAlreadyRegistered(String email) {
        List<Event> events = eventRepository.findAttendeeByEmail(email);

        if (!events.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email ja cadastrado");
    }

    private void validateEventDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        if (startDate.isAfter(endDate))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data incoerente");

        if (startDate.isEqual(endDate))
            if (startTime.isAfter(endTime))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horario incoerente");
    }

    private void verifyAmountOfTickets(Long freeTickets, Long payedTickets) {
        if (freeTickets < 0 || payedTickets < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade de ingresso invalidos");
    }

    private void verifyPrice(Double priceTickets) {
        if (priceTickets < 0.0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "preço do ingresso invalido");
    }

    @Override
    public EventDTO getEventById(Long id) {
        try {
            Event event = eventRepository.findById(id).get();
            return new EventDTO(event);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado");
        }
    }

    @Override
    public void deleteEvent(Long id) {
        getEventById(id);
        Event event = eventRepository.findById(id).get();
        if (event.getAmountFreeTicketsSold() > 0 || event.getAmountPayedTicketsSold() > 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não apagar evento, pos ja foi realizado venda dos ingressos");
        eventRepository.deleteById(id);
    }

    @Override
    public Page<EventDTO> getEvents(
            PageRequest pageRequest,
            String name,
            String description,
            String startDate
    ) {

        LocalDate startDateAux = LocalDate.parse(startDate);

        Page<Event> events = eventRepository.find(
                pageRequest,
                name.trim(),
                description.trim(),
                startDateAux
        );
        return events.map(EventDTO::new);
    }

    @Override
    public EventDTO updateEvent(Long id, EventUpdateDTO eventUpdateDTO) {

        try {
            Event event = eventRepository.getOne(id);

            verifyPossibilityOfInsert(event);

            checkIfEmailAlreadyRegistered(eventUpdateDTO.getEmailContact());

            validateEventDateTime(
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
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado");
        }
    }

    public EventTicketListDTO getEventTicketDTO(Long id) {

        getEventById(id);
        Event eventAux = eventRepository.getOne(id);

        EventTicketDTO eventTicketAux = new EventTicketDTO(eventAux);
        List<TicketGetDTO> tickets = ticketsToDTO(eventAux.getTickets());
        return new EventTicketListDTO(tickets, eventTicketAux);
    }

    private List<TicketGetDTO> ticketsToDTO(List<Ticket> tickets) {
        List<TicketGetDTO> ticketsListDTO = new ArrayList<>();

        for (Ticket ticket : tickets) {
            TicketGetDTO ticketDTO = new TicketGetDTO(ticket);
            ticketsListDTO.add(ticketDTO);
        }

        return ticketsListDTO;
    }

    @Override
    public EventDTO connectPlaceInEvent(Long idEvent, Long idPlace) {

        getEventById(idEvent);
        Event event = eventRepository.findById(idEvent).get();

        placeService.getPlaceById(idPlace);
        Place place = placeRepository.findById(idPlace).get();

        verifyDateOfPlace(event, idPlace);

        event.addPlace(place);
        event = eventRepository.save(event);

        return new EventDTO(event);
    }

    public void verifyDateOfPlace(Event event, Long idPlace) {
        List<Event> events = getEventsByPlace(idPlace);
        for (Event e : events)
            if (!validDate(e, event))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horario invalido");
    }

    public List<Event> getEventsByPlace(Long id) {
        List<Event> events = eventRepository.findAll();
        return events.stream().filter(e -> e.getPlace(id)).collect(Collectors.toList());
    }

    public boolean validDate(Event e, Event event) {
        LocalDate startDate = event.getStartDate();
        LocalDate endDate = event.getEndDate();
        LocalTime startTime = event.getStartTime();
        LocalTime endTime = event.getEndTime();

        if (startDate.isAfter(e.getEndDate()) || endDate.isBefore(e.getStartDate())) {
            return true; // Intervalo de datas não se sobrepõe, é válido
        }

        if (startDate.isEqual(e.getEndDate()) && startTime.isAfter(e.getEndTime())) {
            return true; // Início do evento é após o término do evento `e`, é válido
        }

        if (endDate.isEqual(e.getStartDate()) && endTime.isBefore(e.getStartTime())) {
            return true; // Término do evento é antes do início do evento `e`, é válido
        }

        return false; // Casos de sobreposição de datas, é inválido
    }

    @Override
    public Ticket validateTicketAttendee(Long idEvent, TicketDTO ticketDTO) {

        getEventById(idEvent);
        attendeeService.getAttendeeById(ticketDTO.getIdAttendee());

        Event event = eventRepository.getOne(idEvent);
        Attendee attendee = attendeeRepository.getOne(ticketDTO.getIdAttendee());

        verifyAmountTicketsLeft(event, ticketDTO.getIdAttendee(), ticketDTO.getTypeTicket());
        verifyBalanceAttendee(attendee, event, ticketDTO.getTypeTicket());

        Ticket ticket = createTicket(event, attendee, ticketDTO.getTypeTicket());

        ticketRepository.save(ticket);
        attendeeRepository.save(attendee);
        eventRepository.save(event);

        return ticket;
    }

    private Ticket createTicket(Event event, Attendee attendee, TicketType ticketType) {
        Ticket ticket = TicketFactory.createTicket(event, attendee, ticketType);
        attendee.addTicktes(ticket);
        event.addTicket(ticket);
        return ticket;
    }

    private void verifyBalanceAttendee(Attendee attendee, Event event, TicketType typeTicket) {

        if (typeTicket.equals(TicketType.PAGO)) {

            if (attendee.getBalance() < event.getPriceTickets()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attendee não possui saldo suficiente");
            }
            attendee.setBalance(attendee.getBalance() - (convertToFloat(event.getPriceTickets())));
        }
    }

    private static Float convertToFloat(Double doubleValue) {
        return doubleValue == null ? null : doubleValue.floatValue();
    }

    public void verifyAmountTicketsLeft(Event event, Long idAttendee, TicketType typeTicket) {
        if (TicketType.GRATUITO == typeTicket) {
            if (event.getAmountFreeTickets().compareTo(event.getAmountFreeTicketsSold()) <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tickets grátuitos esgotados");
            }
            event.setAmountFreeTicketsSold(event.getAmountFreeTicketsSold() + 1L);
        } else if (TicketType.PAGO == typeTicket) {
            if (event.getAmountPayedTickets() <= event.getAmountPayedTicketsSold()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tickets pagos esgotados");
            }
            event.setAmountPayedTicketsSold(event.getAmountPayedTicketsSold() + 1L);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de ticket inválido");
        }
    }


    @Override
    public void devolutionTicket(Long idEvent, TicketDTO ticketDTO) {

        getEventById(idEvent);
        attendeeService.getAttendeeById(ticketDTO.getIdAttendee());

        Event event = eventRepository.getOne(idEvent);
        Attendee attendee = attendeeRepository.getOne(ticketDTO.getIdAttendee());

        Ticket ticket = verifyTicketExists(ticketDTO.getTypeTicket(), attendee, idEvent);

        devolutionBallanceToAttendee(attendee, ticket, ticketDTO.getTypeTicket());
        devolutionTicketToEvent(event, ticket, ticketDTO.getTypeTicket());

        eventRepository.save(event);
        attendeeRepository.save(attendee);
        ticketRepository.delete(ticket);
    }

    private Ticket verifyTicketExists(TicketType typeTicket, Attendee attendee, Long idEvent) {
        List<Ticket> attendeesTickets = attendee.getTickets();

        for (Ticket ticket : attendeesTickets) {
            if (Objects.equals(idEvent, ticket.getEvent().getId()) && typeTicket == ticket.getType()) {
                return ticket;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket não encontrado");
    }

    private void devolutionBallanceToAttendee(Attendee attendee, Ticket ticket, TicketType typeTicket) {
        attendee.removeTickte(ticket.getId());

        if (typeTicket.equals(TicketType.PAGO))
            attendee.setBalance(attendee.getBalance() + (convertToFloat(ticket.getPrice())));
    }

    private void devolutionTicketToEvent(Event event, Ticket ticket, TicketType typeTicket) {
        event.removeTicket(ticket.getId());

        if (typeTicket.equals(TicketType.PAGO)) {
            event.setAmountPayedTicketsSold(event.getAmountPayedTicketsSold() - 1);
        } else {
            event.setAmountFreeTicketsSold(event.getAmountFreeTicketsSold() - 1);
        }
    }
}
