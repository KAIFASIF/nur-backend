package kaif.nurbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import kaif.nurbackend.entities.LastItemcode;

public interface LastItemcodeRepo extends JpaRepository<LastItemcode, Long> {
    public LastItemcode findFirstBy();
}
