package kaif.nurbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import kaif.nurbackend.entities.Size;

public interface SizeRepo extends JpaRepository<Size,Long> {
    
}
