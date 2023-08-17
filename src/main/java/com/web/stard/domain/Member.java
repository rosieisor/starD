package com.web.stard.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

@Getter @Setter
@Entity
@NoArgsConstructor(force = true)
@Table(name="Member")
public class Member implements UserDetails {

    @Id
    private String id;

    @NotNull
    private String nickname;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    private String city; // 시

    private String district; // 구

//    @OneToOne @JoinColumn(name = "profile_id")
//    private Profile profile; // 프로필

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authority_id") // Member 테이블에 authority_id 컬럼 추가
    private Authority roles;


    @Builder
    public Member(String id, String name, String email, String password, String phone, String nickname) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nickname = nickname;
    }


    // 계정의 권한 목록 return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.roles.getAuthorityName()));
        return authorities;
    }

    // 계정의 고유한 값 ex) PK return
    @Override
    public String getUsername() {
        return this.id;
    }

    // 계정의 만료 여부 return
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정의 잠김 여부 return
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부 return
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정의 활성화 여부 return
    @Override
    public boolean isEnabled() {
        return true;
    }
}