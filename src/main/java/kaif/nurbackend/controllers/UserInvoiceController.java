package kaif.nurbackend.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kaif.nurbackend.entities.Bill;
import kaif.nurbackend.repo.BillcartRepo;
import kaif.nurbackend.service.BillService;
import kaif.nurbackend.service.UserInvoiceService;
import kaif.nurbackend.utilities.CommonMethods;
import kaif.nurbackend.utilities.Utils;

@RestController
@RequestMapping("/user")
public class UserInvoiceController {

    @Autowired
    private Utils utils;

    @Autowired
    private UserInvoiceService userInvoiceService;

    @Autowired
    private CommonMethods commonMethods;

    // fetch user bills by date
    // @GetMapping("/invoices/{userId}")
    // public ResponseEntity<?> fetchUserInvoices(@PathVariable Long userId,
    // @RequestParam(required = false, defaultValue = "0", value = "page") Integer
    // page,
    // @RequestParam(required = false, defaultValue = "5", value = "size") Integer
    // size,
    // @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date
    // startDate,
    // @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date
    // endDate) {

    // if (utils.isLongNullOrEmpty(userId)) {
    // return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId is a
    // required fields");
    // }
    // Pageable pageable = PageRequest.of(page, size);
    // var res = userInvoiceService.fetchUserInvoices(userId, startDate, endDate,
    // pageable);
    // return ResponseEntity.status(HttpStatus.OK).body(res);
    // }

    // fetch user bill with cart
    @GetMapping("/{userId}/fetchBillWithCart/{id}")
    public ResponseEntity<?> fetchBillWithCart(@PathVariable Long userId, @PathVariable Long id) {
        if (utils.isLongNullOrEmpty(userId) || utils.isLongNullOrEmpty(id)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId and id are required");
        }
        var res = commonMethods.fetchBillWithCart(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // fetch user bills by date or search
    @GetMapping("/invoices/{userId}")
    public ResponseEntity<?> searchInvoice(@PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
            @RequestParam(required = false, defaultValue = "5", value = "size") Integer size,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        Pageable pageable = PageRequest.of(page, size);

        System.out.println("********************* search " + utils.isStringNullOrEmpty(search));

        System.out.println(
                "********************* dates  " + utils.isDateNullOrOutOfRange(new Date(), startDate, endDate));

        // if (startDate != null && utils.isStringNullOrEmpty(search)) {
        // // Only startDate is provided, search is not present
        // // System.out.println("********************* only date");
        // var res = userInvoiceService.fetchUserInvoiceByDate(userId, startDate,
        // endDate, pageable);
        // return ResponseEntity.status(HttpStatus.OK).body(res);
        // }

        if (search == null && utils.isDateNullOrOutOfRange(new Date(), startDate, endDate)) {
            // Both search and date are null or empty
            System.out.println("********************* both");
            var res = userInvoiceService.fetchUserInvoiceByStatusAndDate(userId, search, startDate, endDate, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else if (search == null && utils.isDateNullOrOutOfRange(new Date(), startDate, endDate)) {
            // Only search is provided, date is null or out of range
            System.out.println("********************* dartes  only");
            var res = userInvoiceService.fetchUserInvoiceByDate(userId, startDate, endDate, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            System.out.println("********************* search  only");
            var res = userInvoiceService.fetchUserInvoiceByStatus(userId, search, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }

        // Default case if none of the above conditions are met
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        // if (utils.isStringNullOrEmpty(search) && utils.isDateNullOrOutOfRange(new
        // Date(), startDate, endDate)) {
        // System.out.println("********************* search and date");
        // var res = userInvoiceService.fetchUserInvoiceByStatusAndDate(userId, search,
        // startDate, endDate, pageable);
        // return ResponseEntity.status(HttpStatus.OK).body(res);
        // } else if (startDate != null && endDate != null &&
        // utils.isStringNullOrEmpty(search)) {
        // System.out.println("********************* only status");
        // var res = userInvoiceService.fetchUserInvoiceByStatus(userId, search,
        // pageable);
        // return ResponseEntity.status(HttpStatus.OK).body(res);
        // } else {
        // System.out.println("********************* only date");
        // var res = userInvoiceService.fetchUserInvoiceByDate(userId, startDate,
        // endDate, pageable);
        // return ResponseEntity.status(HttpStatus.OK).body(res);
        // }
        // if (utils.isStringNullOrEmpty(search) &&
        // utils.isDateNullOrOutOfRange(startDate)
        // && utils.isStringNullOrEmpty(endDate)) {
        // var res = userInvoiceService.fetchUserInvoiceByStatusAndDate(userId, search,
        // startDate, endDate, pageable);
        // return ResponseEntity.status(HttpStatus.OK).body(res);
        // } else if (utils.isStringNullOrEmpty(search) &&
        // utils.isStringNullOrEmpty(startDate)
        // && utils.isStringNullOrEmpty(endDate)) {
        // var res = userInvoiceService.fetchUserInvoiceByStatus(userId, search,
        // pageable);
        // return ResponseEntity.status(HttpStatus.OK).body(res);
        // } else {

        // var res = userInvoiceService.fetchUserInvoiceByDate(userId, startDate,
        // endDate, pageable);
        // return ResponseEntity.status(HttpStatus.OK).body(res);
        // }

    }

}
