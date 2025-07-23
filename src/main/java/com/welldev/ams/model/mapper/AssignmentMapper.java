package com.welldev.ams.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.welldev.ams.model.db.Assignment;
import com.welldev.ams.model.request.AssignmentDTO;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
  Assignment toEntity(AssignmentDTO assignmentDTO);
  void updateEntity(AssignmentDTO assignmentDTO, @MappingTarget Assignment assignment);
}
