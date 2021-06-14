package eventoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import eventoapp.models.Ticket;

public interface TicketRepository extends JpaRepository<Ticket,Long>{
}