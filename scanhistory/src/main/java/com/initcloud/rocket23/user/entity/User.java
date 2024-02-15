package com.initcloud.rocket23.user.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.security.dto.OAuthDto;
import com.initcloud.rocket23.user.enums.AuthProvider;
import com.initcloud.rocket23.user.enums.UserState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
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
    @Builder
    public User(String username, AuthProvider oAuthProvider, UserState userState, String password, String email, String contact, String profileImageUrl) {
        this.lastLogin = LocalDateTime.now();
        this.userState = userState;
        this.username = username;
        this.password = password;
        this.email = email;
        this.contact = contact;
        this.profileImageUrl = profileImageUrl;
    }

    public void modifyUser(String profileImageUrl, String contact, String email){
        this.profileImageUrl = profileImageUrl;
        this.contact = contact;
        this.email = email;
    }

    public void modifyState(UserState userState){
        this.userState = userState;
    }

    /**
     * Add Social User with Github, has no team.
     *
     * @return User
     */
    public static User addIndividualSocialUser(OAuthDto.GithubUserDetail detail) {
        return new User(detail.getLogin(), AuthProvider.GITHUB, UserState.ACTIVATE, "","","", "");
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
