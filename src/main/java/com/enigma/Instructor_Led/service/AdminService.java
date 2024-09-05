package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateAdminRequest;
import com.enigma.Instructor_Led.dto.request.UpdateAdminRequest;
import com.enigma.Instructor_Led.dto.response.AdminResponse;

import java.util.List;

public interface AdminService {
    AdminResponse createAdmin(CreateAdminRequest createAdminRequest);
    AdminResponse getById(String id);
    AdminResponse updateAdmin(UpdateAdminRequest updateAdminRequest);
    List<AdminResponse> getAll();
    void deleteAdmin(String id);
}
