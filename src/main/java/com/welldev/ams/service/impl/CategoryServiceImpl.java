package com.welldev.ams.service.impl;

import java.util.Optional;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Category;
import com.welldev.ams.model.db.Role;
import com.welldev.ams.model.mapper.CategoryMapper;
import com.welldev.ams.model.request.CategoryDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.repositories.CategoryRepository;
import com.welldev.ams.service.CategoryService;
import com.welldev.ams.utils.Utils;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;
  private final Utils utils;

  public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, Utils utils){
    this.categoryRepository = categoryRepository;
    this.categoryMapper = categoryMapper;
    this.utils = utils;
  }

  @Override
  public ResponseEntity<BaseResponse> createCategory(CategoryDTO categoryDTO) {
    try {
      Category saveEntity = categoryRepository.save(categoryMapper.categoryDTOToCategoryEntity(categoryDTO));
      return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), "Category Created Successfully."));
    }
    catch (Exception e) {
      log.error("Create Category Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> updateCategory(CategoryDTO categoryDTO, String categoryId) {
    try {
      Optional<Category> categoryEntity = categoryRepository.findByIdAndActiveAndDeleted(UUID.fromString(categoryId), true, false);
      if(categoryEntity.isPresent()) {
        categoryMapper.updateCategoryEntity(categoryDTO, categoryEntity.get());
        Category saveEntity = categoryRepository.save(categoryEntity.get());
        return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), ""));
      }
      return ResponseEntity.notFound().build();
    }
    catch (Exception e) {
      log.error("Update Category Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getCategories(String categoryName, int page, int size, String sortBy,
      String order)
  {
    try {
      Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
      if(categoryName != null && !categoryName.isEmpty()) {
        Page<Category> roleList = categoryRepository.findByNameContainingIgnoreCaseAndDeletedAndActive(categoryName, false, true, pageable);
        return ResponseEntity.ok().body(utils.generateResponse(roleList, true, HttpStatus.OK.value(), ""));
      }
      else{
        Page<Category> roleList = categoryRepository.findByActiveAndDeleted(true, false, pageable);
        return ResponseEntity.ok().body(utils.generateResponse(roleList, true, HttpStatus.OK.value(), ""));
      }
    }
    catch (Exception e) {
      log.error("Get Categories Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getCategory(String categoryId) {
    try {
      Optional<Category> category = categoryRepository.findByIdAndDeleted(UUID.fromString(categoryId), false);
      return category.map(value -> ResponseEntity.ok()
              .body(utils.generateResponse(value, true, HttpStatus.OK.value(), "")))
          .orElseGet(() -> ResponseEntity.notFound()
              .build());
    }
    catch (Exception e) {
      log.error("Get Category Error: {}", categoryId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> deleteCategory(String categoryId) {
    try {
      Optional<Category> category = categoryRepository.findByIdAndActiveAndDeleted(UUID.fromString(categoryId), true, false);
      if(category.isPresent()) {
        category.get().setDeleted(true);
        categoryRepository.save(category.get());
        return ResponseEntity.noContent().build();
      }
      else{
        return ResponseEntity.notFound().build();
      }
    }
    catch (Exception e) {
      log.error("Delete Category Error: {}", categoryId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }
}
