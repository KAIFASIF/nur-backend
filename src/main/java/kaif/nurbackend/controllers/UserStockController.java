package kaif.nurbackend.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

import kaif.nurbackend.entities.Stock;
import kaif.nurbackend.service.UserStockService;


@RestController
@RequestMapping("/user")
public class UserStockController {
       @Autowired
    private UserStockService userStockService;

    @GetMapping("/stock/{itemcode}")
	public ResponseEntity<?> getStockByItemcode(@PathVariable("itemcode") String itemcode) {
		try {
			Stock res = this.userStockService.getStockByItemcode(itemcode.trim());
			if (res != null && res.getAvailableQuantity() == 0)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sold out");
			else {
				Optional.ofNullable(res).orElseThrow();
				return ResponseEntity.status(HttpStatus.OK).body(res);
			}
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
    
}
