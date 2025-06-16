package com.sandyflat.BlogApplication.serviceimpl;

import com.sandyflat.BlogApplication.entity.User;
import com.sandyflat.BlogApplication.exception.ResourceNotFoundException;
import com.sandyflat.BlogApplication.payload.UserDTO;
import com.sandyflat.BlogApplication.repository.UserRepository;
import com.sandyflat.BlogApplication.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User map = this.modelMapper.map(userDTO, User.class);
        User addedUser = this.userRepository.save(map);
        return this.modelMapper.map(addedUser, UserDTO.class);
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
