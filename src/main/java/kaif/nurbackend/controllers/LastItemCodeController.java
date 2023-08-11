package kaif.nurbackend.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kaif.nurbackend.repo.LastItemcodeRepo;
import kaif.nurbackend.utilities.Utils;

@RestController
@RequestMapping("/admin")
public class LastItemCodeController {

    @Autowired
    private LastItemcodeRepo lastItemcodeRepo;

    @Autowired
    private Utils utils;

    @PostMapping("/del")
    public ResponseEntity<?> saveLastItemcode() {
        lastItemcodeRepo.deleteAll();
        return ResponseEntity.status(HttpStatus.CREATED).body("res");
    }


    @GetMapping("/fetchLastItemcode/{userId}")
    public ResponseEntity<?> fetchLastItemcode(@PathVariable Long userId) {
        if (utils.isLongNullOrEmpty(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId is a required field");
        }
        Map<String, Object> map = new HashMap<>();
        try {
            long lastItemcode = 1000;
            if (lastItemcodeRepo.count() > 0)
                lastItemcode = Long.parseLong(lastItemcodeRepo.findFirstBy().getItemcode().replaceAll("[^0-9]", ""));
            map.put("lastItemcode", lastItemcode);
            return new ResponseEntity<>(map, HttpStatus.OK);

        } catch (Exception e) {
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
