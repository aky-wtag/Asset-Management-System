package com.welldev.ams.config;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.welldev.ams.model.db.Users;
import com.welldev.ams.repositories.AssetRequestRepository;

@Component("permissionCheck")
public class PermissionCheck {
  private final AssetRequestRepository assetRequestRepository;

  public PermissionCheck(AssetRequestRepository assetRequestRepository) {
    this.assetRequestRepository = assetRequestRepository;
  }

  public boolean isOwnerOrAdmin(String assetRequestId) {
    Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    boolean isAdmin = currentUser.getRoles().stream()
        .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
    if (isAdmin) {
      return true;
    }
    return assetRequestRepository.existsByIdAndRequestedBy_Id(
        UUID.fromString(assetRequestId), currentUser.getId()
    );
  }
}
