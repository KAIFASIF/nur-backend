package kaif.nurbackend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kaif.nurbackend.entities.Category;
import kaif.nurbackend.exceptions.FieldAlreadyExist;
import kaif.nurbackend.repo.CategoryRepo;
import kaif.nurbackend.repo.UserRepo;

@Service
public class CategoryService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public Category saveCategory(Long userId, Category category) {
        var user = userRepo.findById(userId).get();
        var categoryList = categoryRepo.findAll();
        var categoryExists = false;
        if (category.getId() != null) {
            var filteredList = categoryList.stream().filter(ele -> !ele.getId().equals(category.getId()))
                    .collect(Collectors.toList());
            categoryExists = filteredList.stream().anyMatch(ele -> ele.getName().equalsIgnoreCase(category.getName()));
            category.setId(category.getId());
        } else {
            categoryExists = categoryList.stream().anyMatch(ele -> ele.getName().equalsIgnoreCase(category.getName()));
        }

        if (categoryExists) {
            throw new FieldAlreadyExist("Category already exists");
        }

        category.setUser(user);
        return categoryRepo.save(category);
    }

    public Map<String, Object> fetchCategories(Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        Long count = categoryRepo.count();
        var categories = categoryRepo.findAll(pageable).getContent();
        map.put("count", count);
        map.put("categories", categories);
        return map;
    }

    public List<Category> fetchAllCategories() {
        return categoryRepo.findAll();
    }

    public String deleteCategory(Long id) {
        try {
            categoryRepo.deleteById(id);
            return "Category Deleted";
        } catch (Exception e) {
            return "Category not deleted";
        }
    }

    public String deleteAllCategories() {
        try {
            categoryRepo.deleteAll();
            return "All categories Deleted";
        } catch (Exception e) {
            return "categories not deleted";
        }
    }

    // public List<Category> searchCategory(String category) {
    // return this.categoryRepo.findByCategoryStartingWith(category);
    // }

}
