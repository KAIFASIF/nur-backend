package kaif.nurbackend.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import kaif.nurbackend.entities.Billcart;


public interface BillcartRepo extends JpaRepository<Billcart, Long> {
     List<Billcart> findByBillId(Long parentId);
    
}



