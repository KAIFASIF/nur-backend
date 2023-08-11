package kaif.nurbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kaif.nurbackend.entities.Stock;
import kaif.nurbackend.service.StockService;
import kaif.nurbackend.utilities.Utils;

@RestController
@RequestMapping("/admin")
@EnableTransactionManagement
public class StockController {

	@Autowired
	private StockService stockService;

	@Autowired
	private Utils utils;

	@PostMapping("/saveStock/{userId}")
	public ResponseEntity<?> saveStock(@PathVariable Long userId, @RequestBody Stock stock) {

		if (utils.isStringNullOrEmpty(stock.getCategory()) || utils.isStringNullOrEmpty(stock.getItemcode()) ||
				utils.isStringNullOrEmpty(stock.getSize()) || utils.isLongNullOrEmpty(stock.getPurchasedPrice()) ||
				utils.isLongNullOrEmpty(stock.getPurchasedQuantity())
				|| utils.isLongNullOrEmpty(stock.getAvailableQuantity()) ||
				utils.isLongNullOrEmpty(stock.getMrp())) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId, name are required fields");
		}

		if (stock.getId() != null) {
			var res = stockService.saveStock(userId, stock);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}

		var res = stockService.saveStock(userId, stock);
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}

	@GetMapping("/fetchStocks/{userId}")
	public ResponseEntity<?> fetchStocks(@PathVariable Long userId,
			@RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
			@RequestParam(required = false, defaultValue = "5", value = "size") Integer size) {

		if (utils.isLongNullOrEmpty(userId)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId is a required fields");
		}
		Pageable pageable = PageRequest.of(page, size);
		var res = stockService.fetchStocks(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	@DeleteMapping("/deleteStock/{userId}/{id}")
	public ResponseEntity<?> deleteStock(@PathVariable Long userId, @PathVariable Long id) {

		if (utils.isLongNullOrEmpty(userId) || utils.isLongNullOrEmpty(id)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId and id are required fields");
		}
		var res = stockService.deleteStock(id);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	@DeleteMapping("/deleteAllStocks/{userId}")
	public ResponseEntity<?> deleteAllStocks(@PathVariable Long userId) {
		if (utils.isLongNullOrEmpty(userId)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId is a required fields");
		}
		var res = stockService.deleteAllStocks();
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
}
