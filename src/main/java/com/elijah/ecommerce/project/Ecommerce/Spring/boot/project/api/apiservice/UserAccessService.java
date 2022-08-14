package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.api.apiservice;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.api.domain.Role;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.api.domain.UsersAccess;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.api.repo.RoleRepository;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.api.repo.UserAccessRepository;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserAccessService implements UserDetailsService {

    private final UserAccessRepository userAccessRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UsersAccess usersAccess = userAccessRepository.findByUsername(username);
       if (Objects.isNull(usersAccess)){
           throw new UsernameNotFoundException("User not found");
       }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
       usersAccess.getRoles().forEach(role -> {
           authorities.add(new SimpleGrantedAuthority(role.getName()));
       });
       return new org.springframework.security.core.userdetails.User(usersAccess.getUsername(),usersAccess.getPassword(),authorities);
    }


    public UsersAccess saveUser(UsersAccess usersAccess){
        log.info("Saving new user {} to the database",usersAccess.getName());
        usersAccess.setPassword(passwordEncoder.encode(usersAccess.getPassword()));
        return userAccessRepository.save(usersAccess);
    }

    public Role saveRole(Role role){
        log.info("Saving new role {} to the database",role.getName());
       return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) throws DataNotFoundException {
        log.info("Adding role {} to the user {}",roleName,username);
        UsersAccess usersAccess = userAccessRepository.findByUsername(username);
        if (Objects.isNull(usersAccess)){
            throw new DataNotFoundException("There is no user with this user name");
        }
        Role role = roleRepository.findByName(roleName);
        if (Objects.isNull(role)){
            throw new DataNotFoundException("There is no role with this name");
        }
        usersAccess.getRoles().add(role);
    }
    public UsersAccess getUser(String username) throws DataNotFoundException {
        log.info("Fetching a user {}",username);
        if (Objects.isNull(userAccessRepository.findByUsername(username))){
            throw new DataNotFoundException("Incorrect Username");
        }
        return userAccessRepository.findByUsername(username);
    }
    public List<UsersAccess> getAllUsers(){
        log.info("Getting all users");
        return userAccessRepository.findAll();
    }


}
