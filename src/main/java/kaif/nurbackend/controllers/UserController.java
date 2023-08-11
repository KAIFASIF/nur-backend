package kaif.nurbackend.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import kaif.nurbackend.entities.User;
import kaif.nurbackend.exceptions.ResourceNotFound;
import kaif.nurbackend.service.UserService;
import kaif.nurbackend.utilities.Utils;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private Utils utils;

    @PostMapping("/admin/signup")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (utils.isStringNullOrEmpty(user.getFullname()) || utils.isStringNullOrEmpty(user.getUsername()) ||
                utils.isStringNullOrEmpty(user.getEmail()) || utils.isLongNullOrEmpty(user.getMobile()) ||
                utils.isStringNullOrEmpty(user.getPassword()) || utils.isStringNullOrEmpty(user.getRole()) ||
                utils.isBoolNullOrEmpty(user.getIsAuthorized()) || utils.isStringNullOrEmpty(user.getGender())) {
            throw new ResourceNotFound(
                    "Fullname, username, email, mobile, password, role, isAuthorized  and gender are mandatory required fields");
        }
        var res = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateAndGenerateToken(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {

            User userData = utils.fetchUserByMobileOrEmail(user.getUsername());
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userData.getUsername(),
                            user.getPassword()));

            if (authentication.isAuthenticated()) {
                if (userData.getIsAuthorized().equals(false)) {
                    map.put("message", "User not authorized");
                    return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
                }
            }
            var res = userService.generateToken(null, userData);

            return new ResponseEntity<>(res, HttpStatus.OK);

        } catch (Exception e) {
            map.put("message", "Invalid credentials");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/fetch-user/{id}")
    public ResponseEntity<?> fetchUser(@PathVariable Long id) { 
        if (utils.isLongNullOrEmpty(id)) {
            throw new ResourceNotFound(
                    "Id is required");
        }
        var res = userService.fetchUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        if (utils.isLongNullOrEmpty(user.getId()) || utils.isStringNullOrEmpty(user.getFullname())
                || utils.isStringNullOrEmpty(user.getUsername()) || utils.isStringNullOrEmpty(user.getEmail())
                || utils.isLongNullOrEmpty(user.getMobile()) || utils.isStringNullOrEmpty(user.getPassword())
                || utils.isStringNullOrEmpty(user.getRole()) || utils.isBoolNullOrEmpty(user.getIsAuthorized())
                || utils.isStringNullOrEmpty(user.getGender())) {
            throw new ResourceNotFound(
                    "Id, fullname, username, email,mobile, password, role, isAuthorized  and gender are mandatory required fields");
        }
        var res = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
