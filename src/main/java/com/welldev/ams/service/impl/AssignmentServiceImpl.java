package com.welldev.ams.service.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Asset;
import com.welldev.ams.model.db.Assignment;
import com.welldev.ams.model.mapper.AssignmentMapper;
import com.welldev.ams.model.request.AssignmentDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.repositories.AssignmentRepository;
import com.welldev.ams.service.AssignmentService;
import com.welldev.ams.utils.Utils;

@Slf4j
@Service
public class AssignmentServiceImpl implements AssignmentService {
  private final AssignmentRepository assignmentRepository;
  private final Utils utils;
  private final AssignmentMapper assignmentMapper;

  public AssignmentServiceImpl(AssignmentRepository assignmentRepository, Utils utils, AssignmentMapper assignmentMapper)
  {
    this.assignmentRepository = assignmentRepository;
    this.utils = utils;
    this.assignmentMapper = assignmentMapper;
  }

  @Override
  public ResponseEntity<BaseResponse> createAssignment(AssignmentDTO assignmentDTO) {
    try {
      Assignment saveEntity = assignmentRepository.save(assignmentMapper.toEntity(assignmentDTO));
      return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), "Assignment Created Successfully."));
    }
    catch (Exception e) {
      log.error("Create Assignment Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> updateAssignment(AssignmentDTO assignmentDTO, String assignmentId) {
    try {
      Optional<Assignment> assignmentEntity = assignmentRepository.findByIdAndActiveAndDeleted(UUID.fromString(assignmentId), true, false);
      if(assignmentEntity.isPresent()) {
        assignmentMapper.updateEntity(assignmentDTO, assignmentEntity.get());
        Assignment saveEntity = assignmentRepository.save(assignmentEntity.get());
        return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), ""));
      }
      return ResponseEntity.notFound().build();
    }
    catch (Exception e) {
      log.error("Update Assignment Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getAssignments(String assetId, String userId, String remarks,
      ZonedDateTime assignedDateFrom, ZonedDateTime assignedDateTo, ZonedDateTime returnDateFrom,
      ZonedDateTime returnDateTo, int page, int size, String sortBy, String order)
  {
    try {
      Specification<Assignment> spec = (root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<>();

        if (assetId != null && !assetId.isEmpty()) {
          predicates.add(cb.equal(root.get("assetId"), assetId));
        }
        if (userId != null && !userId.isEmpty()) {
          predicates.add(cb.equal(root.get("userId"), userId));
        }
        if (remarks != null && !remarks.isEmpty()) {
          predicates.add(cb.equal(root.get("remarks"), remarks));
        }
        if (assignedDateFrom != null && assignedDateTo != null) {
          predicates.add(cb.between(root.get("assignedDate"), assignedDateFrom, assignedDateTo));
        }
        if (returnDateFrom != null && returnDateTo != null) {
          predicates.add(cb.between(root.get("returnDate"), returnDateFrom, returnDateTo));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
      };
      Page<Assignment> assignmentList = assignmentRepository.findAll(spec, PageRequest.of(page, size));
      return ResponseEntity.ok().body(utils.generateResponse(assignmentList, true, HttpStatus.OK.value(), ""));
    }
    catch (Exception e) {
      log.error("Get Assignments Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getAssignment(String assignmentId) {
    try {
      Optional<Assignment> asset = assignmentRepository.findById(UUID.fromString(assignmentId));
      return asset.map(value -> ResponseEntity.ok()
              .body(utils.generateResponse(value, true, HttpStatus.OK.value(), "")))
          .orElseGet(() -> ResponseEntity.notFound()
              .build());
    }
    catch (Exception e) {
      log.error("Get Assignment Error: {}", assignmentId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> deleteAssignment(String assignmentId) {
    try {
      Optional<Assignment> asset = assignmentRepository.findByIdAndActiveAndDeleted(UUID.fromString(assignmentId), true, false);
      if(asset.isPresent()) {
        asset.get().setDeleted(true);
        assignmentRepository.save(asset.get());
        return ResponseEntity.noContent().build();
      }
      else{
        return ResponseEntity.notFound().build();
      }
    }
    catch (Exception e) {
      log.error("Delete Assignment Error: {}", assignmentId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }
}
