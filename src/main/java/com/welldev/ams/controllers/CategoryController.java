package com.welldev.ams.controllers;


import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.welldev.ams.model.request.AssignmentDTO;
import com.welldev.ams.model.request.CategoryDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping("/create-category")
  ResponseEntity<BaseResponse> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
    return categoryService.createCategory(categoryDTO);
  }

  @GetMapping("/get-categories")
  ResponseEntity<BaseResponse> getCategories(
      @RequestParam(required = false) String categoryName,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String order) {
    return categoryService.getCategories(categoryName, page, pageSize, sortBy, order);
  }

  @GetMapping("/get-category")
  ResponseEntity<BaseResponse> getCategory(@RequestParam String assignmentId) {
    return categoryService.getCategory(assignmentId);
  }

  @PutMapping("/update-category")
  ResponseEntity<BaseResponse> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @RequestParam String categoryId) {
    return categoryService.updateCategory(categoryDTO,categoryId);
  }

  @DeleteMapping("/delete-category")
  ResponseEntity<BaseResponse> deleteCategory(@RequestParam String categoryId) {
    return categoryService.deleteCategory(categoryId);
  }
}
