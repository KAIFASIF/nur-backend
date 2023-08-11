package kaif.nurbackend.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import kaif.nurbackend.entities.Size;
import kaif.nurbackend.exceptions.FieldAlreadyExist;
import kaif.nurbackend.repo.SizeRepo;
import kaif.nurbackend.repo.UserRepo;

@Service
public class SizeService {

    @Autowired
    private SizeRepo sizeRepo;

    @Autowired
    private UserRepo userRepo;

    public Size saveSize(Long userId, Size size) {
        var user = userRepo.findById(userId).get();
        var sizeList = sizeRepo.findAll();
        var sizeExists = false;
        if (size.getId() != null) {
            var filteredList = sizeList.stream().filter(ele -> !ele.getId().equals(size.getId()))
                    .collect(Collectors.toList());
            sizeExists = filteredList.stream().anyMatch(ele -> ele.getName().equalsIgnoreCase(size.getName()));
            size.setId(size.getId());
        } else {
            sizeExists = sizeList.stream().anyMatch(ele -> ele.getName().equalsIgnoreCase(size.getName()));
        }

        if (sizeExists) {
            throw new FieldAlreadyExist("Size already exists");
        }

        size.setUser(user);
        return sizeRepo.save(size);
    }

    public Map<String, Object> fetchSizes(Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        Long count = sizeRepo.count();
        var sizes = sizeRepo.findAll(pageable).getContent();
        map.put("count", count);
        map.put("sizes", sizes);
        return map;
    }

    public List<Size> fetchAllSizes() {
        return sizeRepo.findAll();
    }

    public String deleteSize(Long id) {
        try {
            sizeRepo.deleteById(id);
            return "Size Deleted";
        } catch (Exception e) {
            return "Size not deleted";
        }
    }

    public String deleteAllSizes() {
        try {
            sizeRepo.deleteAll();
            return "All sizes Deleted";
        } catch (Exception e) {
            return "Sizes not deleted";
        }
    }

}
