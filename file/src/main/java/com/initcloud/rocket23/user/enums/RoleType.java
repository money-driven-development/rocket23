package com.initcloud.rocket23.user.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER"),
	GUEST("ROLE_GUEST");

	private final String role;

	public static RoleType of(String code) {

		return Arrays.stream(RoleType.values())
			.filter(r -> r.getRole().equals(code))
			.findAny()
			.orElse(GUEST);
	}
}
