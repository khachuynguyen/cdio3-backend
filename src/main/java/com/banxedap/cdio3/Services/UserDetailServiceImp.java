package com.banxedap.cdio3.Services;


import com.banxedap.cdio3.Entities.User;
import com.banxedap.cdio3.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailServiceImp implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if(!userRepository.findUsersByUserName(s).isPresent())
            throw new UsernameNotFoundException(s);
        User user = userRepository.findUsersByUserName(s).get();
        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), roles);
        return userDetails;
    }
}
