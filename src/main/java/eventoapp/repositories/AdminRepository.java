package eventoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eventoapp.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository <Admin,Long>{ }