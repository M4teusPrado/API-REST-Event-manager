package eventoapp.domain.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import eventoapp.domain.entities.Attendee;


@Repository
public interface AttendeeRepository extends JpaRepository <Attendee,Long>{
    @Query(  
            " SELECT a FROM Attendee a "
        )
    Page<Attendee> find(Pageable pageRequest);

    @Query(
        " SELECT a FROM Attendee a INNER JOIN BaseUser b ON a.id = b.id " +
        " WHERE ( LOWER(b.email) = LOWER(:email) )"
    )
    public List<Attendee> findAttendeeByEmail(String email);
}