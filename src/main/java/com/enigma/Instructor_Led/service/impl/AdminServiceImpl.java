package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.constant.UserRole;
import com.enigma.Instructor_Led.dto.request.CreateAdminRequest;
import com.enigma.Instructor_Led.dto.request.UpdateAdminRequest;
import com.enigma.Instructor_Led.dto.response.AdminResponse;
import com.enigma.Instructor_Led.entity.Admin;
import com.enigma.Instructor_Led.entity.Role;
import com.enigma.Instructor_Led.entity.UserAccount;
import com.enigma.Instructor_Led.repository.AdminRepository;
import com.enigma.Instructor_Led.repository.UserAccountRepository;
import com.enigma.Instructor_Led.service.AdminService;
import com.enigma.Instructor_Led.service.RoleService;
import com.enigma.Instructor_Led.util.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final Validation validation;

    @Override
    public AdminResponse create(CreateAdminRequest createAdminRequest) {
        validation.validate(createAdminRequest);

        if (userAccountRepository.findByUsername(createAdminRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Encode password
        String hashPassword = passwordEncoder.encode(createAdminRequest.getPassword());

        // Ambil role TRAINEE
        Role role = roleService.getOrSave(UserRole.ROLE_ADMIN);

        // Buat user account baru
        UserAccount account = UserAccount.builder()
                .username(createAdminRequest.getUsername())
                .password(hashPassword)
                .isEnable(true)
                .roles(List.of(role))
                .build();

        // Simpan user account terlebih dahulu untuk generate ID
        userAccountRepository.saveAndFlush(account);

        Admin admin = Admin.builder()
                .name(createAdminRequest.getName())
                .email(createAdminRequest.getEmail())
                .birthDate(createAdminRequest.getBirthDate())
                .phoneNumber(createAdminRequest.getPhoneNumber())
                .address(createAdminRequest.getAddress())
                .userAccount(account)
                .build();

        Admin savedAdmin = adminRepository.save(admin);
        return mapToResponse(savedAdmin);
    }

    @Override
    public AdminResponse update(UpdateAdminRequest updateAdminRequest) {
        validation.validate(updateAdminRequest);

        if (userAccountRepository.findByUsername(updateAdminRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Encode password
        String hashPassword = passwordEncoder.encode(updateAdminRequest.getPassword());

        // Ambil role TRAINEE
        Role role = roleService.getOrSave(UserRole.ROLE_ADMIN);

        // Buat user account baru
        UserAccount account = UserAccount.builder()
                .username(updateAdminRequest.getUsername())
                .password(hashPassword)
                .isEnable(true)
                .roles(List.of(role))
                .build();

        // Simpan user account terlebih dahulu untuk generate ID
        userAccountRepository.saveAndFlush(account);


        Admin admin = getOneById(updateAdminRequest.getId());
        admin.setName(updateAdminRequest.getName());
        admin.setEmail(updateAdminRequest.getEmail());
        admin.setPhoneNumber(updateAdminRequest.getPhoneNumber());
        admin.setAddress(updateAdminRequest.getAddress());
        admin.setUserAccount(account);
        adminRepository.save(admin);

        return mapToResponse(admin);
    }

    @Override
    public AdminResponse getById(String id) {
        Optional<Admin> adminOpt = adminRepository.findById(id);
        return adminOpt.map(this::mapToResponse).orElse(null);
    }

    @Override
    public Admin getOneById(String id) {
        return adminRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));
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
