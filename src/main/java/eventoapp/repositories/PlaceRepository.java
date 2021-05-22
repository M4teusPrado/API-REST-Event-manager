package eventoapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eventoapp.models.Place;

public interface PlaceRepository extends JpaRepository<Place,Long>{
    @Query( "SELECT p FROM Place p ")
    Page<Place> findPlacePageable(Pageable pageRequest);
}