package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateAdminRequest;
import com.enigma.Instructor_Led.dto.request.UpdateAdminRequest;
import com.enigma.Instructor_Led.dto.response.AdminResponse;
import com.enigma.Instructor_Led.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {
    AdminResponse create(CreateAdminRequest createAdminRequest);
    AdminResponse getById(String id);
    Admin getOneById(String id);
    AdminResponse update(UpdateAdminRequest updateAdminRequest);
    Page<AdminResponse> getAll(Pageable pageable);
    void delete(String id);
}
