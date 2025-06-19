package com.sandyflat.BlogApplication.serviceimpl;

import com.sandyflat.BlogApplication.entity.Role;
import com.sandyflat.BlogApplication.entity.User;
import com.sandyflat.BlogApplication.exception.ResourceNotFoundException;
import com.sandyflat.BlogApplication.dto.UserDTO;
import com.sandyflat.BlogApplication.repository.RoleRepository;
import com.sandyflat.BlogApplication.repository.UserRepository;
import com.sandyflat.BlogApplication.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.existsByName(userDTO.getName())){
            throw new IllegalArgumentException("Username already exists..");
        }

        // Fetch roles from database
        Set<Role> roles = userDTO.getRoleId().stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new ResourceNotFoundException("Role", "Roll Id", roleId)))
                .collect(Collectors.toSet());

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(roles)
                .age(userDTO.getAge())
                .gender(userDTO.getGender())
                .build();

       User addedUser = this.userRepository.save(user);
       return modelMapper.map(addedUser, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAge(userDTO.getAge());
        user.setGender(userDTO.getGender());

        User updatedUser = userRepository.save(user);
        return this.modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = users.stream().map((cat) -> this.modelMapper.map(cat, UserDTO.class)).toList();
        return userDTOS;
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
        userRepository.delete(user);
    }
}
