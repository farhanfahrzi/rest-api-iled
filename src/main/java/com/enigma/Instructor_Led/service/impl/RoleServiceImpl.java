//package com.enigma.Instructor_Led.service.impl;
//
//import com.enigma.Instructor_Led.service.RoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.enigma.Instructor_Led.constant.UserRole;
//import com.enigma.Instructor_Led.entity.Role;
//import com.enigma.Instructor_Led.repository.RoleRepository;
//
//@Service
//public class RoleServiceImpl implements RoleService {
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Override
//    public Role getOrSave(UserRole userRole) {
//        Role role = roleRepository.findByRole(userRole);
//        if (role == null) {
//            // Jika role belum ada, buat role baru
//            role = new Role();
//            role.setRole(userRole);
//            role = roleRepository.save(role);
//        }
//        return role;
//    }
//}
