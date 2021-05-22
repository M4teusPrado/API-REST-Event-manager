package eventoapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eventoapp.models.Attendee;


@Repository
public interface AttendeeRepository extends JpaRepository <Attendee,Long>{
    @Query( "SELECT a FROM Attendee a")
    Page<Attendee> findAttendeePageable(Pageable pageRequest);
}