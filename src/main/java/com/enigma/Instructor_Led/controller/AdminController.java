package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.constant.PathUrl;
import com.enigma.Instructor_Led.dto.request.CreateAdminRequest;
import com.enigma.Instructor_Led.dto.request.UpdateAdminRequest;
import com.enigma.Instructor_Led.dto.response.AdminResponse;
import com.enigma.Instructor_Led.dto.response.CommonResponse;
import com.enigma.Instructor_Led.dto.response.PagingResponse;
import com.enigma.Instructor_Led.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathUrl.ADMIN)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<CommonResponse<AdminResponse>> createAdmin(@RequestBody CreateAdminRequest createAdminRequest) {
        AdminResponse adminResponse = adminService.create(createAdminRequest);
        CommonResponse<AdminResponse> response = CommonResponse.<AdminResponse>builder()
                .message("Admin created successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(adminResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<AdminResponse>> updateAdmin(@RequestBody UpdateAdminRequest updateAdminRequest) {
        AdminResponse adminResponse = adminService.update(updateAdminRequest);
        CommonResponse<AdminResponse> response = CommonResponse.<AdminResponse>builder()
                .message("Admin updated successfully")
                .statusCode(HttpStatus.OK.value())
                .data(adminResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<AdminResponse>> getAdminById(@PathVariable String id) {
        AdminResponse adminResponse = adminService.getById(id);
        CommonResponse<AdminResponse> response = CommonResponse.<AdminResponse>builder()
                .message("Admin fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(adminResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<AdminResponse>>> getAllAdmins(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<AdminResponse> adminPage = adminService.getAll(pageable);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(adminPage.getTotalPages())
                .totalElement(adminPage.getTotalElements())
                .hasNext(adminPage.hasNext())
                .hasPrevious(adminPage.hasPrevious())
                .build();

        CommonResponse<List<AdminResponse>> response = CommonResponse.<List<AdminResponse>>builder()
                .message("Successfully retrieved all admins")
                .statusCode(HttpStatus.OK.value())
                .data(adminPage.getContent())
                .paging(pagingResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteAdmin(@PathVariable String id) {
        adminService.delete(id);
        CommonResponse<Void> response = CommonResponse.<Void>builder()
                .message("Admin deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
