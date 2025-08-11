package com.welldev.ams.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.welldev.ams.model.db.Category;
import com.welldev.ams.model.request.CategoryDTO;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
  Category categoryDTOToCategoryEntity(CategoryDTO categoryDTO);

  void updateCategoryEntity(CategoryDTO categoryDTO, @MappingTarget Category category);

  CategoryDTO toDto(Category category);
}
