package com.initcloud.rocket23.user.service;

import com.initcloud.rocket23.infra.repository.UserRepository;
import com.initcloud.rocket23.security.service.CustomUserDetailsService;

public class UserService extends CustomUserDetailsService {
	public UserService(UserRepository userRepository) {
		super(userRepository);
	}

	public void getUserList() {

	}

	public void getUserDetails() {

	}

	public void modifyUserProfile() {

	}

	public void removeUserProfile() {

	}

	public void modifyUserStatus() {

	}
}
