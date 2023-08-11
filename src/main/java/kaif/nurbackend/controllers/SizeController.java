package kaif.nurbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import kaif.nurbackend.entities.Size;
import kaif.nurbackend.service.SizeService;
import kaif.nurbackend.utilities.Utils;

@RequestMapping("/admin")
@RestController
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @Autowired
    private Utils utils;

    @PostMapping("/saveSize/{userId}")
    public ResponseEntity<?> saveSize(@PathVariable Long userId, @RequestBody Size size) {
        if (utils.isLongNullOrEmpty(userId) || utils.isStringNullOrEmpty(size.getName())
                || utils.isStringNullOrEmpty(size.getBrief())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId, name and brief are required fields");
        }

        if (size.getId() != null) {
            var res = sizeService.saveSize(userId, size);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        var res = sizeService.saveSize(userId, size);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/fetchSizes/{userId}")
    public ResponseEntity<?> fetchSizes(@PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
            @RequestParam(required = false, defaultValue = "5", value = "size") Integer size) {
        if (utils.isLongNullOrEmpty(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId is a required fields");
        }
        Pageable pageable = PageRequest.of(page, size);
        var res = sizeService.fetchSizes(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/fetchAllSizes/{userId}")
    public ResponseEntity<?> fetchAllSizes(@PathVariable Long userId) {
        if (utils.isLongNullOrEmpty(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId is a required fields");
        }
        var res = sizeService.fetchAllSizes();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("/deleteSize/{userId}/{id}")
    public ResponseEntity<?> deleteSize(@PathVariable Long userId, @PathVariable Long id) {
        if (utils.isLongNullOrEmpty(userId) || utils.isLongNullOrEmpty(id)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId and id are required fields");
        }
        var res = sizeService.deleteSize(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("/deleteAllSizes/{userId}")
    public ResponseEntity<?> deleteAllSizes(@PathVariable Long userId) {
        if (utils.isLongNullOrEmpty(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId is a required fields");
        }
        var res = sizeService.deleteAllSizes();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/admin/good")
    public ResponseEntity<?> test() {
        return ResponseEntity.status(HttpStatus.OK).body("res");
    }

}
