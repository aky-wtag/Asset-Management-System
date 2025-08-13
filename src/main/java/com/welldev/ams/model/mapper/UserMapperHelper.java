package com.welldev.ams.model.mapper;

import java.util.UUID;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.welldev.ams.model.db.Users;
import com.welldev.ams.repositories.UserRepository;

@Component
public class UserMapperHelper {
  private final UserRepository userRepository;

  public UserMapperHelper(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Named("mapRequestedBy")
  public Users mapRequestedBy(String usernameOrId) {
    return userRepository.findById(UUID.fromString(usernameOrId))
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + usernameOrId));
  }
}
