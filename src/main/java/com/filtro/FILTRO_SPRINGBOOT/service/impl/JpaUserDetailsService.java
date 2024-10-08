package com.filtro.FILTRO_SPRINGBOOT.service.impl;


import com.filtro.FILTRO_SPRINGBOOT.model.UserEntity;
import com.filtro.FILTRO_SPRINGBOOT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //captamos el username y lo buscamos con nuestro metodo
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        //VERIFICAMOS SI EXISTIO O NO
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema", username));
        }

        UserEntity userEntity = userOptional.get();

        //creamos una lista de roles, por medio del apiStream para transormarlo
        List<GrantedAuthority> authorities = userEntity.getRoles().stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName())).collect(Collectors.toList());

        //retornamos una instancia de User, la cual es de SPRING SECURITY
        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                true,
                true,
                true,
                authorities);
        //PASAMOS TODOS LOS ATRIBUTOS, 3 TRUE Y LA LISTA CREADA ANTERIORMENTE
    }
}
