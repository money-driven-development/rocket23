package com.initcloud.rocket23.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import javax.persistence.Id;

import com.initcloud.rocket23.checklist.entity.UsedRule;
import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.user.enums.OAuthProvider;
import com.initcloud.rocket23.user.enums.RoleType;
import com.initcloud.rocket23.user.enums.UserState;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "IS_OAUTHED")
	private Character isOAuthed;

	@Column(name = "OAUTH_PROVIDER")
	@Enumerated(EnumType.STRING)
	private OAuthProvider oAuthProvider;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	private UserOAuthToken oAuthToken;

	@Column(name = "LAST_LOGIN")
	private LocalDateTime lastLogin;

	@Column(name = "NICKNAME", length = 255)
	private String nickname;

	@Column(name = "USERNAME", length = 255)
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "PROFILE_IMAGE_URL")
	private String profileImageUrl;

	@Column(name = "AUTHORITIES")
	private String authorities;

	@Column(name = "ROLE_TYPE")
	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	@Column(name = "USER_STATE")
	@Enumerated(EnumType.STRING)
	private UserState userState;


	@Column(name = "EMAIL")
	@Size(max = 128)
	private String email;


	@Column(name = "contact")
	@Size(max = 16)
	private String contact;

	@OneToMany(mappedBy = "user")
	private List<UsedRule> usedRules = new ArrayList<>();

	/**
	 * Constructor for Modifying Methods.
	 */
	@Builder(builderClassName = "userModifyBuilder", builderMethodName = "userModifyBuilder")
	public User(User user, LocalDateTime lastLogin, String password, String authorities, RoleType roleType,
		UserState userState, String email, String contact) {
		super(user.getCreatedAt(), user.getModifiedAt());
		this.id = user.getId();
		this.lastLogin = lastLogin;
		this.isOAuthed = user.getIsOAuthed();
		this.oAuthProvider = user.getOAuthProvider();
		this.lastLogin = user.getLastLogin();
		this.nickname = user.getNickname();
		this.username = user.getUsername();
		this.password = password;
		this.authorities = authorities;
		this.roleType = roleType;
		this.userState = userState;
		this.email = email;
		this.contact = contact;
	}
}

