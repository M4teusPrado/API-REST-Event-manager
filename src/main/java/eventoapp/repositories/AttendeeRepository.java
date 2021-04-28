package eventoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eventoapp.models.Attendee;


@Repository
public interface AttendeeRepository extends JpaRepository <Attendee,Long>{ }