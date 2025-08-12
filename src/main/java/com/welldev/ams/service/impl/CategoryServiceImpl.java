package com.welldev.ams.service.impl;

import java.util.Optional;
import java.util.UUID;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Category;
import com.welldev.ams.model.mapper.CategoryMapper;
import com.welldev.ams.model.request.CategoryDTO;
import com.welldev.ams.repositories.CategoryRepository;
import com.welldev.ams.service.CategoryService;

@Slf4j
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
    this.categoryRepository = categoryRepository;
    this.categoryMapper = categoryMapper;
  }

  @Override
  public CategoryDTO createCategory(CategoryDTO categoryDTO) {
    Category entity = categoryMapper.categoryDTOToCategoryEntity(categoryDTO);
    Category saved = categoryRepository.save(entity);
    return categoryMapper.toDto(saved);
  }

  @Override
  public CategoryDTO updateCategory(CategoryDTO categoryDTO, String categoryId) {
    Optional<Category> optional = categoryRepository.findById(UUID.fromString(categoryId));
    if (optional.isPresent()) {
      Category entity = optional.get();
      categoryMapper.updateCategoryEntity(categoryDTO, entity);
      Category saved = categoryRepository.save(entity);
      return categoryMapper.toDto(saved);
    }
    throw new RuntimeException("Category not found");
  }

  @Override
  public Page<CategoryDTO> getCategories(int page, int size, String sortBy, String order) {
    Sort sort = order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    Page<Category> categoryPage = categoryRepository.findAll(PageRequest.of(page, size, sort));
    return categoryPage.map(categoryMapper::toDto);
  }

  @Override
  public CategoryDTO getCategory(String categoryId) {
    Optional<Category> category = categoryRepository.findById(UUID.fromString(categoryId));
    if (category.isPresent()) {
      return categoryMapper.toDto(category.get());
    }
    throw new RuntimeException("Category not found");
  }

  @Override
  public void deleteCategory(String categoryId) {
    Optional<Category> category = categoryRepository.findById(UUID.fromString(categoryId));
    if (category.isPresent()) {
      categoryRepository.delete(category.get());
    } else {
      throw new RuntimeException("Category not found");
    }
  }
}
