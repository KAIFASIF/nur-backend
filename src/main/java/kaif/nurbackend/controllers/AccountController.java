package kaif.nurbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kaif.nurbackend.service.AccountService;
import kaif.nurbackend.utilities.Utils;

@RestController
@RequestMapping("/admin")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private Utils utils;

    @GetMapping("/fetchUsers/{userId}")
    public ResponseEntity<?> fetchUsers(@PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
            @RequestParam(required = false, defaultValue = "5", value = "size") Integer size) {
        if (utils.isLongNullOrEmpty(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId  is required");
        }
        Pageable pageable = PageRequest.of(page, size);
        var res = accountService.fetchUsers(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("/deleteUser/{userId}/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId, @PathVariable Long id) {
        if (utils.isLongNullOrEmpty(userId) || utils.isLongNullOrEmpty(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId  and id are required");
        }
        var res = accountService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
