package com.maple.maple_boss_now.service;

import com.maple.maple_boss_now.entity.User;
import com.maple.maple_boss_now.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getId().toString())
                .password("") // password는 사용되지 않음
                .authorities("USER") // 필요시 사용자 권한 설정
                .build();
    }
}
