package eventoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eventoapp.models.Event;

@Repository
public interface EventRepository extends JpaRepository <Event,Long>{

}
