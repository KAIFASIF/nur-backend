package kaif.nurbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kaif.nurbackend.entities.Bill;
import kaif.nurbackend.service.BillService;
import kaif.nurbackend.utilities.Utils;

@RestController
@RequestMapping("/user")
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private Utils utils;

    // create new Bill
    @PostMapping("/{userId}/createBill")
    public ResponseEntity<?> createBill(@PathVariable Long userId, @RequestBody Bill bill) {
        if (utils.isLongNullOrEmpty(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId and id are required fields");
        }
        var res = billService.createBill(userId, bill);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
       
    }
}
