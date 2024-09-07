//package com.enigma.Instructor_Led.service.impl;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserAccountRepository userAccountRepository;
//
//    @Override
//    public UserAccount getByUserId(String id) {
//        return userAccountRepository.findById(id).orElseThrow(
//                () -> new UsernameNotFoundException("User not found with id: " + id));
//    }
//
//    @Override
//    public UserAccount getByContext() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        return userAccountRepository.findById(username).orElseThrow(
//                () -> new UsernameNotFoundException("User not found with username: " + username));
//    }
//}