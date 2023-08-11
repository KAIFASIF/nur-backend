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

import kaif.nurbackend.entities.Category;
import kaif.nurbackend.service.CategoryService;
import kaif.nurbackend.utilities.Utils;

@RestController
@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private Utils utils;

	@PostMapping("/saveCategory/{userId}")
	public ResponseEntity<?> saveCategory(@PathVariable Long userId, @RequestBody Category categeory) {

		if (utils.isLongNullOrEmpty(userId) || utils.isStringNullOrEmpty(categeory.getName())) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId, name are required fields");
		}

		if (categeory.getId() != null) {
			var res = categoryService.saveCategory(userId, categeory);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}

		var res = categoryService.saveCategory(userId, categeory);
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}

	@GetMapping("/fetchCategories/{userId}")
	public ResponseEntity<?> fetchSizes(@PathVariable Long userId,
			@RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
			@RequestParam(required = false, defaultValue = "5", value = "size") Integer size) {
		if (utils.isLongNullOrEmpty(userId)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId is a required fields");
		}
		Pageable pageable = PageRequest.of(page, size);
		var res = categoryService.fetchCategories(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	@GetMapping("/fetchAllCategories/{userId}")
	public ResponseEntity<?> fetchAllCategories(@PathVariable Long userId) {
		if (utils.isLongNullOrEmpty(userId)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId is a required fields");
		}
		var res = categoryService.fetchAllCategories();
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	@DeleteMapping("/deleteCategory/{userId}/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Long userId, @PathVariable Long id) {
		if (utils.isLongNullOrEmpty(userId) || utils.isLongNullOrEmpty(id)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId and id are required fields");
		}
		var res = categoryService.deleteCategory(id);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	@DeleteMapping("/deleteAllCategories/{userId}")
	public ResponseEntity<?> deleteAllCategories(@PathVariable Long userId) {
		if (utils.isLongNullOrEmpty(userId)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("userId is a required fields");
		}
		var res = categoryService.deleteAllCategories();
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	// @GetMapping("/{searchVal}/searchCategory")
	// public ResponseEntity<?> searchCategory(@PathVariable String searchVal) {
	// try {
	// List<Category> category = this.categoryService.searchCategory(searchVal);

	// return ResponseEntity.status(HttpStatus.OK).body(category);
	// } catch (Exception e) {
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// }
	// }

}
