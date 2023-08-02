package com.initcloud.rocket23.user.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.Email;

import com.initcloud.rocket23.security.dto.OAuthDto;
import com.initcloud.rocket23.user.enums.AuthProvider;
import com.initcloud.rocket23.user.enums.UserState;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.initcloud.rocket23.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private UserState userState;

    @Column
    private LocalDateTime lastLogin;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String profileImageUrl;

    @Column
    private String contact;

    @Email
    @Column
    private String email;

    /**
     * Constructor for Individual social User
     */
    public User(String username, String nickname, AuthProvider oAuthProvider, UserState userState) {
        this.lastLogin = LocalDateTime.now();
        this.userState = userState;
        this.username = username;
        this.password = "";
        this.email = "";
        this.contact = "";
    }

    /**
     * Add Social User with Github, has no team.
     *
     * @return User
     */
    public static User addIndividualSocialUser(OAuthDto.GithubUserDetail detail) {
        return new User(detail.getLogin(), detail.getName(), AuthProvider.GITHUB, UserState.ACTIVATE);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
