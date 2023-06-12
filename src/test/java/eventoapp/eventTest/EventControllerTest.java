package eventoapp.eventTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void testInsertEvent() throws Exception {
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(event.getName()))
                .andExpect(jsonPath("$.description").value(event.getDescription()));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        mockMvc.perform(delete("/events/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetEventById() throws Exception {
        mockMvc.perform(get("/events/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEvents() throws Exception {
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

}