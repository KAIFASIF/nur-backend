package kaif.nurbackend.utilities;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kaif.nurbackend.entities.User;
import kaif.nurbackend.exceptions.FieldAlreadyExist;
import kaif.nurbackend.repo.UserRepo;

@Service
public class Utils {

    @Autowired
    private UserRepo userRepo;

    public Boolean isStringNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty() || str.trim().isBlank();
    }


    public Boolean isLongNullOrEmpty(Long value) {
        return value == null;
    }

    public Boolean isBoolNullOrEmpty(Boolean value) {
        return value == null;
    }

    public Boolean isDateNullOrOutOfRange(Date date, Date startDate, Date endDate) {
        return date == null || (startDate != null && date.before(startDate)) || (endDate != null && date.after(endDate));
    }
    
   
    public void checkUserUniqueFields(User user) {
        if (user.getId() != null) {
            List<User> userList = userRepo.findAll();

            List<User> filteredList = userList.stream().filter(ele -> !ele.getId().equals(user.getId()))
                    .collect(Collectors.toList());

            Boolean usernameExists = filteredList.stream()
                    .anyMatch(ele -> ele.getUsername().equalsIgnoreCase(user.getUsername()));
            Boolean mobileExists = filteredList.stream().anyMatch(ele -> ele.getMobile().equals(user.getMobile()));
            Boolean emailExists = filteredList.stream()
                    .anyMatch(ele -> ele.getEmail().equalsIgnoreCase(user.getEmail()));

            if (usernameExists) {
                throw new FieldAlreadyExist("Username already exists");
            }

            if (mobileExists) {
                throw new FieldAlreadyExist("Mobile already exists");
            }
            if (emailExists) {
                throw new FieldAlreadyExist("Email already exists");
            }

        } else {
            var usernameExists = this.userRepo.findAll().stream()
                    .anyMatch(ele -> ele.getUsername().equalsIgnoreCase(user.getUsername()));
            User mobileExists = this.userRepo.findByMobile(user.getMobile());

            User emailExists = this.userRepo.findByEmailIgnoreCase(user.getEmail());
            if (usernameExists) {
                throw new FieldAlreadyExist("Username already exists");
            }
            if (mobileExists != null) {
                throw new FieldAlreadyExist("Mobile already exists");
            }
            if (emailExists != null) {
                throw new FieldAlreadyExist("Email already exists");
            }
        }
    }

    public User fetchUserByMobileOrEmail(String username) {
        User user;
        try {
            Long mobile = Long.parseLong(username);
            user = userRepo.findByMobile(mobile);
        } catch (NumberFormatException e) {
            user = userRepo.findByEmailIgnoreCase(username);
        }
        return user;
    }

}
