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
        EventServiceFunctions eventService = new EventServiceFunctions(eventRepository, adminService);

        // Crie um evento existente
        Event existingEvent = buildEvent();
        eventRepository.save(existingEvent);

        // Crie um novo evento para teste
        Event newEvent = buildEvent();
        newEvent.setStartDate(LocalDate.of(2023, 10, 15));
        newEvent.setEndDate(LocalDate.of(2023, 10, 25));
        newEvent.setStartTime(LocalTime.of(10, 0));
        newEvent.setEndTime(LocalTime.of(12, 0));

        // Teste um evento que tem data e hora totalmente diferentes do evento existente
        assertTrue(eventService.validDate(existingEvent, newEvent));

        // Teste um evento que tem a mesma data de início que o evento existente, mas horário anterior
        newEvent.setStartDate(existingEvent.getStartDate());
        newEvent.setStartTime(LocalTime.of(8, 0));
        assertTrue(eventService.validDate(existingEvent, newEvent));

        // Teste um evento que tem a mesma data de término que o evento existente, mas horário posterior
        newEvent.setStartDate(LocalDate.of(2023, 10, 10));
        newEvent.setEndDate(existingEvent.getEndDate());
        newEvent.setEndTime(LocalTime.of(20, 0));
        assertTrue(eventService.validDate(existingEvent, newEvent));

        // Teste um evento que tem a mesma data de início e término que o evento existente, mas horário incompatível
        newEvent.setStartDate(existingEvent.getStartDate());
        newEvent.setEndDate(existingEvent.getEndDate());
        newEvent.setStartTime(LocalTime.of(13, 0));
        newEvent.setEndTime(LocalTime.of(15, 0));
        assertFalse(eventService.validDate(existingEvent, newEvent));

        // Teste um evento que tem uma data de início logo após a data de término do evento existente
        newEvent.setStartDate(existingEvent.getEndDate().plusDays(1));
        newEvent.setEndDate(newEvent.getStartDate().plusDays(5));
        assertTrue(eventService.validDate(existingEvent, newEvent));

        // Teste um evento que tem uma data de término logo antes da data de início do evento existente, com horário posterior
        newEvent.setStartDate(existingEvent.getStartDate().minusDays(5));
        newEvent.setEndDate(existingEvent.getStartDate().minusDays(1));
        newEvent.setEndTime(LocalTime.of(14, 0));
        assertTrue(eventService.validDate(existingEvent, newEvent));

        // Teste um evento que tem a mesma data de término que o evento existente, mas horário incompatível
        newEvent.setEndDate(existingEvent.getEndDate());
        newEvent.setEndTime(LocalTime.of(9, 0));
        assertFalse(eventService.validDate(existingEvent, newEvent));
    }
}
