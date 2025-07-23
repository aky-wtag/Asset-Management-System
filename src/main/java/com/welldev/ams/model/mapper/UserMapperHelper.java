package com.welldev.ams.model.mapper;

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
    return userRepository.findByEmail(usernameOrId)
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + usernameOrId));
  }
}
