package com.welldev.ams.service.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Assignment;
import com.welldev.ams.model.mapper.AssignmentMapper;
import com.welldev.ams.model.request.AssignmentDTO;
import com.welldev.ams.repositories.AssignmentRepository;
import com.welldev.ams.service.AssignmentService;

@Slf4j
@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {
  private final AssignmentRepository assignmentRepository;
  private final AssignmentMapper assignmentMapper;

  public AssignmentServiceImpl(AssignmentRepository assignmentRepository, AssignmentMapper assignmentMapper) {
    this.assignmentRepository = assignmentRepository;
    this.assignmentMapper = assignmentMapper;
  }

  @Override
  public AssignmentDTO createAssignment(AssignmentDTO assignmentDTO) {
    Assignment entity = assignmentMapper.toEntity(assignmentDTO);
    Assignment saved = assignmentRepository.save(entity);
    return assignmentMapper.toDto(saved);
  }

  @Override
  public AssignmentDTO updateAssignment(AssignmentDTO assignmentDTO, String assignmentId) {
    Optional<Assignment> optional = assignmentRepository.findById(UUID.fromString(assignmentId));
    if (optional.isPresent()) {
      Assignment entity = optional.get();
      assignmentMapper.updateEntity(assignmentDTO, entity);
      Assignment saved = assignmentRepository.save(entity);
      return assignmentMapper.toDto(saved);
    }
    throw new RuntimeException("Assignment not found");
  }

  @Override
  public Page<AssignmentDTO> getAssignments(String assetId, String userId, ZonedDateTime assignedDateFrom, ZonedDateTime assignedDateTo, int page, int size, String sortBy, String order) {
    Specification<Assignment> spec = (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (assetId != null && !assetId.isEmpty()) {
        predicates.add(cb.equal(root.get("asset").get("id"), assetId));
      }
      if (userId != null && !userId.isEmpty()) {
        predicates.add(cb.equal(root.get("user").get("id"), userId));
      }
      if (assignedDateFrom != null && assignedDateTo != null) {
        predicates.add(cb.between(root.get("assignedDate"), assignedDateFrom, assignedDateTo));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
    Page<Assignment> assignmentPage = assignmentRepository.findAll(spec, PageRequest.of(page, size));
    return assignmentPage.map(assignmentMapper::toDto);
  }

  @Override
  public AssignmentDTO getAssignment(String assignmentId) {
    Optional<Assignment> assignment = assignmentRepository.findById(UUID.fromString(assignmentId));
    if (assignment.isPresent()) {
      return assignmentMapper.toDto(assignment.get());
    }
    throw new RuntimeException("Assignment not found");
  }

  @Override
  public void deleteAssignment(String assignmentId) {
    Optional<Assignment> assignment = assignmentRepository.findById(UUID.fromString(assignmentId));
    if (assignment.isPresent()) {
      assignmentRepository.delete(assignment.get());
    } else {
      throw new RuntimeException("Assignment not found");
    }
  }
}
