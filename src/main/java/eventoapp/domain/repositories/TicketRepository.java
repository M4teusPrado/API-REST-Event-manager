package eventoapp.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import eventoapp.domain.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket,Long>{
}