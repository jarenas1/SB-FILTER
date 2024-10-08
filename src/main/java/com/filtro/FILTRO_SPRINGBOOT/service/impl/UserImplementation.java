package com.filtro.FILTRO_SPRINGBOOT.service.impl;

import com.filtro.FILTRO_SPRINGBOOT.model.RoleEntity;
import com.filtro.FILTRO_SPRINGBOOT.model.UserEntity;
import com.filtro.FILTRO_SPRINGBOOT.repository.RoleRepository;
import com.filtro.FILTRO_SPRINGBOOT.repository.UserRepository;
import com.filtro.FILTRO_SPRINGBOOT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserImplementation implements UserService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserEntity save(UserEntity userEntity) {
        //Find role default
        Optional<RoleEntity> optionalRoleEntityUser = roleRepository.findByName("ROLE_USER");
        //list of roles to the user
        List<RoleEntity> roles = new ArrayList<>();

        optionalRoleEntityUser.ifPresent(role->roles.add(role));
        //check if user is admin
        if(userEntity.isAdmin()){
            Optional<RoleEntity> optionalRoleEntityAdmin = roleRepository.findByName("ROLE:ADMIN");

            optionalRoleEntityAdmin.ifPresent(role->roles.add(role));
        }
        //setUserRoles
        userEntity.setRoles(roles);
        //encode the password
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findById(String id) {
        return userRepository.findById(id);
    }
}
