package com.welldev.ams.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.welldev.ams.model.db.Role;
import com.welldev.ams.model.db.Users;
import com.welldev.ams.model.mapper.UsersMapper;
import com.welldev.ams.model.request.UserDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.repositories.RoleRepository;
import com.welldev.ams.repositories.UserRepository;
import com.welldev.ams.service.UsersService;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {
  private final UserRepository userRepository;
  private final UsersMapper usersMapper;
  private final RoleRepository roleRepository;
  private final PasswordEncoder encoder;

  public UsersServiceImpl(UserRepository userRepository, UsersMapper usersMapper, RoleRepository roleRepository, PasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.usersMapper = usersMapper;
    this.roleRepository = roleRepository;
    this.encoder = encoder;
  }

  @Override
  public UserDTO createUser(UserDTO userDTO) {
      Optional<Users> existingUser = userRepository.findByEmail(userDTO.getEmail());
      if (existingUser.isPresent()){
        throw new RuntimeException("User already exists");
      }
      else {
        Users user = usersMapper.toUserEntity(userDTO);
        Role userRole = roleRepository.findByIdAndActiveAndDeleted(UUID.fromString(userDTO.getRole()), true, false)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        user.setPassword(encoder.encode(userDTO.getPassword()));
        Users saveEntity = userRepository.save(user);
        return usersMapper.toDto(saveEntity);
      }

  }

  @Override
  public UserDTO updateUser(UserDTO userDTO, String userId) {
      Optional<Users> entity = userRepository.findByIdAndActiveAndDeleted(UUID.fromString(userId), true, false);
      if(entity.isPresent()) {
        usersMapper.updateUserFromDto(userDTO, entity.get());
        if (StringUtils.hasText(userDTO.getPassword())) {
          entity.get().setPassword(encoder.encode(userDTO.getPassword()));
        }
        if (StringUtils.hasText(userDTO.getRole())) {
          Role userRole = roleRepository.findByIdAndActiveAndDeleted(UUID.fromString(userDTO.getRole()), true, false)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          Set<Role> roles = new HashSet<>();
          roles.add(userRole);
          entity.get().setRoles(roles);
        }
        Users user = userRepository.save(entity.get());
        return usersMapper.toDto(user);
      }
      else{
        throw new RuntimeException("User not found");
      }
  }

  @Override
  public Page<UserDTO> getUsers(String username, String email, String department, int page,
      int size, String sortBy, String order)
  {
      Specification<Users> spec = (root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<>();

        if (username != null && !username.isEmpty()) {
          predicates.add(cb.like(root.get("username"), "%"+username+"%"));
        }
        if (email != null && !email.isEmpty()) {
          predicates.add(cb.equal(root.get("email"), email));
        }
        if (department != null && !department.isEmpty()) {
          predicates.add(cb.equal(root.get("department"), department));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
      };
      Page<Users> vendorList = userRepository.findAll(spec, PageRequest.of(page, size));
    return vendorList.map(usersMapper::toDto);
  }

  @Override
  public UserDTO getUser(String userId) {
      Optional<Users> user = userRepository.findById(UUID.fromString(userId));
if(user.isPresent()){
    return usersMapper.toDto(user.get());
  }
      throw new RuntimeException("Role not found");
  }

  @Override
  public void deleteUser(String userId) {
      Optional<Users> user = userRepository.findByIdAndActiveAndDeleted(UUID.fromString(userId), true, false);
      if(user.isPresent()) {
        user.get().setDeleted(true);
        userRepository.save(user.get());
      }
      else{
        throw new RuntimeException("User not found");
      }
  }
}
