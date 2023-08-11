package kaif.nurbackend.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import kaif.nurbackend.entities.Stock;

public interface StockRepo extends JpaRepository<Stock, Long> {
    Stock findByItemcode(String itemcode);

    List<Stock> findByItemcodeStartingWith(String itemcode);

}
