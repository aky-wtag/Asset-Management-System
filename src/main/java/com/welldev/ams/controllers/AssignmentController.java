package com.welldev.ams.controllers;

import java.time.ZonedDateTime;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.welldev.ams.model.request.AssetRequestDTO;
import com.welldev.ams.model.request.AssignmentDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.AssignmentService;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {
  private final AssignmentService assignmentService;

  public AssignmentController(AssignmentService assignmentService) {
    this.assignmentService = assignmentService;
  }

  @PostMapping("/create")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> createAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO) {
    return assignmentService.createAssignment(assignmentDTO);
  }

  @GetMapping("/getAll")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getAssetRequests(
      @RequestParam(required = false) String assetId,
      @RequestParam(required = false) String userId,
      @RequestParam(required = false) String remarks,
      @RequestParam(required = false) ZonedDateTime assignedDateFrom,
      @RequestParam(required = false) ZonedDateTime assignedDateTo,
      @RequestParam(required = false) ZonedDateTime returnDateFrom,
      @RequestParam(required = false) ZonedDateTime returnDateTo,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String order) {
    return assignmentService.getAssignments(assetId, userId, remarks, assignedDateFrom, assignedDateTo, returnDateFrom, returnDateTo, page, pageSize, sortBy, order);
  }

  @GetMapping("/get")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getAssignment(@RequestParam String assignmentId) {
    return assignmentService.getAssignment(assignmentId);
  }

  @PutMapping("/update")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> updateAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO, @RequestParam String assignmentId) {
    return assignmentService.updateAssignment(assignmentDTO,assignmentId);
  }

  @DeleteMapping("/delete")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> deleteAssignment(@RequestParam String assignmentId) {
    return assignmentService.deleteAssignment(assignmentId);
  }
}
