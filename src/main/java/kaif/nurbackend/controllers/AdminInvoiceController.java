package kaif.nurbackend.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kaif.nurbackend.service.AdminInvoiceService;
import kaif.nurbackend.utilities.CommonMethods;
import kaif.nurbackend.utilities.Utils;

@RestController
@RequestMapping("/admin")
public class AdminInvoiceController {

    @Autowired
    private Utils utils;

    @Autowired
    private AdminInvoiceService adminInvoiceService;

    @Autowired
    private CommonMethods commonMethods;

    @GetMapping("/fetchBillWithCart/{id}")
    public ResponseEntity<?> fetchBillWithCart(@PathVariable Long id) {
        if (utils.isLongNullOrEmpty(id)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("id is required");
        }
        var res = commonMethods.fetchBillWithCart(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // fetch user bills by date or search
    @GetMapping("/invoices")
    public ResponseEntity<?> searchInvoice(
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
            @RequestParam(required = false, defaultValue = "5", value = "size") Integer size,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        Pageable pageable = PageRequest.of(page, size);
        if (!search.isEmpty() && startDate != null && endDate != null) {
            var res = adminInvoiceService.fetchInvoicesByStatusAndDate(search, startDate,endDate, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else if (search.isEmpty() && startDate != null && endDate != null) {
            var res = adminInvoiceService.fetchInvoicesByDate(startDate, endDate, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            var res = adminInvoiceService.fetchInvoicesByStatus(search, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
    }
}
