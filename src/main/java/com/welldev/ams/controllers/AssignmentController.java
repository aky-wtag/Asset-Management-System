package com.welldev.ams.controllers;

import java.time.ZonedDateTime;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.welldev.ams.model.request.AssignmentDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.AssignmentService;
import com.welldev.ams.utils.Utils;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {
  private final AssignmentService assignmentService;
  private final Utils utils;

  public AssignmentController(AssignmentService assignmentService, Utils utils) {
    this.assignmentService = assignmentService;
    this.utils = utils;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<BaseResponse> createAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO) {
    AssignmentDTO created = assignmentService.createAssignment(assignmentDTO);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(utils.generateResponse(created,true, HttpStatus.CREATED.value(), "Assignment Created Successfully"));
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<BaseResponse> getAssignments(
      @RequestParam(required = false) String assetId,
      @RequestParam(required = false) String userId,
      @RequestParam(required = false) ZonedDateTime assignedDateFrom,
      @RequestParam(required = false) ZonedDateTime assignedDateTo,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "assignedDate") String sortBy,
      @RequestParam(defaultValue = "desc") String order) {
    Page<AssignmentDTO> assignments = assignmentService.getAssignments(assetId, userId, assignedDateFrom, assignedDateTo, page, pageSize, sortBy, order);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(assignments,true, HttpStatus.OK.value(), ""));
  }

  @GetMapping("/{assignmentId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<BaseResponse> getAssignment(@PathVariable String assignmentId) {
    AssignmentDTO dto = assignmentService.getAssignment(assignmentId);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(dto,true, HttpStatus.OK.value(), ""));
  }

  @PutMapping("/{assignmentId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<BaseResponse> updateAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO, @PathVariable String assignmentId) {
    AssignmentDTO updated = assignmentService.updateAssignment(assignmentDTO, assignmentId);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(updated,true, HttpStatus.OK.value(), "Assignment Updated Successfully"));
  }

  @DeleteMapping("/{assignmentId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<Void> deleteAssignment(@PathVariable String assignmentId) {
    assignmentService.deleteAssignment(assignmentId);
    return ResponseEntity.noContent().build();
  }
}
