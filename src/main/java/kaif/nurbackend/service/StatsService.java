package kaif.nurbackend.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kaif.nurbackend.exceptions.ResourceNotFound;
import kaif.nurbackend.repo.BillRepo;
import kaif.nurbackend.repo.UserRepo;

@Service
public class StatsService {

    @Autowired
    private BillRepo billRepo;

    @Autowired
    private UserRepo userRepo;

    // fetch Stats By Status and date
    public Map<String, Object> fetchStatsByStatusAndDate(String status, Date starDate, Date endDate) {
        Map<String, Object> map = new HashMap<>();
        if (status.equalsIgnoreCase("paid") || status.equalsIgnoreCase("pending")) {
            Long totalCount = billRepo.countByDateBetween(starDate, endDate);
            Long statusCount = billRepo.countByStatusAndDateBetween(status, starDate, endDate);
            String fetchedStatus = status.substring(0, 1).toUpperCase() + status.substring(1);
            var bills = billRepo.findByStatusAndDateBetween(status, starDate, endDate);
            map.put("Total bills", totalCount);
            map.put(fetchedStatus, statusCount);
            map.put("bills", bills);
            return map;
        }
        var user = userRepo.findByFullnameIgnoreCase(status);
        if (user == null) {
            throw new ResourceNotFound("Invalid search");
        }
        var count = billRepo.countByUserIdAndDateBetween(user.getId(), starDate, endDate);
        var bills = billRepo.findByUserIdAndDateBetween(user.getId(), starDate, endDate);
        var paidCount = billRepo.countByUserIdAndStatusAndDateBetween(user.getId(), "paid", starDate, endDate);
        var pendingCount = billRepo.countByUserIdAndStatusAndDateBetween(user.getId(), "pending", starDate, endDate);
        map.put("Total Bills", count);
        map.put("Paid", paidCount);
        map.put("Pending", pendingCount);
        map.put("bills", bills);
        return map;
    }

    // fetch Stats By date only
    public Map<String, Object> fetchStatsByDate(Date starDate, Date endDate) {
        Map<String, Object> map = new HashMap<>();
        Long totalCount = billRepo.countByDateBetween(starDate, endDate);
        Long paid = billRepo.countByStatusAndDateBetween("paid", starDate, endDate);
        Long pending = billRepo.countByStatusAndDateBetween("pending", starDate, endDate);
        var bills = billRepo.findByDateBetween(starDate, endDate);
        map.put("bills", bills);
        map.put("Total bills", totalCount);
        map.put("Paid", paid);
        map.put("Pending", pending);
        return map;
    }

    // fetch Stats By Status only or user only
    public Map<String, Object> fetchStatsByStatus(String status) {
        Map<String, Object> map = new HashMap<>();
        if (status.equalsIgnoreCase("paid") || status.equalsIgnoreCase("pending")) {
            String fetchedStatus = status.substring(0, 1).toUpperCase() + status.substring(1);
            var totalBills = billRepo.count();
            Long countByStatus = billRepo.countByStatus(status);
            var bills = billRepo.findByStatus(status);
            map.put("Total Bills", totalBills);
            map.put(fetchedStatus, countByStatus);
            map.put("bills", bills);
            return map;
        }

        var user = userRepo.findByFullnameIgnoreCase(status);
        if (user == null) {
            throw new ResourceNotFound("Invalid search");
        }

        var count = billRepo.countByUserId(user.getId());
        var paidCount = billRepo.countByUserIdAndStatus(user.getId(), "paid");
        var pendingCount = billRepo.countByUserIdAndStatus(user.getId(), "pending");
        var bills = billRepo.findByUserId(user.getId());
        map.put("Total Bills", count);
        map.put("Paid", paidCount);
        map.put("Pending", pendingCount);
        map.put("bills", bills);
        return map;
    }

    

}
