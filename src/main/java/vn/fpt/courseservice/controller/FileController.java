package vn.fpt.courseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.fpt.courseservice.dto.response.ApiResponse;
import vn.fpt.courseservice.service.FileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;

    @PostMapping
    ApiResponse<String> uploadFile(MultipartFile file) {
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .result(fileService.uploadImage(file))
                .build();
    }

}
