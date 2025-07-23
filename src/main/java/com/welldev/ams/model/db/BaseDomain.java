package com.welldev.ams.model.db;

import java.time.ZonedDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class BaseDomain {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private Boolean active = true;
  private Boolean deleted = false;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = false, nullable = false)
  private ZonedDateTime createdAt = ZonedDateTime.now();
  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = true, nullable = true)
  private ZonedDateTime updatedAt;
}
