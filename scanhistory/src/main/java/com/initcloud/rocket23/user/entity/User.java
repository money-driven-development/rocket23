package com.initcloud.rocket23.user.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    private LocalDateTime lastLogin;

    private String username;

    private String password;

    private String profileImageUrl;

    private String contact;

    private String email;

    public User(LocalDateTime createdAt, LocalDateTime modifiedAt, Long id, LocalDateTime lastLogin, String username,
                String password, String profileImageUrl, String contact, String email) {
        super(createdAt, modifiedAt);
        this.id = id;
        this.lastLogin = lastLogin;
        this.username = username;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.contact = contact;
        this.email = email;
    }

    /**
     * Constructor for Individual social User
     */
    public User(String username, String nickname, AuthProvider oAuthProvider, UserState userState) {
        super(LocalDateTime.now(), LocalDateTime.now());
        this.lastLogin = LocalDateTime.now();
        this.username = username;
        this.password = "";
        this.email = "";
        this.contact = "";
    }

    /**
     * Add Social User with Github, has no team.
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
