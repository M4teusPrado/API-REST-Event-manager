package eventoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import eventoapp.models.Place;

public interface PlaceRepository extends JpaRepository<Place,Long>{   }