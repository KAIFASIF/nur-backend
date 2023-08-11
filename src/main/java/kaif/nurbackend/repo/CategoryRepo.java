package kaif.nurbackend.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import kaif.nurbackend.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    // List<Category> findByCategoryStartingWith(String name);
}
