package com.welldev.ams.service;

import org.springframework.data.domain.Page;
import com.welldev.ams.model.request.CategoryDTO;

public interface CategoryService {
  CategoryDTO createCategory(CategoryDTO categoryDTO);

  CategoryDTO updateCategory(CategoryDTO categoryDTO, String categoryId);

  Page<CategoryDTO> getCategories(int page, int size, String sortBy, String order);

  CategoryDTO getCategory(String categoryId);

  void deleteCategory(String categoryId);
}
