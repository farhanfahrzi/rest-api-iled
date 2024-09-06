package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.dto.request.CreateAdminRequest;
import com.enigma.Instructor_Led.dto.request.UpdateAdminRequest;
import com.enigma.Instructor_Led.dto.response.AdminResponse;
import com.enigma.Instructor_Led.entity.Admin;
import com.enigma.Instructor_Led.repository.AdminRepository;
import com.enigma.Instructor_Led.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public AdminResponse create(CreateAdminRequest createAdminRequest) {
        Admin admin = Admin.builder()
                .name(createAdminRequest.getName())
                .email(createAdminRequest.getEmail())
                .birthDate(createAdminRequest.getBirthDate())
                .phoneNumber(createAdminRequest.getPhoneNumber())
                .address(createAdminRequest.getAddress())
                .build();

        Admin savedAdmin = adminRepository.save(admin);
        return mapToResponse(savedAdmin);
    }

    @Override
    public AdminResponse update(UpdateAdminRequest updateAdminRequest) {
        Optional<Admin> adminOpt = adminRepository.findById(updateAdminRequest.getId());
        if (adminOpt.isEmpty()) {
            return null;
        }

        Admin admin = adminOpt.get();
        admin.setName(updateAdminRequest.getName());
        admin.setEmail(updateAdminRequest.getEmail());
        admin.setPhoneNumber(updateAdminRequest.getPhoneNumber());
        admin.setAddress(updateAdminRequest.getAddress());
        Admin updatedAdmin = adminRepository.save(admin);

        return mapToResponse(updatedAdmin);
    }

    @Override
    public AdminResponse getById(String id) {
        Optional<Admin> adminOpt = adminRepository.findById(id);
        return adminOpt.map(this::mapToResponse).orElse(null);
    }

    @Override
    public Page<AdminResponse> getAll(Pageable pageable) {
        return adminRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public void delete(String id) {
        adminRepository.deleteById(id);
    }

    private AdminResponse mapToResponse(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .birthDate(admin.getBirthDate())
                .phoneNumber(admin.getPhoneNumber())
                .address(admin.getAddress())
                .build();
    }
}
