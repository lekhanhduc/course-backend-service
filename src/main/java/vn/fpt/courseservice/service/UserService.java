package vn.fpt.courseservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.fpt.courseservice.dto.request.UserCreationRequest;
import vn.fpt.courseservice.dto.response.UserCreationResponse;
import vn.fpt.courseservice.model.User;
import vn.fpt.courseservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCreationResponse registration(UserCreationRequest request) {
        if(userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email already in use");

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return UserCreationResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
