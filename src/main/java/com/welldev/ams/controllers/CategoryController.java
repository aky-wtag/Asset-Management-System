package com.welldev.ams.controllers;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.welldev.ams.model.request.CategoryDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.CategoryService;
import com.welldev.ams.utils.Utils;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  private final CategoryService categoryService;
  private final Utils utils;

  public CategoryController(CategoryService categoryService,Utils utils) {
    this.categoryService = categoryService;
    this.utils = utils;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<BaseResponse> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
    CategoryDTO created = categoryService.createCategory(categoryDTO);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(utils.generateResponse(created,true, HttpStatus.CREATED.value(), "Location Created Successfully"));
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<BaseResponse> getCategories(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "name") String sortBy,
      @RequestParam(defaultValue = "asc") String order) {
    Page<CategoryDTO> categories = categoryService.getCategories(page, pageSize, sortBy, order);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(categories,true, HttpStatus.OK.value(), ""));
  }

  @GetMapping("/{categoryId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<BaseResponse> getCategory(@PathVariable String categoryId) {
    CategoryDTO dto = categoryService.getCategory(categoryId);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(dto,true, HttpStatus.OK.value(), ""));
  }

  @PutMapping("/{categoryId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<BaseResponse> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable String categoryId) {
    CategoryDTO updated = categoryService.updateCategory(categoryDTO, categoryId);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(updated,true, HttpStatus.OK.value(), ""));
  }

  @DeleteMapping("/{categoryId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
    categoryService.deleteCategory(categoryId);
    return ResponseEntity.noContent().build();
  }
}
