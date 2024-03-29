package eventoapp.domain.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eventoapp.domain.entities.Event;

@Repository
public interface EventRepository extends JpaRepository <Event,Long>{

    @Query(
            "SELECT e FROM Event e " + 
            "WHERE " +
            " ( LOWER(e.name)        LIKE LOWER(CONCAT('%', :name,'%'))  ) AND " + 
            " ( LOWER(e.description) LIKE LOWER(CONCAT('%', :description, '%'))  ) AND " + 
            " ( e.startDate >= :startDateAux ) "
        )
    public Page <Event> find(
                            Pageable pageRequest,
                            String name,
                            String description,
                            LocalDate startDateAux
                            );

    @Query(
        " SELECT e FROM Event e " +
        " WHERE ( LOWER(e.emailContact) = LOWER(:email) )"
    )
    public List<Event> findAttendeeByEmail(String email);
}