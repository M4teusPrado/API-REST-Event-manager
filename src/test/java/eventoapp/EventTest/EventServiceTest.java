package eventoapp.EventTest;

import eventoapp.domain.dto.EventDTO;
import eventoapp.domain.entities.Admin;
import eventoapp.domain.entities.Event;
import eventoapp.domain.entities.Place;
import eventoapp.domain.entities.Ticket;
import eventoapp.domain.entities.enums.TicketType;
import eventoapp.domain.entities.objectsValue.Email;
import eventoapp.domain.repositories.EventRepository;
import eventoapp.domain.services.AdminService;
import eventoapp.domain.services.functions.EventServiceFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EventServiceTest {

    @Mock
    private EventServiceFunctions eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private AdminService adminService;

    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.eventService = new EventServiceFunctions(eventRepository, adminService);
        this.event = buildEvent();
    }

    Event buildEvent() {
        Event event = new Event();

        // Definição de valores para as variáveis
        event.setName("Meu evento");
        event.setDescription("Um evento incrível");
        event.setStartDate(LocalDate.of(2023, 10, 20));
        event.setEndDate(LocalDate.of(2023, 10, 22));
        event.setStartTime(LocalTime.of(9, 0));
        event.setEndTime(LocalTime.of(18, 0));
        event.setEmailContact("contato@meuevento.com");
        event.setAmountFreeTickets(100L);
        event.setAmountPayedTickets(50L);
        event.setAmountFreeTicketsSold(50L);
        event.setAmountPayedTicketsSold(25L);
        event.setPriceTickets(50.0);

        // Adição de um administrador ao evento
        Admin admin = new Admin();
        admin.setId(1L);
        admin.setName("Meu admin");
        admin.setEmail(new Email("admin@meuevento.com"));
        event.setAdmin(admin);

        // Adição de um lugar ao evento
        Place place = new Place();
        place.setName("Meu lugar");
        place.setAddress("Rua dos Eventos, 123");
        event.addPlace(place);

        // Adição de um bilhete ao evento
        Ticket ticket = new Ticket();
        ticket.setPrice(10.0);
        ticket.setType(TicketType.PAGO);
        event.addTicket(ticket);

        return event;
    }

    @Test
    public void testVerifyPossibilityOfInsert() {

        //Testa a validação de inserção no passado
        event.setStartDate(LocalDate.now().minusDays(1));
        event.setStartTime(LocalTime.of(10, 0));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> eventService.insertEvent(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Não é possivel criar evento no passado", exception.getReason());
    }

    @Test
    public void testCheckIfEmailAlreadyRegistered() {
        when(eventRepository.findAttendeeByEmail("contato@meuevento.com")).thenReturn(Collections.singletonList(event));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> eventService.insertEvent(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("email ja cadastrado", exception.getReason());
    }

    @Test
    public void testValidateEventDateTime() {

        event.setStartDate(LocalDate.now().plusDays(1));
        event.setEndDate(LocalDate.now());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> eventService.insertEvent(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Data incoerente", exception.getReason());

        event.setStartDate(LocalDate.now());
        event.setEndDate(LocalDate.now());
        event.setStartTime(LocalTime.of(4, 3));
        event.setEndTime(LocalTime.of(3, 4));

        exception = assertThrows(ResponseStatusException.class, () -> eventService.insertEvent(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Horario incoerente", exception.getReason());
    }

    @Test
    public void testVerifyAmountOfTickets() {

        event.setAmountFreeTickets(-50L);
        event.setAmountPayedTickets(-10L);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> eventService.insertEvent(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Quantidade de ingresso invalidos", exception.getReason());
    }

    @Test
    public void testVerifyPrice() {

        event.setPriceTickets(-1.5);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> eventService.insertEvent(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("preço do ingresso invalido", exception.getReason());
    }

    @Test
    public void testInsertEvent() {
        when(eventRepository.save(event)).thenReturn(event);
        when(adminService.getAdminById(1L)).thenReturn(null);
        Event savedEvent = eventService.insertEvent(event);
        assertEquals(event, savedEvent);
    }

    @Test
    public void testGetEventById() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        EventDTO eventDTO = eventService.getEventById(1L);

        assertNotNull(eventDTO);
        assertEquals(eventDTO.getName(), event.getName());
        assertEquals(eventDTO.getDescription(), event.getDescription());
    }

    @Test
    public void testGetEventByIdNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> eventService.getEventById(1L));
    }

    @Test
    public void testDeleteEvent() {
        event.setAmountFreeTicketsSold(0L);
        event.setAmountPayedTicketsSold(0L);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        eventService.deleteEvent(1L);

        verify(eventRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteEventWithTicketsSold() {

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        assertThrows(ResponseStatusException.class, () -> eventService.deleteEvent(1L));

        verify(eventRepository, never()).deleteById(1L);
    }




    @Test
    public void testValidDate() {

        // Crie um evento de referência
        Event event1 = new Event();
        event1.setStartDate(LocalDate.of(2023, 6, 10));
        event1.setEndDate(LocalDate.of(2023, 6, 12));
        event1.setStartTime(LocalTime.of(10, 0));
        event1.setEndTime(LocalTime.of(12, 0));

        // Crie um evento que se sobrepõe ao evento de referência (inválido)
        Event event2 = new Event();
        event2.setStartDate(LocalDate.of(2023, 6, 11));
        event2.setEndDate(LocalDate.of(2023, 6, 13));
        event2.setStartTime(LocalTime.of(11, 0));
        event2.setEndTime(LocalTime.of(13, 0));

        // Chame o método público que utiliza o método validDate
        boolean isValid = eventService.validDate(event1, event2);

        // Verifique se o resultado é o esperado (deve ser false, pois os eventos se sobrepõem)
        assertFalse(isValid);
    }


    @Test
    public void testVerifyAmountTicketsLeft() {


        Long idAttendee = 1L;


        TicketType typeTicket = TicketType.GRATUITO;
        event.setAmountFreeTicketsSold(0L);

        eventService.verifyAmountTicketsLeft(event, idAttendee, typeTicket);

        event.setAmountFreeTicketsSold(event.getAmountFreeTickets());


        TicketType finalTypeTicket = typeTicket;
        ResponseStatusException exceptionFreeTickets = assertThrows(ResponseStatusException.class, () ->
                eventService.verifyAmountTicketsLeft(event, idAttendee, finalTypeTicket));
        assertEquals(HttpStatus.BAD_REQUEST, exceptionFreeTickets.getStatus());
        assertEquals("Tickets grátuitos esgotados", exceptionFreeTickets.getReason());



        // Test case: Tickets pagos, tickets available
        typeTicket = TicketType.PAGO;
        event.setAmountPayedTicketsSold(0L);

        // Call the method to test
        eventService.verifyAmountTicketsLeft(event, idAttendee, typeTicket);

        // No exception should be thrown

        // Test case: Tickets pagos, tickets sold out
        event.setAmountPayedTicketsSold(event.getAmountPayedTickets());

        // Call the method to test and expect a ResponseStatusException with BAD_REQUEST status
        TicketType finalTypeTicket1 = typeTicket;
        ResponseStatusException exceptionPayedTickets = assertThrows(ResponseStatusException.class, () ->
                eventService.verifyAmountTicketsLeft(event, idAttendee, finalTypeTicket1));
        assertEquals(HttpStatus.BAD_REQUEST, exceptionPayedTickets.getStatus());
        assertEquals("Tickets pagos esgotados", exceptionPayedTickets.getReason());

        // Test case: Invalid ticket type
        typeTicket = null;

        // Call the method to test and expect a ResponseStatusException with BAD_REQUEST status
        TicketType finalTypeTicket2 = typeTicket;
        ResponseStatusException exceptionInvalidTicketType = assertThrows(ResponseStatusException.class, () ->
                eventService.verifyAmountTicketsLeft(event, idAttendee, finalTypeTicket2));
        assertEquals(HttpStatus.BAD_REQUEST, exceptionInvalidTicketType.getStatus());
        assertEquals("Tipo de ticket inválido", exceptionInvalidTicketType.getReason());
    }

}
