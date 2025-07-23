package com.welldev.ams.service;

import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.request.CategoryDTO;
import com.welldev.ams.model.response.BaseResponse;

public interface CategoryService {
  ResponseEntity<BaseResponse> createCategory(CategoryDTO categoryDTO);

  ResponseEntity<BaseResponse> updateCategory(CategoryDTO categoryDTO, String categoryId);

  ResponseEntity<BaseResponse> getCategories(String categoryName, int page, int size, String sortBy, String order);

  ResponseEntity<BaseResponse> getCategory(String categoryId);

  ResponseEntity<BaseResponse> deleteCategory(String categoryId);
}
