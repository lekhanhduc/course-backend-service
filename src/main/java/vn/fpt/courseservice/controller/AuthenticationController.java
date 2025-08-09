package vn.fpt.courseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.fpt.courseservice.dto.request.LoginRequest;
import vn.fpt.courseservice.dto.response.LoginResponse;
import vn.fpt.courseservice.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    LoginResponse login(@RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }
}
