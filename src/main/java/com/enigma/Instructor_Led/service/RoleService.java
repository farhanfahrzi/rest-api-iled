package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.constant.UserRole;
import com.enigma.Instructor_Led.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole role);
}
