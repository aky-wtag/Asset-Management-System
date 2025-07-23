package com.welldev.ams.service;

import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.request.AssignmentDTO;
import com.welldev.ams.model.response.BaseResponse;

public interface AssignmentService {
  ResponseEntity<BaseResponse> createAssignment(AssignmentDTO assignmentDTO);

  ResponseEntity<BaseResponse> updateAssignment(AssignmentDTO assignmentDTO, String assignmentId);

  ResponseEntity<BaseResponse> getAssignments(String assetId, String userId, String remarks, ZonedDateTime assignedDateFrom, ZonedDateTime assignedDateTo, ZonedDateTime returnDateFrom, ZonedDateTime returnDateTo, int page, int size, String sortBy, String order);

  ResponseEntity<BaseResponse> getAssignment(String assignmentId);

  ResponseEntity<BaseResponse> deleteAssignment(String assignmentId);
}
