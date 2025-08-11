package com.welldev.ams.controllers;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.welldev.ams.model.request.CategoryDTO;
import com.welldev.ams.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
    CategoryDTO created = categoryService.createCategory(categoryDTO);
    return ResponseEntity.status(201).body(created);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<Page<CategoryDTO>> getCategories(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "name") String sortBy,
      @RequestParam(defaultValue = "asc") String order) {
    Page<CategoryDTO> categories = categoryService.getCategories(page, pageSize, sortBy, order);
    return ResponseEntity.ok(categories);
  }

  @GetMapping("/{categoryId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<CategoryDTO> getCategory(@PathVariable String categoryId) {
    CategoryDTO dto = categoryService.getCategory(categoryId);
    return ResponseEntity.ok(dto);
  }

  @PutMapping("/{categoryId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable String categoryId) {
    CategoryDTO updated = categoryService.updateCategory(categoryDTO, categoryId);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{categoryId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
    categoryService.deleteCategory(categoryId);
    return ResponseEntity.noContent().build();
  }
}
