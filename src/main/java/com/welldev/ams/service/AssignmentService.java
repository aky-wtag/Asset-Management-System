package com.welldev.ams.service;

import java.time.ZonedDateTime;

import org.springframework.data.domain.Page;

import com.welldev.ams.model.request.AssignmentDTO;

public interface AssignmentService {
  AssignmentDTO createAssignment(AssignmentDTO assignmentDTO);

  AssignmentDTO updateAssignment(AssignmentDTO assignmentDTO, String assignmentId);

  Page<AssignmentDTO> getAssignments(String assetId, String userId, ZonedDateTime assignedDateFrom, ZonedDateTime assignedDateTo, int page, int size, String sortBy, String order);

  AssignmentDTO getAssignment(String assignmentId);

  void deleteAssignment(String assignmentId);
}
