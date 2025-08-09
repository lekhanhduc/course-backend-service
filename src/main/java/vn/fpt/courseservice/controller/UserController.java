package vn.fpt.courseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.fpt.courseservice.dto.request.UserCreationRequest;
import vn.fpt.courseservice.dto.response.UserCreationResponse;
import vn.fpt.courseservice.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    UserCreationResponse createUser(@RequestBody UserCreationRequest request) {
        return userService.registration(request);
    }

}
