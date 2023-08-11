package kaif.nurbackend.controllers;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kaif.nurbackend.service.StatsService;

@RestController
@RequestMapping("/admin")
public class StatsControllers {

    @Autowired
    private StatsService statsService;

    @GetMapping("/fetchStats")
    public ResponseEntity<?> fetchStats(
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        if (!search.isEmpty() && startDate != null && endDate != null) {
            var res = statsService.fetchStatsByStatusAndDate(search, startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else if (search.isEmpty() && startDate != null && endDate != null) {    
            var res = statsService.fetchStatsByDate(startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            var res = statsService.fetchStatsByStatus(search);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
    }

}
