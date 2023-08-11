package kaif.nurbackend.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import kaif.nurbackend.repo.UserRepo;
import kaif.nurbackend.security_config.JwtService;
import kaif.nurbackend.utilities.Utils;
import kaif.nurbackend.dto.UserDTO;
import kaif.nurbackend.entities.User;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Utils utils;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO fetchUser(Long id) {
        var user = userRepo.findById(id).get();
        var res = new UserDTO(user.getId(), user.getFullname(), user.getUsername(), user.getEmail(),
                user.getMobile(), user.getRole(), user.getIsAuthorized(), user.getGender());
        return res;
    }

    public UserDTO createUser(User user) {
        utils.checkUserUniqueFields(user);
        if (user.getId() != null) {
            user.setId(user.getId());
            user.setFullname(user.getFullname());
            user.setUsername(user.getUsername());
            user.setEmail(user.getEmail());
            user.setMobile(user.getMobile());
            user.setRole(user.getRole());
            user.setIsAuthorized(user.getIsAuthorized());
            user.setGender(user.getGender());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var res = userRepo.save(user);
        var updatedUser = new UserDTO(res.getId(), res.getFullname(), res.getUsername(), res.getEmail(),
                res.getMobile(), res.getRole(), res.getIsAuthorized(), res.getGender());
        return updatedUser;
    }

    public Map<String, Object> generateToken(String username, User user) {
        Map<String, Object> map = new HashMap<>();
        String token = jwtService.generateToken(username);
        var userDto = new UserDTO(user.getId(), user.getFullname(), user.getUsername(), user.getEmail(),
                user.getMobile(), user.getRole(), user.getIsAuthorized(), user.getGender());
        map.put("role", user.getRole());
        map.put("user", userDto);
        map.put("token", token);
        return map;
    }

}
