package vn.fpt.courseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.fpt.courseservice.dto.request.UserCreationRequest;
import vn.fpt.courseservice.dto.response.ApiResponse;
import vn.fpt.courseservice.dto.response.UserCreationResponse;
import vn.fpt.courseservice.dto.response.UserDetailResponse;
import vn.fpt.courseservice.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    ApiResponse<UserCreationResponse> createUser(@RequestBody UserCreationRequest request) {
        var result = userService.registration(request);
        return ApiResponse.<UserCreationResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("User Registration Successful")
                .result(result)
                .build();
    }

    @GetMapping
    ApiResponse<UserDetailResponse> getUserInfo() {
        var result = userService.getUserInfo();
        return ApiResponse.<UserDetailResponse>builder()
                .code(HttpStatus.OK.value())
                .message("User Information")
                .result(result)
                .build();
    }

}
