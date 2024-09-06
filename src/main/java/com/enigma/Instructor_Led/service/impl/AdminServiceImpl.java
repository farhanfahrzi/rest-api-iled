package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.dto.request.CreateAdminRequest;
import com.enigma.Instructor_Led.dto.request.UpdateAdminRequest;
import com.enigma.Instructor_Led.dto.response.AdminResponse;
import com.enigma.Instructor_Led.entity.Admin;
import com.enigma.Instructor_Led.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public AdminResponse createAdmin(CreateAdminRequest createAdminRequest) {
        Admin admin = new Admin();
        admin.setName(createAdminRequest.getName());
        admin.setEmail(createAdminRequest.getEmail());
        admin = adminRepository.save(admin);
        return new AdminResponse(admin.getId(), admin.getName(), admin.getEmail());
    }

    @Override
    public AdminResponse getById(String id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
        return new AdminResponse(admin.getId(), admin.getName(), admin.getEmail());
    }

    @Override
    public AdminResponse updateAdmin(UpdateAdminRequest updateAdminRequest) {
        Admin admin = adminRepository.findById(updateAdminRequest.getId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        admin.setName(updateAdminRequest.getName());
        admin.setEmail(updateAdminRequest.getEmail());
        admin = adminRepository.save(admin);
        return new AdminResponse(admin.getId(), admin.getName(), admin.getEmail());
    }

    @Override
    public List<AdminResponse> getAll() {
        return adminRepository.findAll().stream()
                .map(admin -> new AdminResponse(admin.getId(), admin.getName(), admin.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAdmin(String id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
        adminRepository.delete(admin);
    }
}