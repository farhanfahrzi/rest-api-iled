package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.constant.TraineeStatus;
import com.enigma.Instructor_Led.constant.UserRole;
import com.enigma.Instructor_Led.dto.request.LoginRequest;
import com.enigma.Instructor_Led.dto.request.RegisterRequest;
import com.enigma.Instructor_Led.dto.request.RegitserTraineeRequest;
import com.enigma.Instructor_Led.dto.response.LoginResponse;
import com.enigma.Instructor_Led.dto.response.RegisterResponse;
import com.enigma.Instructor_Led.entity.*;
import com.enigma.Instructor_Led.repository.UserAccountRepository;
import com.enigma.Instructor_Led.service.*;
import com.enigma.Instructor_Led.util.Validation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final ProgrammingLanguageService programmingLanguageService;
    private final PasswordEncoder passwordEncoder;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Validation validation;

    @Value("${iled.superadmin.username}")
    private String adminUsername;
    @Value("${iled.superadmin.password}")
    private String adminPassword;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initAdmin() {
        Optional<UserAccount> currentUserAdmin = userAccountRepository.findByUsername(adminUsername);
        if (currentUserAdmin.isPresent()) return;

        Role trainee = roleService.getOrSave(UserRole.ROLE_TRAINEE);
        Role admin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        Role trainer = roleService.getOrSave(UserRole.ROLE_TRAINER);
        Role superAdmin = roleService.getOrSave(UserRole.ROLE_SUPER_ADMIN);

        UserAccount account = UserAccount.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .roles(List.of(superAdmin, admin, trainer, trainee))
                .isEnable(true)
                .build();
        userAccountRepository.save(account);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(RegitserTraineeRequest request) {
        // Validasi request
        validation.validate(request);

        // Cek apakah username sudah ada di database
        if (userAccountRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Encode password
        String hashPassword = passwordEncoder.encode(request.getPassword());

        // Ambil role TRAINEE
        Role role = roleService.getOrSave(UserRole.ROLE_TRAINEE);

        // Buat user account baru
        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .isEnable(true)
                .roles(List.of(role))
                .build();

        // Simpan user account terlebih dahulu untuk generate ID
        userAccountRepository.saveAndFlush(account);

        // Buat trainee baru
        Trainee trainee = Trainee.builder()
                .name(request.getName())
                .nik(request.getNik())
                .email(request.getEmail())
                .address(request.getAddress())
                .birthDate(request.getBirthDate())
                .phoneNumber(request.getPhoneNumber())
                .userAccount(account)
                .status(TraineeStatus.ACTIVE)
                .build();


        // Simpan trainee ke database
        traineeService.createTrainee(trainee);

        // Return response registrasi
        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public LoginResponse login (LoginRequest request) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authentication);
        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();

        String token = jwtService.generateToken (userAccount);

        return LoginResponse.builder()
                .token(token)
                .username(userAccount.getUsername())
                .roles (userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerTrainer(RegisterRequest request) {
        // Cek apakah username sudah ada di database
        if (userAccountRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Encode password
        String hashPassword = passwordEncoder.encode(request.getPassword());

        // Ambil role TRAINEE
        Role role = roleService.getOrSave(UserRole.ROLE_TRAINER);

        // Buat user account baru
        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .isEnable(true)
                .roles(List.of(role))
                .build();

        // Simpan user account terlebih dahulu untuk generate ID
        userAccountRepository.saveAndFlush(account);

        Trainer trainer = trainerService.getOneById(request.getId());
        trainer.setUserAccount(account);

        // Return response registrasi
        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
