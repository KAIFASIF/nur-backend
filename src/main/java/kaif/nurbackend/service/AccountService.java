package kaif.nurbackend.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kaif.nurbackend.repo.UserRepo;

@Service
public class AccountService {

    @Autowired
    private UserRepo userRepo;

    public Map<String, Object> fetchUsers(Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        Long count = userRepo.count();
        var users = userRepo.findAll(pageable).getContent().stream().filter(ele -> !ele.getRole().equals("ROLE_ADMIN"))
                .collect(Collectors.toList());

        map.put("count", count);
        map.put("users", users);
        return map;
    }

    // deleteUser
    public String deleteUser(Long id) {
        var user = userRepo.findById(id);

        System.out.println("************************************************************************");
        System.out.println(user.get().getBill());
        System.out.println("************************************************************************");

        // userRepo.deleteById(id); 
        return "User deleted sucessfully";
    }

}
