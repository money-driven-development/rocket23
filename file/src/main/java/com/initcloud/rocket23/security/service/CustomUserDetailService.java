package com.initcloud.rocket23.security.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.user.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return (UserDetails)userRepository.findByUsername(username).orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));
	}
}
