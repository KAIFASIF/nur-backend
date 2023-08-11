package kaif.nurbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import kaif.nurbackend.entities.Billno;

public interface BillNoRepo  extends JpaRepository<Billno, Long> {
    public Billno findFirstBy();
}
