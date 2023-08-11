package kaif.nurbackend.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kaif.nurbackend.entities.Bill;

public interface BillRepo extends JpaRepository<Bill, Long> {

    Long countByUserIdAndDateBetween(Long userId, Date startDate, Date endDate);

    Page<Bill> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable pageable);

    Long countByUserIdAndStatusStartingWith(Long userId, String status);

    Page<Bill> findByUserIdAndStatusStartingWith(Long userId, String status, Pageable pageable);

    Long countByUserIdAndStatusStartingWithAndDateBetween(Long userId, String status, Date startDate, Date endDate);

    Page<Bill> findByUserIdAndStatusStartingWithAndDateBetween(Long userId, String status, Date startDate, Date endDate,
            Pageable pageable);

    // admin methods
    Long countByDateBetween(Date startDate, Date endDate);

    Page<Bill> findByDateBetween(Date startDate, Date endDate, Pageable pageable);

    List<Bill> findByDateBetween(Date startDate, Date endDate);

    Long countByStatus(String status);

    Long countByStatusAndDateBetween(String status, Date startDate, Date endDate);

    Long countByUserId(Long userId);

    Long countByUserIdAndStatus(Long userId, String status);

    List<Bill> findByUserId(Long userId);

    List<Bill> findByStatus(String status);

    List<Bill> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate);

    Long countByUserIdAndStatusAndDateBetween(Long userId, String status, Date startDate, Date endDate);

    Page<Bill> findByStatus(String status, Pageable pageable);

    Page<Bill> findByUserId(Long userId, Pageable pageable);

    Page<Bill> findByStatusAndDateBetween(String status, Date startDate, Date endDate, Pageable pageable);
    List<Bill> findByStatusAndDateBetween(String status, Date startDate, Date endDate);


}
