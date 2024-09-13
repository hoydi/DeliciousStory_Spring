package com.i5.ds.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date; // Java SQL Date
import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	// 생성자 주입으로 변경
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User registerUser(String userId, String userPw, String userName, String userEmail) {
		if (userRepository.findByUserId(userId).isPresent()) {
			throw new IllegalArgumentException("User ID already exists: " + userId);
		}
		java.util.Date currentDate = new java.util.Date();
		java.sql.Date userRegister = new java.sql.Date(currentDate.getTime());
		String encodedPassword = passwordEncoder.encode(userPw);
		User user = new User(userId, encodedPassword, userName, userEmail, userRegister);
		return userRepository.save(user);
	}

	public Optional<User> findByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}

	// 비밀번호 없이 사용자 정보만 업데이트하는 메서드
	public User updateUser(String userId, String userName, String userEmail) {
		User user = userRepository.findByUserId(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

		user.setUserName(userName);
		user.setUserEmail(userEmail);

		return userRepository.save(user);
	}

	// 새로운 비밀번호로 사용자 업데이트
	public void updateUserPassword(String userId, String newPassword) {
		User user = userRepository.findByUserId(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

		user.setUserPw(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}
}
