package kaif.nurbackend.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kaif.nurbackend.repo.BillNoRepo;
import kaif.nurbackend.utilities.Utils;

@RestController
@RequestMapping("/user")
public class BillNoController {

    @Autowired
    private BillNoRepo billNoRepo;

    @Autowired
    private Utils utils;

    @GetMapping("/fetchLastBillNo/{userId}")
    public ResponseEntity<?> fetchLastItemcode(@PathVariable Long userId) {
        if (utils.isLongNullOrEmpty(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId is a required field");
        }
        Map<String, Object> map = new HashMap<>();
        try {
            long billNo = 1000;
            if (billNoRepo.count() > 0)
                billNo = billNoRepo.findFirstBy().getLastBillNo();
            map.put("billNo", billNo);
            return new ResponseEntity<>(map, HttpStatus.OK);

        } catch (Exception e) {
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
