package com.gswep.insurance.user.service;
import com.gswep.insurance.user.entity.User;
import com.gswep.insurance.user.entity.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {


    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }
    

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 역할을 가져옴
        UserRoleEnum role = user.getRole();
        // 사용자의 역할에서 권한(Authority)을 가져옴
        String authority = role.getAuthority();

        // Spring Security의 SimpleGrantedAuthority를 사용하여 권한 객체를 생성
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        // 권한 객체를 담을 Collection을 생성
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 생성한 권한 객체를 Collection에 추가
        authorities.add(simpleGrantedAuthority);

        // 생성한 권한 목록을 반환
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}