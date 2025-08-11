package com.welldev.ams.controllers;

import java.time.ZonedDateTime;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.welldev.ams.model.request.AssignmentDTO;
import com.welldev.ams.service.AssignmentService;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {
  private final AssignmentService assignmentService;

  public AssignmentController(AssignmentService assignmentService) {
    this.assignmentService = assignmentService;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<AssignmentDTO> createAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO) {
    AssignmentDTO created = assignmentService.createAssignment(assignmentDTO);
    return ResponseEntity.status(201).body(created);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<Page<AssignmentDTO>> getAssignments(
      @RequestParam(required = false) String assetId,
      @RequestParam(required = false) String userId,
      @RequestParam(required = false) ZonedDateTime assignedDateFrom,
      @RequestParam(required = false) ZonedDateTime assignedDateTo,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "assignedDate") String sortBy,
      @RequestParam(defaultValue = "desc") String order) {
    Page<AssignmentDTO> assignments = assignmentService.getAssignments(assetId, userId, assignedDateFrom, assignedDateTo, page, pageSize, sortBy, order);
    return ResponseEntity.ok(assignments);
  }

  @GetMapping("/{assignmentId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<AssignmentDTO> getAssignment(@PathVariable String assignmentId) {
    AssignmentDTO dto = assignmentService.getAssignment(assignmentId);
    return ResponseEntity.ok(dto);
  }

  @PutMapping("/{assignmentId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<AssignmentDTO> updateAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO, @PathVariable String assignmentId) {
    AssignmentDTO updated = assignmentService.updateAssignment(assignmentDTO, assignmentId);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{assignmentId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<Void> deleteAssignment(@PathVariable String assignmentId) {
    assignmentService.deleteAssignment(assignmentId);
    return ResponseEntity.noContent().build();
  }
}
