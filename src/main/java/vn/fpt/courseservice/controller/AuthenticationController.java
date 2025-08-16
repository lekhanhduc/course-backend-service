package vn.fpt.courseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.fpt.courseservice.dto.request.LoginRequest;
import vn.fpt.courseservice.dto.response.ApiResponse;
import vn.fpt.courseservice.dto.response.LoginResponse;
import vn.fpt.courseservice.service.AuthenticationService;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        var result = authenticationService.login(request);

        return ApiResponse.<LoginResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Login success")
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestHeader(name = "Authorization") String header) throws ParseException {
        String token = header.replace("Bearer ", "");
        authenticationService.logout(token);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Logout success")
                .build();
    }

}
